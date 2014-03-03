package actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Actor that aggregates ship tiles. 
 * @author Vikram
 *
 */
public class Ship extends Group 
{
	private ShipTile[] tiles = new ShipTile[4];
	
	public Ship(int x, int y)
	{
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
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        
        // Draw the individual ship tiles. 
        drawChildren(batch,parentAlpha);
	}
}
