package gameLogic;

import gameLogic.Constants.OrientationType;
import state.ships.AbstractShip;

/**
 * Helper Class. 
 * This class determines if the action selected by the player is basically valid. 
 * (i.e. he does not try and move a ship outside of range). 
 * @author vikramsundaram
 *
 */
public class ActionValidator
{
	// The currentShip. 
	private static AbstractShip currentShip = null; 
	
	public static boolean validateMove(int x, int y)
	{
		OrientationType orientation = currentShip.getOrientation(); 
		
		if(orientation == OrientationType.East)
		{
			int maxFront = currentShip.getX() + currentShip.getLength() - 2 + currentShip.getSpeed();
			int maxBack = currentShip.getX() - 1; 
			int maxHeight = currentShip.getY(); 
			System.out.println(maxFront + " " + maxBack); 
			if(!(x <= maxFront && x >= maxBack && y == maxHeight))
			{
				return false; 
			}
		}
		
		if(orientation == OrientationType.West)
		{
			int maxFront = currentShip.getX() - currentShip.getLength() + 2 - currentShip.getSpeed();
			int maxBack = currentShip.getX() + 1; 
			int maxHeight = currentShip.getY(); 
			int minHeight = currentShip.getY(); 
			//System.out.println(maxFront + " " + maxBack); 
			if(!(x <= maxFront && x >= maxBack && y == maxHeight))
			{
				return false; 
			}
		}
		
		
		
		return true; 
	}
	
	/**
	 * Set The current Ship
	 */
	public static void setCurrentShip(AbstractShip ship)
	{
		currentShip = ship; 
	}
}
