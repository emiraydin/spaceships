package actors;

import gameLogic.Constants;
import common.GameConstants.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The Base Tile Actor.
 * Knows which player it belongs to. 
 * @author Vikram
 *
 */
public class BaseTileActor extends Image 
{
	PlayerNumber currentPlayer; 														// The ID of the owning player. 
	private Sprite SPRITE; 														// The Graphic to be rendered.
	private final int WIDTH = Constants.PIXEL_WIDTH, 							// The actual pixel size. 
					  HEIGHT = Constants.PIXEL_HEIGHT;

	
	/**
	 * Constructor. 
	 * @param x : The x position of the BaseTile.
	 * @param y : The y position of the BaseTile. 
	 * @param playerId : The Id of the player who owns this tile. 
	 */
	public BaseTileActor(int x, int y, PlayerNumber currentPlayer)
	{
		if(currentPlayer == PlayerNumber.PlayerOne) 
		{
			System.out.println("TEST");
			SPRITE = new Sprite(generateBaseTile());
		}
		else 
		{
			SPRITE = new Sprite(generateOtherBaseTile());
		}
		setPosition(x,y);
		setWidth(1);
		setHeight(1);
		this.currentPlayer = currentPlayer; 
	}


	private Texture generateOtherBaseTile() 
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		pixmap.setColor(0/255f, 0/255f, 0/255f, 1f);
		//pixmap.setColor(29/255f, 3/255f, 50/255f, 1);
		pixmap.fill(); 
		Texture newTexture = new Texture(pixmap);
		
		return newTexture;
	}


	private Texture generateBaseTile() 
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		pixmap.setColor(0/255f, 0/255f, 0/255f, 1f);
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
}
