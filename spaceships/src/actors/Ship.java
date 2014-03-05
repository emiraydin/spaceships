package actors;

import state.ships.AbstractShip;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Actor that aggregates ship tiles. 
 * @author Vikram
 *
 */
public class Ship extends Group 
{
	private ShipTile[] tiles;
	public AbstractShip ship; 
	private boolean isCurrent = false; 
	
	public Ship(int x, int y, AbstractShip ship)
	{
		
		tiles = new ShipTile[ship.getLength()]; 
		this.ship = ship;
		// Create a new Ship with the tiles in the appropriate places
		initShip(x, y);
	}

	private void initShip(int startX, int startY) 
	{
		for(int i = 0; i < tiles.length; i++)
		{
			tiles[i] = new ShipTile(startX, startY);
			startX ++; 
			addActor(tiles[i]);
		}
	}
	
	public void moveBy(float x, float y)
	{
		for(ShipTile tile : tiles)
		{
			tile.moveBy(x, y);
		}
	}
	

	public float getX()
	{
		return tiles[0].getX();
	}
	
	public float getY()
	{
		return tiles[0].getY();
	}
	
	public void drawAsWhite()
	{
		for(ShipTile child : tiles)
		{
			child.drawAsWhite(); 
		}
	}
	
	public void drawAsBlue()
	{
		for(ShipTile child : tiles)
		{
			child.drawAsBlue(); 
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

	public void setCurrentShip(boolean b) 
	{
		this.isCurrent = b; 
	}
	
	public boolean getIsCurrent()
	{
		return this.isCurrent; 
	}

}
