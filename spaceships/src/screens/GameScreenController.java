package screens;

import gameLogic.Constants;
import actors.ActorState;
import actors.Asteroid;
import actors.BaseTile;
import actors.Gameboard;
import actors.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * This class handles all the game logic changes in the Game Screen. 
 * Created to make handling it easier and more robust. 
 * @author Vikram
 *
 */
public class GameScreenController 
{
	/******************************************************************************
	 * ****************************************************************************
	 * Instance variables 
	 * ****************************************************************************
	 ******************************************************************************/
	Gameboard BOARD; 												// The Game Board Actor, handles position of all objects. 
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
		
	}
	
	
	/*******************************************************************************
	 * Game Logic Updates
	 *******************************************************************************/
	
	/**
	 * The basic update.
	 * Checks the GAMESTATE consistently in order to know what items to draw and stuff.  
	 * @param delta
	 */
	public void update(float delta) 
	{
		// If a ship is selected, display the movement and fire range. 
		updateMovementAndFire(); 
		
		// Get player action Selection
		checkPlayerAction(); 
		
		// Confirm action with server; 
		confirmWithServer(); 
		
		// Update visibility. 
		updateVisibility(); 
		
		// Update the Camera. 
		CAMCONTROLLER.updateCamera(delta); 
		
		// Update the stage. 
		STAGE.act(); 
	}

	
	/********************************************************************************
	 * Player Control Logic
	 ********************************************************************************/
	
	/**
	 * Updates the visible game board, 
	 * Looks at the visible array in the GameState. 
	 */
	private void updateVisibility() 
	{
		// TODO Auto-generated method stub
		
	}


	/**
	 * Confirm the selected move with the server. 
	 * Asks the server to validate and do the move. 
	 */
	private void confirmWithServer() 
	{
		// TODO Auto-generated method stub
	}


	/**
	 * Method that checks to see if the player has selected any actions. 
	 */
	private void checkPlayerAction()
	{
		
		
	}


	/**
	 * Method that dictates what objects should be drawn and what objects that are left out. 
	 */
	private void updateMovementAndFire() 
	{
		// TODO Auto-generated method stub
		
	}


	/**
	 * Initializes the Asteroids.
	 * Useful so we dont keep resetting the entire stage.
	 */
	private void resetAsteroids()
	{
		
		for(Actor a : STAGE.getActors())
		{
			if(a instanceof Asteroid)
			{
				a.remove(); 
			}
		}
		
		ActorState.initializeAsteroidTiles(); 
		// Add all the asteroids to the Stage. 
		for(Asteroid[] xArray : ActorState.asteroidField)
		{
			for(Asteroid asteroid : xArray)
			{
				if(asteroid != null) STAGE.addActor(asteroid);
			}
		}		
	}
	
	
	
	/*********************************************************************************
	 * Initializing methods.
	 *********************************************************************************/
	/**
	 * Initialize the camera with the appropriate settings. 
	 */
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
		
		
		// Initialize the GameTiles 
		ActorState.initializeBoardTiles(); 
		

		for(Tile[] xArray : ActorState.boardTiles)
		{
			for(Tile tile : xArray)
			{
				STAGE.addActor(tile);
			}
		}
		
		// Initialize the Asteroids. 
		ActorState.initializeAsteroidTiles(); 
		

		for(Asteroid[] xArray : ActorState.asteroidField)
		{
			for(Asteroid asteroid : xArray)
			{
				if(asteroid != null) STAGE.addActor(asteroid);
			}
		}
		
		// Initialize the Bases. 
		ActorState.initializeBaseTiles(); 
		
		for(BaseTile[] xArray : ActorState.playerOneBase)
		{
			for(BaseTile base : xArray)
			{
				if(base != null) STAGE.addActor(base);
			}
		}
		
		for(BaseTile[] xArray : ActorState.playerTwoBase)
		{
			for(BaseTile base : xArray)
			{
				if(base != null) STAGE.addActor(base);
			}
		}
	}
}
