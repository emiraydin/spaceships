package actors;

import gameLogic.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * This is the actor that just draws the background on the gameboard.
 * It is used so we can control the position and semantic location. 
 * @author Vikram
 *
 */
public class BackgroundActor extends Image 
{
	private final int WIDTH = Constants.APP_WIDTH, HEIGHT = Constants.APP_HEIGHT; 
	private Sprite SPRITE; 
	private String location = "images/background.png";
	
	/**
	 * Create the Background and set the position to start just off the game screen. 
	 * Width and Height are in in game units. 
	 */
	public BackgroundActor()
	{
		importBackground(); 
		setWidth(75);
		setHeight(55); 
		setPosition(-5,-20);
		
	}

	/**
	 * Import the background from file.
	 */
	private void importBackground() 
	{
		Texture texture = new Texture(Gdx.files.internal(location));
		
		SPRITE = new Sprite(texture); 
	}
	
	
	/**
	 * Draw the background
	 */
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, 0.9f);
        batch.draw(SPRITE, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
}
