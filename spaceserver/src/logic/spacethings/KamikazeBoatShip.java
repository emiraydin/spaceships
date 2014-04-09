package logic.spacethings;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import logic.AbstractWeapon;
import logic.Explosives;
import logic.FleetCommander;
import logic.StarBoard;

import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.SpaceThingType;

public class KamikazeBoatShip extends AbstractShip {
	
	public KamikazeBoatShip(int x, int y, OrientationType oType, FleetCommander owner, StarBoard gameBoard){
		super(x, y, oType, owner, gameBoard);

		this.length = 1;
		this.setMaxSpeed(-1); 		// UNIQUE
		
		initializeHealth(this.length, true);
		
		this.cannonLength = 0;
		this.cannonWidth = 0;
		this.cannonLengthOffset = 0;
		
		this.radarVisibilityWidth = 5;
		this.radarVisibilityLength = 5;
		this.radarVisibilityLengthOffset = -2;
		
		this.arsenal = new AbstractWeapon[1];
		arsenal[0] = new Explosives(this);
		
	}
	
	/**
	 * Checks if within movement range and there isn't an obstacle in the way.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isValidIntermediateMove(int x, int y) { 
		int shipX = this.getX();
		int shipY = this.getY();
		
		// out of bounds
		if(!StarBoard.inBounds(x, y)) { 
			return false;
		}
		
		// not within movement range of ship
		if(x < shipX-2 || x > shipX+2 || y < shipY-2 || y > shipY+2) { 
			return false;
		}
		
		// something in the way (mines not included)
		SpaceThing thing = this.getOwner().getHandler().getBoard().getSpaceThing(x, y);
		if(thing != null && thing != this && !(thing instanceof Mine)) { 
			return false;
		}
		
		return true;

	}
	
	/**
	 * Handles movement for the Kamikaze boat ship
	 * NOTE: the kamikaze boat is different so validation etc must be done here, including all messages
	 * @param ship
	 * @return 
	 */
	public int move(int x, int y) { 	
		int shipX = this.getX();
		int shipY = this.getY();
		
		if(!StarBoard.inBounds(x, y)) { 
			this.getOwner().setActionResponse("Ships cannot go out of bounds");
			return -1;
		}
		
		if(x < shipX - 2 || x > shipX + 2 || y > shipY + 2 || y < shipY - 2) { 
			this.getOwner().setActionResponse("Ship cannot move that fast");
			return -1;
		}
		
		if(shipX == x && shipY == y) { 
			// no need to move
			return 0;
		}
		
		/* Need to find an obstacle-free manhattan path from point A to point B */
		
		// create graph
		GridNode root = constructMovementGraph();
		
		// breadth first search
		GridNode target = null;
		Queue<GridNode> bfsQueue = new LinkedList<>();
		root.visit();
		bfsQueue.add(root);
		while(!bfsQueue.isEmpty()) { 
			GridNode node = bfsQueue.remove();
			if(node.getPosition().x == x && node.getPosition().y == y) { 
				target = node;
				break;
			}
			else { 
				for(GridNode neighbour : node.getNeighbours()) { 
					if(!neighbour.isVisited()) { 
						neighbour.visit();
						neighbour.setParent(node);
						bfsQueue.add(neighbour);
					}
				}
			}
		}
		
		if(target != null) { 
			// reachable! get path.
			List<Position> path = new ArrayList<>();
			path.add(target.getPosition());
			GridNode current = target;
			while(current.parent != null) { 
				current = current.getParent();
				path.add(current.getPosition());
				if(current == root) { 
					break;
				}			
			}
			Collections.reverse(path);
			
			// ensure there are no mines lol
			for(Position coord : path) { 
				if(this.getOwner().handleMineExplosions(this, coord.x, coord.y)) { 
					// if there's a mine, the ship dies anyway
					return path.indexOf(coord);
				}
			}
			
			// actually successfully moved with no mines
			FleetCommander fc = this.getOwner();
			StarBoard board = fc.getHandler().getBoard();
			fc.decrementVisibility(this);
			board.clearSpaceThing(this);
			
			this.setX(x);
			this.setY(y);
			
			board.setSpaceThing(this);
			fc.incrementVisibility(this);	
			
			// TODO: UPDATE THE MESSAGE WITH PATH
			return path.size();
		}
		
		// target not reachable
		this.getOwner().setActionResponse("That move is not valid");
		return -1;
	}
	
