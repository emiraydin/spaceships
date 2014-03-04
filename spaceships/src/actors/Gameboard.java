package actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * This class aggregates all the sprites on Screen. 
 * @author Vikram
 *
 */
public class Gameboard 
{
	private final int HEIGHT = 30, WIDTH = 30; 
	public Tile[][] TILE_LAYER = new Tile[HEIGHT][WIDTH]; 				// The Game Tile Layer. 
	public Actor[][] OBJECT_LAYER = new Actor[HEIGHT][WIDTH]; 			// The actual gameplay objects layer
	private Stage STAGE; 
	
	/**
	 * Constructor.
	 * Currently initializes all the tiles. 
	 */
	public Gameboard(Stage stage)
	{
		// Set the Stage to draw on. 
		this.STAGE = stage; 
		
	}
	



	/**
	 * Updates the Sprites on the screen. 
	 */
	public void update(float delta)
	{
		
	}
	
	/**
	 * Moves sprite to appropriate tile
	 */
	public void moveTo(Actor actor, float x, float y, float delta)
	{
		if (actor.getX() < x)
		{
			actor.moveBy(delta, 0);
		}
		if (actor.getX() > x)
		{
			actor.moveBy(-delta, 0);
		}
		if( actor.getY() < y)
		{
			actor.moveBy(0, delta);
		}
		if (actor.getY() > y)
		{
			actor.moveBy(0, -delta);
		}
	}
}

