package actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The basic representation of Tiles in the GameBoard. 
 * Rendering is handled through the Actor / Stage motive.
 * @author Vikram
 *
 */
public class Tile extends Image 
{
	// Pixel Width and Height
	private final int WIDTH = 32, HEIGHT = 32; 
	
	// Texture that is drawn on Screen
	private final Sprite SPRITE;  
	
	// Position on the GameScreen
	private final Vector2 POSITION; 
	
	/**
	 * Constructor for Tile Object.
	 * Requires an x, y position
	 */
	public Tile(int x, int y)
	{
		POSITION = new Vector2(x,y); 
		Texture texture = generateStandardTile(); 
		SPRITE = new Sprite(texture);
		setPosition(x,y);
		setScale(1,1); 
		setWidth(1);
		setHeight(1);
	}
	
	/**
	 * Generates a basic Texture that is Square
	 * with blue border. 
	 * @return : A texture with blue border. 
	 */
	private Texture generateStandardTile() 
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		// Draw a cyan-colored border around square
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, WIDTH, HEIGHT);
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
