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
	private Tile[][] TILE_LAYER = new Tile[HEIGHT][WIDTH]; 	// The Game Tile Layer. 
	private Actor[][] OBJECT_LAYER = new Actor[HEIGHT][WIDTH]; 
	
	/**
	 * Constructor.
	 * Currently initializes all the tiles. 
	 */
	public Gameboard()
	{
		// Initialize the Tile Layer 
		initTiles(); 
	}
	

	/**
	 * Initializes the TileLayer
	 */
	private void initTiles()
	{
		for(int i = 0; i < HEIGHT; i++)
		{
			for(int k = 0; k < WIDTH; k++)
			{
				TILE_LAYER[i][k] = new Tile(i,k); 
			}
		}
	}

	/**
	 * Resets the Stage to the basic gameBoard. 
	 * @param stage
	 */
	public void initStage(Stage stage) 
	{
		for(int i = 0; i < HEIGHT; i++)
		{
			for(int k = 0; k < WIDTH; k++)
			{
				stage.addActor(TILE_LAYER[i][k]); 
			}
		}
		
		for(int i = 0; i < 20; i ++)
		{
			int randomX = 10 + (int)(Math.random() * ((20 - 10) + 1));
			int randomY = 10 + (int)(Math.random() * ((20 - 10) + 1));
			if(OBJECT_LAYER[randomX][randomY] == null)
			{
				OBJECT_LAYER[randomX][randomY] = new Asteroid(randomX, randomY); 
				stage.addActor(OBJECT_LAYER[randomX][randomY]);
			}
			System.out.println(randomX + " " + randomY);
		}
		
		
		Ship t = new Ship(0,0);
		stage.addActor(t); 
		OBJECT_LAYER[0][0] = t; 
	}

	
	/**
	 * Updates the Sprites on the screen. 
	 */
	public void update(float delta)
	{
		moveTo(OBJECT_LAYER[0][0], 5, 5, delta);
	}
	
	/**
	 * Moves sprite to appropriate tile
	 */
	public void moveTo(Actor actor, float x, float y, float delta)
	{
		if (actor.getX() < x)
		{
			actor.moveBy(delta, 0);
			System.out.println(actor.getX()); 
		}
		if(actor.getX() >= x && actor.getY() < y)
		{
			actor.moveBy(0, delta);
		}
	}
}

