package actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * The background for the background pane. 
 * @author Vikram
 *
 */
public class uiBackground extends Image
{
	private Sprite sprite; 
	private int width = Gdx.graphics.getWidth() / 2 - 50, height = (int) (Gdx.graphics.getHeight() * 0.75); 
	
	
	public uiBackground()
	{
		sprite = new Sprite(generateBackground()); 
		setWidth(width); 
		setHeight(height);
		setPosition(Gdx.graphics.getWidth() - width - 10,Gdx.graphics.getHeight() - height - 75); 
	}


	private Texture generateBackground()
	{
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		// Draw a cyan-colored border around square
		pixmap.setColor(0, 0, 1, 0.25f);
		pixmap.fill(); 
		pixmap.drawRectangle(0, 0, width, height);
		Texture newTexture = new Texture(pixmap);
		
		return newTexture;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
}
