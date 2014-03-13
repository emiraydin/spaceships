package actors;

import gameLogic.Constants;
import gameLogic.Constants.OrientationType;
import gameLogic.Constants.PlayerNumber;

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
public class ShipTileActor extends Image
{
	
	private Sprite SPRITE; 
	private boolean isHead = false;				// Is this currently the head of the ship.
	private boolean isDestroyed = false; 		// Is the section currently destroyed. 
	private OrientationType orientation; 
	private final int WIDTH = Constants.PIXEL_WIDTH, HEIGHT = Constants.PIXEL_HEIGHT;

	/**
	 * Create a new ship tile at the specified location. 
	 * @param x : The 'X' location of the tile. 
	 * @param y : The 'Y' location of the tile. 
	 */
	public ShipTileActor(int x, int y) 
	{
		SPRITE = new Sprite(generateUnselectedShipTexturePlayerOne());
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
	public ShipTileActor(int startX, int startY, OrientationType orientation)
	{
		this.orientation = orientation; 
		SPRITE = new Sprite(generateUnselectedHeadPlayerOne(orientation)); 
		setPosition(startX, startY); 
		setWidth(1); 
		setHeight(1); 
	}


	/** 
	 * Draws the head as unselected and facing the direction by orientaiton. 
	 * @param orientation : The direction the ship is facing. 
	 * @return : a Texture
	 */
	private Texture generateUnselectedHeadPlayerOne(OrientationType orientation)
	{
		if(orientation == OrientationType.East)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(0/255f, 45/255f, 75/255f, 1f);
			pixmap.fillTriangle(0, 0, 0, 32, 32, 32/2); 
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else if(orientation == OrientationType.West)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(0/255f, 45/255f, 75/255f, 1f);
			pixmap.fillTriangle(0, 32/2, 32, 0, 32, 32);
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else if(orientation == OrientationType.North)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(0/255f, 45/255f, 75/255f, 1f);
			pixmap.fillTriangle(0, 32, 32, 32, 32/2, 0);
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else if(orientation ==  OrientationType.South)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(0/255f, 45/255f, 75/255f, 1f);
			pixmap.fillTriangle(0, 0, 32/2, 32, 32, 0);
			
			
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
	private Texture generateSelectedHeadPlayerOne(OrientationType orientation)
	{
		if(orientation == OrientationType.East)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(0, 0, 200/255f, 1f);
			pixmap.fillTriangle(0, 0, 0, 32, 32, 32/2); 
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else if(orientation == OrientationType.West)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(0, 0, 200/255f, 1f);
			pixmap.fillTriangle(0, 32/2, 32, 0, 32, 32); 
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else if(orientation == OrientationType.North)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(0, 0, 200/255f, 1f);
			pixmap.fillTriangle(0, 32, 32, 32, 32/2, 0);
			Texture newTexture = new Texture(pixmap);
			return newTexture;
		}
		else if(orientation ==  OrientationType.South)
		{
			Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
			pixmap.setColor(0, 0, 200/255f, 1f);
			pixmap.fillTriangle(0, 0, 32/2, 32, 32, 0);
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
	private Texture generateUnselectedShipTexturePlayerOne() 
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		// Draw a cyan-colored border around square
		pixmap.setColor(0/255f, 45/255f, 75/255f, 1f);
		pixmap.fill(); 
		Texture newTexture = new Texture(pixmap);
		
		return newTexture;
	}
	
	/**
	 * Generates a selected Ship Texture
	 */
	private Texture generateSelectedBodyPlayerOne() 
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		// Draw a cyan-colored border around square
		pixmap.setColor(0, 0, 200/255f, 1f);
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


	public void drawAsNonCurrent(PlayerNumber player)
	{
		this.SPRITE.getTexture().dispose(); 
		if(isDestroyed)
		{
			drawAsDestroyed(); 
			return; 
		}
		if(player == PlayerNumber.PlayerOne)
		{
			if(isHead)
			{
				SPRITE = new Sprite(generateUnselectedHeadPlayerOne(orientation)); 
				return; 
			}
			SPRITE = new Sprite(generateUnselectedShipTexturePlayerOne()); 
		}
		else
		{
			if(isHead)
			{
				SPRITE = new Sprite(generateUnselectedHeadPlayerTwo(orientation)); 
				return; 
			}
			SPRITE = new Sprite(generateUnselectedShipTexturePlayerTwo()); 
		}
		
	}
	
	private Texture generateUnselectedShipTexturePlayerTwo()
	{
		// TODO Auto-generated method stub
		return null;
	}

	private Texture generateUnselectedHeadPlayerTwo(OrientationType orientation2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void drawAsCurrent(PlayerNumber player)
	{
		this.SPRITE.getTexture().dispose(); 
		if(isDestroyed)
		{
			drawAsDestroyed(); 
			return; 
		}
		if(player == PlayerNumber.PlayerOne)
		{

			if(isHead)
			{
				SPRITE = new Sprite(generateSelectedHeadPlayerOne(orientation)); 
				return; 
			}
			SPRITE = new Sprite(generateSelectedBodyPlayerOne()); 
		}
		
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

	/**
	 * Draws the tile as if it had been destroyed. 
	 */
	public void drawAsDestroyed()
	{
		this.SPRITE.getTexture().dispose(); 
		
		this.SPRITE = new Sprite(generateDestroyedTexture()); 
		
	}

	/**
	 * 
	 * @return
	 */
	private Texture generateDestroyedTexture()
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		// Draw a cyan-colored border around square
		pixmap.setColor(0, 0, 0, 0.4f);
		pixmap.fill(); 
		pixmap.setColor(1, 0, 0, 1f);
		pixmap.drawLine(0, 0, 32, 32); 
		pixmap.drawLine(0, 32, 32, 0); 
		Texture newTexture = new Texture(pixmap);
		
		return newTexture;
	}
	
	/**
	 * 
	 */
	public void setDestroyed(boolean b)
	{
		this.isDestroyed = b; 
	}
	
	/**
	 * 
	 */
	public boolean getDestroted()
	{
		return this.isDestroyed; 
	}

	
	public void setOrientation(OrientationType orientation2)
	{
		this.orientation = orientation2; 
		
	}

}
