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
			return 0;
		}
		
		if(x < shipX - 2 || x > shipX + 2 || y > shipY + 2 || y < shipY - 2) { 
			this.getOwner().setActionResponse("Ship cannot move that fast");
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
			if(node.getCoords().x == x && node.getCoords().y == y) { 
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
			List<Point> path = new ArrayList<>();
			path.add(target.getCoords());
			GridNode current = target.getParent();
			while(current != root) { 
				path.add(current.getCoords());
				current = current.getParent();
			}
			Collections.reverse(path);
			
			// ensure there are no mines lol
			for(Point coord : path) { 
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
		return 0;
	}

	
	public GridNode constructMovementGraph() { 
		int x = this.getX();
		int y = this.getY();
		
		HashMap<Point, GridNode> constructedNodes = new HashMap<>();
		
		Point rootCoords = new Point(x, y);
		GridNode root = new GridNode(rootCoords);
		constructedNodes.put(rootCoords, root);
		
		for(int i = x-2; i <= x+2; i++) { 
			for(int j = y-2; j <= y+2; j++) { 
				if(!isValidIntermediateMove(i, j)) { 
					continue;
				}
				
				Point nodeCoords = new Point(i, j);
				GridNode node = constructedNodes.get(nodeCoords);
				if(node == null) { 
					node = new GridNode(nodeCoords);
					constructedNodes.put(nodeCoords, node);
				}				
				
				// north
				if(isValidIntermediateMove(i, j+1)) {
					Point northCoords = new Point(i, j+1);
					GridNode north = constructedNodes.get(northCoords);
					if(north == null) { 
						north = new GridNode(northCoords); 
						constructedNodes.put(northCoords, north);
					}
					node.hasNeighbour(north);
				}
				// south
				if(isValidIntermediateMove(i, j-1)) { 
					Point southCoords = new Point(j, j-1);
					GridNode south = constructedNodes.get(southCoords);
					if(south == null) { 
						south = new GridNode(southCoords);
						constructedNodes.put(southCoords, south);
					}
					node.hasNeighbour(south);
				}
				// east
				if(isValidIntermediateMove(i+1, j)) { 
					Point eastCoords = new Point(i+1, j);
					GridNode east = constructedNodes.get(eastCoords);
					if(east == null) { 
						east = new GridNode(eastCoords);
						constructedNodes.put(eastCoords, east);
					}
					node.hasNeighbour(east);
				}
				// west
				if(isValidIntermediateMove(i-1, j)) { 
					Point westCoords = new Point(i-1, j);
					GridNode west = constructedNodes.get(westCoords);
					if(west == null) { 
						west = new GridNode(westCoords);
						constructedNodes.put(westCoords, west);
					}
					node.hasNeighbour(west);
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
		private Point coords;
		private GridNode parent;
		private List<GridNode> neighbours;
		private boolean visited;
		
		public GridNode(Point coords) { 
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
		
		public Point getCoords() { 
			return this.coords;
		}
	}

}
