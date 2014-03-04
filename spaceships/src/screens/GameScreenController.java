package screens;

import gameLogic.Constants;
import state.ships.AbstractShip;
import actors.ActorState;
import actors.Asteroid;
import actors.BaseTile;
import actors.Gameboard;
import actors.Ship;
import actors.ShipTile;
import actors.Tile;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * This class handles all the game logic changes in the Game Screen. 
 * Created to make handling it easier and more robust. 
 * @author Vikram
 *
 */
public class GameScreenController implements InputProcessor
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
	private int CURRENT_SELECTION = -1; 								// The currently selected ship
	
	
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
		updateMovementAndFire(delta); 
		
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
		// Display the movement and fire range for a ship. 
		if(CURRENT_SELECTION != -1)
		{
			Ship currentShip = ActorState.shipList.get(CURRENT_SELECTION); 
			currentShip.setCurrentShip(true); 
			
			for(Ship ship : ActorState.shipList)
			{
				if(ship == currentShip) continue; 
				else ship.setCurrentShip(false);
			}
			
			drawShipBoundaries(); 
			
		}
		else if (CURRENT_SELECTION == -1)
		{
			for(Ship ship : ActorState.shipList)
			{
				ship.setCurrentShip(false);
			}
			
			drawShipBoundaries(); 
		}
	}
	
	/**
	 * Method that draws the appropriate selectable boundaries. 
	 * Uses the information stored in the MODEL so as to not display incorrect information. 
	 */
	private void drawShipBoundaries()
	{
		for(Ship theShip : ActorState.shipList)
		{
			if(theShip.getIsCurrent())
			{
				AbstractShip ship = theShip.ship; 
				int shipSpeed = ship.getSpeed(); 
				int xPosition = ship.getX(); 
				int yPosition = ship.getY(); 
				
				for(int i = xPosition; i < xPosition + shipSpeed; i++)
				{
					ActorState.boardTiles[i][yPosition].drawAsRed();
				}
			}
			else
			{
				AbstractShip ship = theShip.ship; 
				int shipSpeed = ship.getSpeed(); 
				int xPosition = ship.getX(); 
				int yPosition = ship.getY(); 
				
				for(int i = xPosition; i < xPosition + shipSpeed; i++)
				{
					ActorState.boardTiles[i][yPosition].drawAsBlue();
				}
			}
		}
	}


	/**
	 * This method compares the ACTOR ship with the MODEL ship.
	 * It determines if the two are in the same location, and if not,
	 * it moves them to the proper place. 
	 */
	private void updateMovementAndFire(float delta) 
	{
		// Update all the ships Based on location. 
		for(Ship currentShip : ActorState.shipList)
		{
			
			// Turning the ship. 
			// TODO: Figure this shit out... 
			
			// Moving forward or backward. 
			if(currentShip.getX() < (float) currentShip.ship.getX())
			{
				currentShip.moveBy(delta, 0);
			}
			else if(currentShip.getX() > (float) currentShip.ship.getX())
			{
				currentShip.moveBy(-delta, 0);
			}
			else if(currentShip.getY() < (float) currentShip.ship.getY())
			{
				currentShip.moveBy(0, delta);
			}
			else if(currentShip.getY() > (float) currentShip.ship.getY())
			{
				currentShip.moveBy(0, -delta);
			}

		}
		
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
		Group bg = new Group(); 
		Group fg = new Group(); 
		
		STAGE = new Stage(); 
		STAGE.setCamera(CAMERA);
		STAGE.setViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, true);
		
		
		// Initialize the GameTiles 
		ActorState.initializeBoardTiles(); 
		

		for(Tile[] xArray : ActorState.boardTiles)
		{
			for(Tile tile : xArray)
			{
				fg.addActor(tile);
			}
		}
		
		// Initialize the Asteroids. 
		ActorState.initializeAsteroidTiles(); 
		

		for(Asteroid[] xArray : ActorState.asteroidField)
		{
			for(Asteroid asteroid : xArray)
			{
				if(asteroid != null) bg.addActor(asteroid);
			}
		}
		
		// Initialize the Bases. 
		ActorState.initializeBaseTiles(); 
		
		for(BaseTile[] xArray : ActorState.playerOneBase)
		{
			for(BaseTile base : xArray)
			{
				if(base != null) bg.addActor(base);
			}
		}
		
		for(BaseTile[] xArray : ActorState.playerTwoBase)
		{
			for(BaseTile base : xArray)
			{
				if(base != null) bg.addActor(base);
			}
		}
		
		// Add the ships. 
		ActorState.initializeShipDefault(); 
		
		for(ShipTile[] xArray : ActorState.playerOneFleet)
		{
			for(ShipTile ship : xArray)
			{
				if(ship != null) bg.addActor(ship);
			}
		}
		
		STAGE.addActor(bg); 
		STAGE.addActor(fg); 
	}

	
	/*********************************************************************************
	 * Controller Methods
	 *********************************************************************************/
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyUp(int keycode) 
	{
		// Cycle through current Ships 
		if(Keys.SPACE == keycode)
		{
			if(CURRENT_SELECTION < 7)
			{
				CURRENT_SELECTION ++;
				System.out.println(CURRENT_SELECTION);
			}
			else
			{
				CURRENT_SELECTION = 0; 
			}
		}
		
		// Display no ship borders
		if(Keys.ALT_RIGHT == keycode)
		{
			CURRENT_SELECTION = -1; 
		}
		
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
