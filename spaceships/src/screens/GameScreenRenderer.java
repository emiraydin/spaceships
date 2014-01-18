package screens;

import gameLogic.Constants;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Handles Rendering for the Game Screen. 
 * @author vikramsundaram
 *
 */
public class GameScreenRenderer
{
	// Used to get access GameLogic shit. 
	private GameScreenController controller; 
	private SpriteBatch batch; 
	
	// Das Camera
	private OrthographicCamera camera; 
	
	public GameScreenRenderer(GameScreenController controller)
	{
		this.controller = controller; 
		batch = new SpriteBatch(); 
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT); 
		camera.position.set(0,0,0); 
		camera.update(); 
	}
	
	public void render()
	{
		
		controller.cameraHelper.applyTo(camera); 
		batch.setProjectionMatrix(camera.combined); 
		
		batch.begin(); 
		controller.sprite.draw(batch); 
		batch.end(); 
	}
	
}
