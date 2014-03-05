package actors;

import gameLogic.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The Background for the Game Board.
 * @author Vikram
 *
 */
public class Background extends Image 
{
	// Pixel Width and Height
	private final int WIDTH = Constants.APP_WIDTH, HEIGHT = Constants.APP_HEIGHT; 
	private Sprite SPRITE; 
	private String location = "images/background.png";
	
	public Background()
	{
		importBackground(); 
		setWidth(75);
		setHeight(55); 
		setPosition(-5,-20);
	}

	private void importBackground() 
	{
		Texture texture = new Texture(Gdx.files.internal(location));
		
		SPRITE = new Sprite(texture); 
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(SPRITE, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
}
