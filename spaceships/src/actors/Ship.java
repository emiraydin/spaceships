package actors;

import gameLogic.Constants.OrientationType;
import state.ships.AbstractShip;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Actor that aggregates ship tiles. 
 * Helps in the manipulation of ship objects. 
 * @author Vikram
 *
 */
public class Ship extends Group 
{
	private ShipTile[] tiles;									// The array of Tile Objects this ship occupies. 
	public AbstractShip ship; 									// The Model Ship that represents the current state. 
	private boolean isCurrent = false; 							// Is this ship currently being selected? 
	private OrientationType orientation; 						// The heading the ship is facing. 
	
	/**
	 * Create a new ship where the back end is at position (x,y). 
	 * @param x: The 'x' position of the backend of the ship. 
	 * @param y: The 'y' position of the backend of the ship. 
	 * @param ship: The ModelShip that this ship represents. 
	 */
	public Ship(int x, int y, AbstractShip ship)
	{
		
		tiles = new ShipTile[ship.getLength()]; 
		this.ship = ship;
		// Create a new Ship with the tiles in the appropriate places
		initShip(x, y);
	}

	
	/**
	 * Initialize the ship at the appropriate location. 
	 * Add the ShipTiles to the group this ship aggregates. 
	 * TODO: Initialize the ship based on position and location (East or West facing?) 
	 * @param startX
	 * @param startY
	 */
	private void initShip(int startX, int startY) 
	{
		this.orientation = ship.getOrientation(); 
		
		for(int i = 0; i < tiles.length; i++)
		{
			// Set the head of the ship
			if(i == tiles.length - 1)
			{
				tiles[i] = new ShipTile(startX, startY, orientation); 
				tiles[i].setIsHead(true); 
			}
			else
			{
				tiles[i] = new ShipTile(startX, startY);
			}
			startX ++; 
			addActor(tiles[i]);
		}
		

	}
	
	/**
	 * Move the ship by the x,y coordinates. 
	 * Typically called in the controller to move the all parts of the ship to new locations. 
	 */
	public void moveBy(float x, float y)
	{
		for(ShipTile tile : tiles)
		{
			tile.moveBy(x, y);
		}
	}
	

	/**
	 * Get the X location of the ship. 
	 */
	public float getX()
	{
		return tiles[0].getX();
	}
	
	/**
	 * Get the Y location of the ship. 
	 */
	public float getY()
	{
		return tiles[0].getY();
	}
	
	/**
	 * Render this ship as if it is NOT currently selected. 
	 */
	public void drawAsNonCurrent()
	{
		for(ShipTile child : tiles)
		{
			child.drawAsNonCurrent(); 
		}
	}
	
	/**
	 * Render this ship as if it IS currently selected. 
	 */
	public void drawAsCurrent()
	{
		for(ShipTile child : tiles)
		{
			child.drawAsCurrent(); 
		}
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        
        // Draw the individual ship tiles. 
        drawChildren(batch,parentAlpha);
	}

	/**
	 * Set this ship as the current ship.
	 * @param b : True if this ship is currently selected, false otherwise. 
	 */
	public void setCurrentShip(boolean b) 
	{
		this.isCurrent = b; 
	}
	
	/**
	 * Gets whether this ship is currently selected or not. 
	 * @return : boolean: is Currently Selected? 
	 */
	public boolean getIsCurrent()
	{
		return this.isCurrent; 
	}

	/**
	 * Set the orientation of the ship to the orientation of the model ship.
	 * Redraws the ship so that it faces the correct orientation.  
	 */
	public void setOrientation(OrientationType o)
	{
		// Update tha actors orientation 
		this.orientation = o; 
		
		// Get the required information from the ship model. 
		int xLocation = ship.getX(); 
		int yLocation = ship.getY(); 
		
		
		// Redraw the onscreen sprite so they reflect the orientation change. 
		if(this.orientation == OrientationType.East)
		{
			for(ShipTile tile : tiles)
			{
				tile.setX(xLocation); 
				tile.setY(yLocation); 
				xLocation++; 
			}
		}
		if(this.orientation == OrientationType.West)
		{
			for(ShipTile tile : tiles)
			{
				tile.setX(xLocation); 
				tile.setY(yLocation); 
				xLocation--; 
			}
		}
		if(this.orientation == OrientationType.North)
		{
			for(ShipTile tile : tiles)
			{
				tile.setX(xLocation); 
				tile.setY(yLocation); 
				yLocation++; 
			}
		}
		if(this.orientation == OrientationType.South)
		{
			for(ShipTile tile : tiles)
			{
				tile.setX(xLocation); 
				tile.setY(yLocation); 
				yLocation--; 
			}
		}
		
	}
	
	/**
	 * Gets the ship current orientation 
	 */
	public OrientationType getOrientation()
	{
		return this.orientation; 
	}

}
