package screens;

import state.GameState;
import util.TCPClient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import common.GameConstants.PlayerNumber;

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
	private GameScreenUiController uiController; 
	private GameScreenController controller; 
	private GameScreenRenderer 	 renderer ; 
	//private FPSLogger fpsLog = new FPSLogger(); 
	public static boolean canStart = false;
	
	public GameScreen()
	{
		
	}
	
	/**
	 * Run. 
	 * @param delta
	 */
	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(255/255.0f, 255/255.0f, 255/255.0f, 255/255.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Update and Render the Game World. 
		controller.update(delta); 
		
		// Update the Ui. 
		uiController.update(delta); 

		renderer.render();
		
		// Display the current FPS.
		//fpsLog.log(); 
	}

	@Override
	public void resize(int width, int height) 
	{
		 //stage.setViewport(width, height, true);

	}

	@Override
	public void show() 
	{
		while(true)
		{
			if(TCPClient.canStart == false && GameState.getAllSpaceThings().keySet().size() < 50)
			{
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				} 
			}
			else
			{
				break; 
			}
		}
		
		
		// Create and Initialize GameScreenController. 
		if(GameState.getPlayerId() == 0)
		{
			controller = new GameScreenController(PlayerNumber.PlayerOne);
//			System.out.println("I am player One!"); 
		}
		else if(GameState.getPlayerId() == 1)
		{
			controller = new GameScreenController(PlayerNumber.PlayerTwo);
			System.out.println("I am player Two!"); 
		}
		else
		{
			System.out.println("ERROR The Player Numbers are bugging out. Try restarting the server!");
		}
		
		
		// All the UI Stuff. 
		uiController = new GameScreenUiController(controller); 
		
		// Create and Initialize GameScreenRenderer. 
		renderer = new GameScreenRenderer(controller, uiController);
		
		// controller will handle user input. 
		Gdx.input.setInputProcessor(new InputMultiplexer(controller, controller.STAGE, uiController.uiStage));

		
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
