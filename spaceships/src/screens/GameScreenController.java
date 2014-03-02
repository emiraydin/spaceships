package screens;

import gameLogic.Constants;
import actors.Gameboard;

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
	Gameboard BOARD; 												// The Game Board Actor
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
		BOARD = new Gameboard(); 
		BOARD.initStage(STAGE); 
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
		BOARD.update(delta); 
		STAGE.act(); 
	}

}
