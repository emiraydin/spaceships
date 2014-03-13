package actors;

import gameLogic.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * The basic representation of Tiles in the GameBoard. 
 * Rendering is handled through the Actor / Stage motive.
 * @author Vikram
 *
 */
public class TileActor extends Image 
{
	// Pixel Width and Height
	private final int WIDTH = Constants.PIXEL_WIDTH, HEIGHT = Constants.PIXEL_HEIGHT; 
	
	// Sprite that is drawn on Screen
	private Sprite SPRITE;  
	
	// Position on the GameScreen
	private final Vector2 POSITION; 
	
	// Just for testing. 
	
	/**
	 * Constructor for Tile Object.
	 * Requires an x, y position
	 */
	public TileActor(int x, int y)
	{
		POSITION = new Vector2(x,y); 
		Texture texture = generateStandardTile(); 
		SPRITE = new Sprite(texture);
		setPosition(x,y);
		setWidth(1);
		setHeight(1);
		
		setUpListeners(); 
		

	}
	
	private void setUpListeners()
	{
		addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				ActorState.setCurrentTile(getThisTile()); 
				return false;
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
			//	drawAsGreen(); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
			//	drawAsBlue(); 
			}
		});
		
	}

	private TileActor getThisTile()
	{
		return this; 
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
		pixmap.setColor(0, 1, 1, 0.4f);
		pixmap.drawRectangle(0, 0, WIDTH, HEIGHT);
		Texture newTexture = new Texture(pixmap);
		pixmap.dispose(); 
		return newTexture;
	}
	
	/**
	 * Generates a basic Texture that is Square
	 * with a red fill. 
	 * @return : A red Texture. 
	 */
	private Texture generateStandardRedTile() 
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		// Draw a cyan-colored border around square
		pixmap.setColor(1, 0, 0, 0.25f);
		pixmap.fill(); 
		//pixmap.drawRectangle(0, 0, WIDTH, HEIGHT);
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, WIDTH, HEIGHT);
		Texture newTexture = new Texture(pixmap);
		pixmap.dispose(); 
		return newTexture;
	}
	
	/**
	 * Generates a basic Texture that is Square
	 * with a red border
	 * @return : A red Texture. 
	 */
	private Texture generateStandardRedBorderTile() 
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		// Draw a cyan-colored border around square
		pixmap.setColor(1, 0, 0, 1f);
		//pixmap.fill(); 
		pixmap.drawRectangle(0, 0, WIDTH, HEIGHT);
		//pixmap.setColor(0, 1, 1, 1);
		//pixmap.drawRectangle(0, 0, WIDTH, HEIGHT);
		Texture newTexture = new Texture(pixmap);
		pixmap.dispose(); 
		return newTexture;
	}
	
	/**
	 * Generates a basic Texture that is Square
	 * with a red fill. 
	 * @return : A red Texture. 
	 */
	private Texture generateStandardGreenTile() 
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		// Draw a cyan-colored border around square
		pixmap.setColor(0, 1, 0, 0.25f);
		pixmap.fill(); 
		//pixmap.drawRectangle(0, 0, WIDTH, HEIGHT);
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, WIDTH, HEIGHT);
		Texture newTexture = new Texture(pixmap);
		pixmap.dispose(); 
		return newTexture;
	}

	public void drawAsRed()
	{
		SPRITE.getTexture().dispose(); 
		SPRITE = new Sprite(generateStandardRedTile()); 
	}
	
	public void drawAsRedBorder() {
		SPRITE.getTexture().dispose();
		SPRITE = new Sprite(generateStandardRedBorderTile()); 
		
	}
	
	public void drawAsBlue()
	{
		SPRITE.getTexture().dispose(); 
		SPRITE = new Sprite(generateStandardTile()); 
	}
	
	public void drawAsGreen()
	{
		SPRITE.getTexture().dispose();
		SPRITE = new Sprite(generateStandardGreenTile()); 
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(SPRITE, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
	
	


}