	public boolean inMoveRange(int x, int y) { 
		int shipX = this.getX();
		int shipY = this.getY();
		
		if(!StarBoard.inBounds(x, y)) { 
			return false;
		}
		if(x < shipX-2 || x > shipX+2 || y < shipY-2 || y > shipY+2) { 
			return false;
		}
		return true;
	}

	
	public GridNode constructMovementGraph() { 
		int x = this.getX();
		int y = this.getY();
		StarBoard board = this.getOwner().getHandler().getBoard();
		
		HashMap<Position, GridNode> constructedNodes = new HashMap<>();
		
		Position rootCoords = new Position(x, y);
		GridNode root = new GridNode(rootCoords);
		constructedNodes.put(rootCoords, root);
		
		// construct all nodes and put into constructedNodes
		for(int i = x-2; i <= x+2; i++) { 
			for(int j = y-2; j <= y+2; j++) { 
				if(!(j == y && i == x)) { 
					if(StarBoard.inBounds(i, j)) { 
						Position coords = new Position(i, j);
						constructedNodes.put(coords, new GridNode(coords));
					}
				}
			}
		}
		
		// join all neighbours
		for(Position coords : constructedNodes.keySet()) { 
//			int diffx = coords.x-x;
//			int diffy = coords.y-y;
//			System.out.println("checking " + diffx + "," + diffy);
			
			GridNode node = constructedNodes.get(coords);
			List<Position> neighbours = new ArrayList<Position>();
			
			if(constructedNodes.containsKey(new Position(coords.x, coords.y+1))) { 
				neighbours.add(new Position(coords.x, coords.y+1));
			}
			
			if(constructedNodes.containsKey(new Position(coords.x, coords.y-1))) { 
				neighbours.add(new Position(coords.x, coords.y-1));
			}
			if(constructedNodes.containsKey(new Position(coords.x+1, coords.y))) { 
				neighbours.add(new Position(coords.x+1, coords.y));
			}
			if(constructedNodes.containsKey(new Position(coords.x-1, coords.y))) { 
				neighbours.add(new Position(coords.x-1, coords.y));
			}
			
			for(Position neighbour : neighbours) { 
				SpaceThing spaceThing = board.getSpaceThing(neighbour.x, neighbour.y);
				if(spaceThing == null || spaceThing == this || spaceThing instanceof Mine) { 
					node.hasNeighbour(constructedNodes.get(new Position(neighbour.x, neighbour.y)));
				}
			}
		}
		
		return root;
	}

	@Override
	public SpaceThingType getShipType() {
		return SpaceThingType.KamikazeBoatShip;
	}

	/*
	 * Useless for this ship type - doesn't need to turn
	 */
	@Override
	public List<Point> getObstaclesInTurnZone(ActionType direction) {
		return new ArrayList<Point>();
	}
	
	public class GridNode { 
		private Position coords;
		private GridNode parent;
		private List<GridNode> neighbours;
		private boolean visited;
		
		public GridNode(Position coords) { 
			this.coords = coords;
			parent = null;
			neighbours = new ArrayList<GridNode>();
			visited = false;
		}
		
		public void setParent(GridNode parent) { 
			this.parent = parent;
		}
		
		public GridNode getParent() { 
			return this.parent;
		}
		
		public void hasNeighbour(GridNode node) { 
			neighbours.add(node);
		}
		
		public List<GridNode> getNeighbours() { 
			return this.neighbours;
		}
		
		public void visit() { 
			this.visited = true;
		}
		
		public boolean isVisited() { 
			return visited;
		}	
		
		public Position getPosition() { 
			return this.coords;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((coords == null) ? 0 : coords.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GridNode other = (GridNode) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (coords == null) {
				if (other.coords != null)
					return false;
			} else if (!coords.equals(other.coords))
				return false;
			return true;
		}

		private KamikazeBoatShip getOuterType() {
			return KamikazeBoatShip.this;
		}
		
		
	}
	
	public class Position {
		public int x;
		public int y;
		
		public Position(int x, int y) { 
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		private KamikazeBoatShip getOuterType() {
			return KamikazeBoatShip.this;
		}

		

		
		
		
		
	}

}
