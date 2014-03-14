package gameLogic;

import actors.ActorState;

import common.GameConstants.*;

import state.ships.AbstractShip;
import state.ships.TorpedoShip;

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
			int maxFront = currentShip.getX() + currentShip.getSpeed(); 
			int maxBack = currentShip.getX() - 1; 
			int maxHeight = currentShip.getY() + 1; 
			int minHeight = currentShip.getY() - 1; 
			
			if(!(x <= maxFront && x >= maxBack && y <= maxHeight && y >= minHeight))
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
			if(!(x >= maxFront && x <= maxBack && y == maxHeight))
			{
				return false; 
			}
		}
		
		if(orientation == OrientationType.North)
		{
			int maxUp = currentShip.getY() + currentShip.getLength() - 2 + currentShip.getSpeed(); 
			int maxDown = currentShip.getY() - 1; 
			int maxSide = currentShip.getX(); 
			
			if(!(y <= maxUp && y >= maxDown && x == maxSide))
			{
				return false; 
			}
		}
		
		if(orientation == OrientationType.South)
		{
			int maxUp = currentShip.getY() - currentShip.getLength() + 2 + currentShip.getSpeed(); 
			int maxDown = currentShip.getY() + 1; 
			int maxSide = currentShip.getX(); 
			
			if(!(y >= maxUp && y <= maxDown && x == maxSide))
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

	public static boolean validateShot(int x, int y)
	{	
		// Get the actor and model ships, and draw their appropriate rectangles. 
		int length = currentShip.getLength(); 
		int speed = currentShip.getSpeed(); 
		int cannonLength = currentShip.getCannonLength(); 
		int cannonWidth = currentShip.getCannonWidth(); 
		int xPos = currentShip.getX(); 
		int yPos = currentShip.getY(); 
		OrientationType orientation = currentShip.getOrientation(); 
		int shipBack = xPos; 
		
		// The drawing is different depending on the orientation. 
		if(orientation == OrientationType.East)
		{
			if(currentShip instanceof TorpedoShip)
			{
				int xStart = xPos; 
				int xEnd = cannonLength; 
				int yStart = yPos - 2; 
				int yEnd = yPos + 2; 
				
				if(!(x <= xEnd && x >= xStart && y <= yEnd && y >= yStart))
				{
					return false; 
				}
			}
			else
			{
				// Get the front of the ship. 
				int shipFront = xPos + length - 1; 
				
				// Get the boundaries
				int middleTile = (shipBack + shipFront) / 2; 
				int xStart = middleTile - cannonLength / 2; 
				int xEnd = xStart + cannonLength; 
				int yStart = yPos - (cannonWidth / 2); 
				int yEnd = yStart + cannonWidth; 
								
				if(!(x <= xEnd && x >= xStart && y <= yEnd && y >= yStart))
				{
					return false; 
				}
			}
			
			return true;
		}
		
		if(orientation == OrientationType.West)
		{
			if(currentShip instanceof TorpedoShip)
			{
				
				int xStart = xPos; 
				int xEnd = xPos - cannonLength; 
				int yStart = yPos - 2; 
				int yEnd = yPos + 2; 
				
				if(!(x >= xEnd && x <= xStart && y <= yEnd && y >= yStart))
				{
					return false; 
				}
			}
			else
			{
				// Get the front of the ship. 
				int shipFront = xPos - length + 1; 
				
				// Get the boundaries
				int middleTile = (shipBack + shipFront) / 2; 
				int xStart = middleTile + cannonLength / 2; 
				int xEnd = xStart - cannonLength; 
				int yStart = yPos - (cannonWidth / 2); 
				int yEnd = yStart + cannonWidth; 
								
				if(!(x >= xEnd && x <= xStart && y <= yEnd && y >= yStart))
				{
					return false; 
				}
			}
			return true; 
		}
		
		if(orientation == OrientationType.North)
		{
			if(currentShip instanceof TorpedoShip)
			{
				int xStart = xPos - 2; 
				int xEnd = xPos + 2; 
				int yStart = yPos; 
				int yEnd = yPos + cannonLength; 
				
				if(!(x <= xEnd && x >= xStart && y <= yEnd && y >= yStart))
				{
					return false; 
				}
			}
			else
			{
				// Get the front of the ship. 
				int shipFront = yPos + length - 1; 
				
				// Get the boundaries
				int middleTile = (shipBack + shipFront) / 2; 
				int yStart = middleTile - cannonLength / 2; 
				int yEnd = yStart + cannonLength; 
				int xStart = xPos - (cannonWidth / 2); 
				int xEnd = xStart + cannonWidth; 
								
				if(!(x <= xEnd && x >= xStart && y <= yEnd && y >= yStart))
				{
					return false; 
				}
			}
			return true; 
		}
		
		if(orientation == OrientationType.South)
		{
			if(currentShip instanceof TorpedoShip)
			{
				int xStart = xPos - 2; 
				int xEnd = xPos + 2; 
				int yStart = yPos; 
				int yEnd = yPos - cannonLength; 
				
				if(!(x <= xEnd && x >= xStart && y >= yEnd && y <= yStart))
				{
					return false; 
				}
			}
			else
			{
				// Get the front of the ship. 
				int shipFront = yPos - length + 1; 
				
				// Get the boundaries
				int middleTile = (shipBack + shipFront) / 2; 
				int yStart = middleTile + cannonLength / 2; 
				int yEnd = yStart - cannonLength; 
				int xStart = xPos - (cannonWidth / 2); 
				int xEnd = xStart + cannonWidth; 
								
				if(!(x <= xEnd && x >= xStart && y >= yEnd && y <= yStart))
				{
					return false; 
				}
			}
			return true; 
		}
		return false;
	}
}
