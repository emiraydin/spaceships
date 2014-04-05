package actors;

import gameLogic.Constants;
import screens.GameSetupScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Hacky class. I'm using this in order to be able to display the GameSetup Tile Actors. 
 * This lets us give them different actions if they are clicked. 
 * @author vikramsundaram
 *
 */
public class GameSetupTileActor extends Image 
{
	// Pixel Width and Height 
	private final int WIDTH = Constants.PIXEL_WIDTH, HEIGHT = Constants.PIXEL_HEIGHT; 
	
	// The Sprite that will be draw on the Screen. 
	private Sprite sprite; 
	
	// Can this tile still be selected?
	private boolean selectable = true; 
	
	// The GameSetupScreen this is associated with. 
	private GameSetupScreen theScreen; 
	
	
	/**
	 * Create a new GameSetupTileActor located at the specified x,y.
	 */
	public GameSetupTileActor(int x, int y, GameSetupScreen fuckMeThisIsHacky)
	{ 
		theScreen = fuckMeThisIsHacky; 
		sprite = new Sprite(generateStandardTexture()); 
		setPosition(x,y); 
		setSize(1,1); 
		
		addListener(new ClickListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				theScreen.changeSelectedTile(getThisTile()); 
				return false;
			}
		}); 
	}
	
	private Texture generateStandardTexture()
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888); 
		
		// Draw a coloured border around the tile. 
		pixmap.setColor(1,1,1,1f);  
		pixmap.drawRectangle(0, 0, WIDTH, HEIGHT); 
		Texture texture = new Texture(pixmap); 
		pixmap.dispose(); 
		
		return texture; 
	}
	
	private Texture generateSelectableTexture()
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888); 
		
		// Draw a coloured border around the tile. 
		pixmap.setColor(1,1,1,0.2f);  
		pixmap.fill(); 
		pixmap.setColor(1,1,1,1); 
		pixmap.drawRectangle(0, 0, WIDTH, HEIGHT);
		Texture texture = new Texture(pixmap); 
		pixmap.dispose(); 
		
		return texture; 
	}
	
	private Texture generateNotSelectableTexture()
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888); 
		
		// Draw a coloured border around the tile. 
		pixmap.setColor(1,1,1,0.4f);  
		pixmap.fill(); 
		pixmap.setColor(1,1,1,1); 
		pixmap.drawRectangle(0, 0, WIDTH, HEIGHT);
		Texture texture = new Texture(pixmap); 
		pixmap.dispose(); 
		
		return texture; 
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}

	public void drawSelectable()
	{
		sprite.getTexture().dispose(); 
		sprite = new Sprite(generateSelectableTexture()); 	
		selectable = true; 
	}
	
	public void drawNotSelectable()
	{
		sprite.getTexture().dispose(); 
		sprite = new Sprite(generateNotSelectableTexture()); 
		selectable = false; 
	}
	
	public void drawStandard()
	{
		sprite.getTexture().dispose(); 
		sprite = new Sprite(generateStandardTexture()); 	
		selectable = false; 
	}
	
	public GameSetupTileActor getThisTile()
	{
		return this; 
	}

	public String toString()
	{
		return this.getX() + " ,"+ this.getY(); 
	}
}
