package actors;

import gameLogic.Constants;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Represents the in game asteroid actors. 
 * Drawn with a width and height of 32 pixels. (Declared in Constants). 
 */
public class AsteroidActor extends Image
{
	private Sprite SPRITE; 
	private final int WIDTH = Constants.PIXEL_WIDTH, HEIGHT = Constants.PIXEL_HEIGHT;

	/**
	 * Create a new asteroid image at the game board location (x,y). 
	 * @param x
	 * @param y
	 */
	public AsteroidActor(int x, int y) 
	{
		SPRITE = new Sprite(generateAsteroidTexture());
		setPosition(x,y);
		setWidth(1);
		setHeight(1);
	}
	
	/**
	 * Generate and return the texture for the asteroid object.
	 * Currently we are just creating a colored square, can be extended for images. 
	 * @return The colored asteroid image. 
	 */
	private Texture generateAsteroidTexture() 
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		pixmap.setColor(82/255f, 69/255f, 57/255f, 1);
		pixmap.fill(); 
		Texture newTexture = new Texture(pixmap);
		
		return newTexture;
	}

	/**
	 * Draw the actual asteroid to the screen. 
	 */
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(SPRITE, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
}
