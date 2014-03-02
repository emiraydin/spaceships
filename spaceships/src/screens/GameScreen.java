package screens;

import actors.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * The Basic Game Screen. 
 * @author Vikram
 *
 */
public class GameScreen implements Screen 
{
	/*
	 * Instance Variables 
	 */
	private GameScreenController controller; 
	private GameScreenRenderer 	 renderer ; 	

	@Override
	public void render(float delta) 
	{
		//Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f, 0xed/255.0f, 0xff/255.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Update and Render the Game World. 
		controller.update(delta); 
		renderer.render();
	}

	@Override
	public void resize(int width, int height) 
	{
		 //stage.setViewport(width, height, true);

	}

	@Override
	public void show() 
	{
		// Create and Initialize GameScreenController. 
		controller = new GameScreenController();
		
		// Create and Initialize GameScreenRenderer. 
		renderer = new GameScreenRenderer(controller);
		
		
	}

	@Override
	public void hide() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() 
	{
		// TODO Auto-generated method stub

	}

}
