package screens;

import gameLogic.Constants;
import actors.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * This class handles all the game logic changes in the Game Screen. 
 * Created to make handling it easier and more robust. 
 * @author Vikram
 *
 */
public class GameScreenController 
{
	/*
	 * Instance variables 
	 */
	private Tile[][] GAMEBOARD = new Tile[30][30];					// TODO: Make this its own Object? 
	private OrthographicCamera CAMERA; 								// The Game Camera
	private CameraController CAMCONTROLLER; 						// Handles changing Camera views. 
	public Stage STAGE; 											// The Stage. 
	
	
	/**
	 * Constructor for GameScreenController. 
	 */
	public GameScreenController()
	{
		
		// Initialize Camera. 
		initCamera(); 
		
		// Initialize Stage. 
		initStage(); 
		
		// Initialize the Basic GameBoard. 
		initGameBoard(); 
		
	}
	
	private void initGameBoard() 
	{
		for(int i = 0; i < GAMEBOARD.length; i++)
		{
			for(int k = 0; k < GAMEBOARD[0].length; k++)
			{
				Tile t = new Tile(i,k); 
				STAGE.addActor(t);
				System.out.println(t);
			}
		}
	}

	private void initCamera() 
	{
		CAMERA = new OrthographicCamera(); 
		CAMCONTROLLER = new CameraController(CAMERA);
		CAMERA.update(); 
	}

	/**
	 * Initialize the Stage and set appropriate Viewports. 
	 */
	private void initStage()
	{
		STAGE = new Stage(); 
		STAGE.setCamera(CAMERA);
		STAGE.setViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, true);
	}

	public void update(float delta) 
	{
		CAMCONTROLLER.updateCamera(delta); 
		STAGE.act(); 
	}

}
