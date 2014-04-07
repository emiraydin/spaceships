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
 * This class is just a 32x32 grey Tile that allows the player to see which sections of
 * the screen he is able to see. 
 * @author vikramsundaram
 *
 */
public class RadarDisplayActor extends Image
{
	// Pixel Width and Height
	private final int WIDTH = Constants.PIXEL_WIDTH, HEIGHT = Constants.PIXEL_HEIGHT; 
	
	// Sprite that is drawn on Screen
	private Sprite SPRITE;  
	
	public RadarDisplayActor(int x, int y)
	{
		Texture texture = generateStandardTile(); 
		SPRITE = new Sprite(texture);
		setPosition(x,y);
		setWidth(1);
		setHeight(1);
	}
	
	private Texture generateStandardTile()
	{
		Pixmap p = new Pixmap(WIDTH, HEIGHT, Format.RGB888);
		p.setColor(new Color(0/255f, 0/255f, 0/255f, 0.3f)); 
		p.fill(); 
		return new Texture(p); 
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha)
	{
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(SPRITE, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
}
