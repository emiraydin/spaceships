package logic;

import logic.spacethings.AbstractShip;
import logic.spacethings.Asteroid;
import logic.spacethings.SpaceThing;
import static common.GameConstants.BOARD_HEIGHT;
import static common.GameConstants.BOARD_WIDTH;
import static common.GameConstants.MAX_ASTEROIDS;;

public class GameBoard {
	SpaceThing[][] map;
	
	public GameBoard(){
		map = new SpaceThing[BOARD_HEIGHT][BOARD_WIDTH];
	}
	
	public SpaceThing getSpaceThing(int x, int y){
		if (inBounds(x, y)){
			return map[x][y];
		} else {
			return null;
		}
	}
	public void setSpaceThing(SpaceThing sThing, int x, int y){
		if (inBounds(x, y)){
			if (map[x][y] != null){
				System.out.println("Warning: overriding spacething!");
			}
			map[x][y] = sThing;
		} else {
			System.out.println("Can't set SpaceThing out of bounds.");
		}
	}
	
	public void setSpaceThing(AbstractShip ship, int x, int y){
		//TODO: Overload setSpaceThing with ship
		for (int i = 0; i < ship.getLength(); i++){
			switch (ship.getOrientation()){
				case North:
					map[x][y-i] = ship;
					break;
				case South:
					map[x][y+i] = ship;
					break;
				case East:
					map[x+1][y] = ship;
					break;
				case West:
					map[x-1][y] = ship;
					break;
			}
		}
	}
	
	public void clearSpaceThing(int x, int y){
		if (inBounds(x, y)){
			if (map[x][y] instanceof AbstractShip){
				AbstractShip ship = (AbstractShip) map[x][y];
				for (int i = 0; i < ship.getLength(); i++){
					switch (ship.getOrientation()){
						case North:
							map[ship.getX()][ship.getY()-i] = null;
							break;
						case South:
							map[ship.getX()][ship.getY()+i] = null;
							break;
						case East:
							map[ship.getX()+1][ship.getY()] = null;
							break;
						case West:
							map[ship.getX()-1][ship.getY()] = null;
							break;
					}
				}
			} else {
				map[x][y] = null;
			}
		} else {
			System.out.println("Can't clear SpaceThing out of bounds.");
		}
	}
	
	public int getSectionAt(int x, int y, AbstractShip ship){
		if (getSpaceThing(x, y) instanceof AbstractShip){
 			if (ship.equals(getSpaceThing(x, y))){
 				
 			}
		}
		return -1;
	}
	
	public void generateAsteroids(){
		// The Count of the number of displayed Asteroids. 
		int count = 0; 
		
		while(count < MAX_ASTEROIDS)
		{
			int randX = 10 + (int) (Math.random() * ((20 - 10) + 1)); 
			int randY = 3 + (int) (Math.random() * ((27 - 3) + 1));
			
//			if(getSpaceThing(randX, randY) == null){
//			{
//				setSpaceThing(new Asteroid(), randX, randY);
//				count++; 
////				asteroidField[randX][randY] = new Asteroid(randX, randY); 
//			}
		}
	}
	
	public boolean isBlocked(int x, int y){
		return (map[x][y] != null);
	}
	
	public boolean handleMineExplosions(){
		//TODO: Mine explosions... can this be moved?..
		return false;
	}
	
	private boolean inBounds(int x, int y){
		return (x >=0 && y >= 0 && x < BOARD_WIDTH && y < BOARD_HEIGHT);
	}

}
