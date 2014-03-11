package actors;

import gameLogic.Constants;
import gameLogic.Constants.OrientationType;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *
 * One ship tile. 
 * Used to draw each seperate part of the ship. 
 * Currently we only differentiate between the head and the stern of the ship. 
 * @author Vikram
 *
 */
public class ShipTile extends Image
{
	
	private Sprite SPRITE; 
	private boolean isHead = false;				// Is this currently the head of the ship.
	private OrientationType orientation; 
	private final int WIDTH = Constants.PIXEL_WIDTH, HEIGHT = Constants.PIXEL_HEIGHT;

	/**
	 * Create a new ship tile at the specified location. 
	 * @param x : The 'X' location of the tile. 
	 * @param y : The 'Y' location of the tile. 
	 */
	public ShipTile(int x, int y) 
	{
		SPRITE = new Sprite(generateUnselectedShipTexture());
		setPosition(x,y);
		setWidth(1);
		setHeight(1);
	}

	/**
	 * This is the constructor typically used to create a shipTile that will be the head of the ship. 
	 * @param startX : The 'x' location of the head of the ship. 
	 * @param startY : The 'y' location of the head of the ship. 
	 * @param orientation : The direction in which we need to draw the head. 
	 */
	public ShipTile(int startX, int startY, OrientationType orientation)
	{
		this.orientation = orientation; 
		SPRITE = new Sprite(generateUnselectedHead(orientation)); 
		setPosition(startX, startY); 
		setWidth(1); 
		setHeight(1); 
	}


	/** 
	 * Draws the head as unselected and facing the direction by orientaiton. 
	 * @param orientation : The direction the ship is facing. 
	 * @return : a Texture
	 */
	private Texture generateUnselectedHead(OrientationType orientation)
	{
		if(orientation == OrientationType.East)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(0, 0, 0, 1);
			pixmap.fill(); 
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else if(orientation == OrientationType.West)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(0, 0, 0, 1);
			pixmap.fill(); 
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else if(orientation == OrientationType.North)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(0, 0, 0, 1);
			pixmap.fill(); 
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else if(orientation ==  OrientationType.South)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(0, 0, 0, 1);
			pixmap.fill(); 
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else
		{
			return null;
		}

	}
	
	/**
	 * Draws the head as a selected and facing the direction by orientation. 
	 * @param orientation : The direction the ship is facing. 
	 * @return : a Texture. 
	 */
	private Texture generateSelectedHead(OrientationType orientation)
	{
		if(orientation == OrientationType.East)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(1, 0, 0, 1);
			pixmap.fill(); 
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else if(orientation == OrientationType.West)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(1, 0, 0, 1);
			pixmap.fill(); 
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else if(orientation == OrientationType.North)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(1, 0, 0, 1);
			pixmap.fill(); 
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else if(orientation ==  OrientationType.South)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(1, 0, 0, 1);
			pixmap.fill(); 
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else
		{
			return null;
		}
	}


	
	/**
	 * Generates an unselectedShipTexture
	 * @return
	 */
	private Texture generateUnselectedShipTexture() 
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		// Draw a cyan-colored border around square
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fill(); 
		Texture newTexture = new Texture(pixmap);
		
		return newTexture;
	}
	
	/**
	 * Generates a selected Ship Texture
	 */
	private Texture generateBlueShipTexture() 
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		// Draw a cyan-colored border around square
		pixmap.setColor(0, 0, 1, 1);
		pixmap.fill(); 
		Texture newTexture = new Texture(pixmap);
		
		return newTexture;
	}



	@Override
	public void draw(Batch batch, float parentAlpha)
	{
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(SPRITE, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}


	public Sprite getSprite() {
		return SPRITE; 
	}


	public void drawAsNonCurrent()
	{
		this.SPRITE.getTexture().dispose(); 
		if(isHead)
		{
			SPRITE = new Sprite(generateUnselectedHead(orientation)); 
			return; 
		}
		SPRITE = new Sprite(generateUnselectedShipTexture()); 
	}
	
	public void drawAsCurrent()
	{
		this.SPRITE.getTexture().dispose(); 
		if(isHead)
		{
			SPRITE = new Sprite(generateSelectedHead(orientation)); 
			return; 
		}
		SPRITE = new Sprite(generateBlueShipTexture()); 
	}
	
	/**
	 * Sets whether this tile is the head of the ship or not. 
	 */
	public void setIsHead(boolean b)
	{
		this.isHead = b; 
	}
	
	/**
	 * Returns whether this tile is the head of the ship or not. 
	 */
	public boolean isHead()
	{
		return isHead; 
	}

}
