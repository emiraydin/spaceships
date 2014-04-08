package actors;

import gameLogic.Constants;
import state.Mine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * This is the Actor that represents a MineActor. 
 * This is what the player will see when deploying and doing shit with mines. 
 * @author vikramsundaram
 *
 */
public class MineActor extends Image
{
	// Pixel Width and Height
		private final int WIDTH = Constants.PIXEL_WIDTH, HEIGHT = Constants.PIXEL_HEIGHT; 
		
		// Sprite that is drawn on Screen
		private Sprite SPRITE;  
		
		public Mine mine; 
		
		public MineActor(int x, int y,  Mine mine)
		{
			Texture texture = generateStandardTile(); 
			SPRITE = new Sprite(texture);
			setPosition(x,y);
			setWidth(1);
			setHeight(1);
			this.mine = mine; 
		}
		
		private Texture generateStandardTile()
		{
			Pixmap p = new Pixmap(WIDTH, HEIGHT, Format.RGB888);
			p.setColor(new Color(176/255f, 48/255f, 96/255f, 1f)); 
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
