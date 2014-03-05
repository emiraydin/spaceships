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
 * One Ship Tile.
 * Pretty much a testing class at the moment, since we dont have actually assets. 
 * @author Vikram
 *
 */
public class ShipTile extends Image
{
	
	private Sprite SPRITE; 
	private final int WIDTH = Constants.PIXEL_WIDTH, HEIGHT = Constants.PIXEL_HEIGHT;

	public ShipTile(int x, int y) 
	{
		SPRITE = new Sprite(generateShipTexture());
		setPosition(x,y);
		setWidth(1);
		setHeight(1);
	}
	

	private Texture generateShipTexture() 
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		// Draw a cyan-colored border around square
		pixmap.setColor(1, 1, 1, 1);
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


	public void drawAsWhite()
	{
		this.SPRITE.getTexture().dispose(); 
		SPRITE = new Sprite(generateShipTexture()); 
	}
	
	public void drawAsBlue()
	{
		this.SPRITE.getTexture().dispose(); 
		SPRITE = new Sprite(generateBlueShipTexture()); 
	}


	private Texture generateBlueShipTexture() 
	{
		Pixmap pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		// Draw a cyan-colored border around square
		pixmap.setColor(0, 0, 1, 1);
		pixmap.fill(); 
		Texture newTexture = new Texture(pixmap);
		
		return newTexture;
	}

}
