package screens;

import gameLogic.Constants;
import gameLogic.Constants.OrientationType;
import state.ships.AbstractShip;
import state.ships.TorpedoShip;
import actors.ActorState;
import actors.Asteroid;
import actors.Background;
import actors.BaseTile;
import actors.Ship;
import actors.ShipTile;
import actors.Tile;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
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
	public OrthographicCamera CAMERA; 								// The Game Camera
	private CameraController CAMCONTROLLER; 						// Handles changing Camera views. 
	public Stage STAGE; 											// The Stage. 
	private int CURRENT_SELECTION = -1; 							// The currently selected ship
 
	
	
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
				if(ship == currentShip)
				{
					ship.drawAsCurrent();
					continue; 
				}
				else
				{
					ship.drawAsNonCurrent();
					ship.setCurrentShip(false);
				}
			}
		}
		else if (CURRENT_SELECTION == -1)
		{
			for(Ship ship : ActorState.shipList)
			{
				ship.drawAsNonCurrent(); 
				ship.setCurrentShip(false);
			}
		}
	}
	

	/**
	 * Draws the entire gameBoard as blue tiles. 
	 */
	private void resetGameBoardBlue() 
	{
		// Reset all the game tiles to blue. 
		for(int i = 0; i < ActorState.boardHeight; i ++)
		{
			for(int k = 0; k < ActorState.boardWidth; k++)
			{
				ActorState.boardTiles[i][k].drawAsBlue(); 
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
		// Update the ships orientation. 
		// if there was something to update then just return. 
		for(Ship currentShip : ActorState.shipList)
		{
			if(currentShip.getOrientation() != currentShip.ship.getOrientation())
			{
				currentShip.setOrientation(currentShip.ship.getOrientation()); 
				return; 
			}
			
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
		Group backDrop = new Group(); 
		Group bg = new Group(); 
		Group fg = new Group(); 
		
		STAGE = new Stage(); 
		STAGE.setCamera(CAMERA);
		STAGE.setViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, true);
		
		backDrop.addActor(new Background());
		
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
		
		STAGE.addActor(backDrop);
		STAGE.addActor(bg); 
		STAGE.addActor(fg); 
	}

	
	/*********************************************************************************
	 * Controller Methods
	 *********************************************************************************/
	
	@Override
	public boolean keyDown(int keycode) 
	{
		if(keycode == Keys.H)
		{
			if(CURRENT_SELECTION != -1)
			{
				Ship actorShip = ActorState.shipList.get(CURRENT_SELECTION);
				AbstractShip modelShip = actorShip.ship; 
				
				// Draw the movement Range
				drawMovementRange(actorShip, modelShip); 
			}
		}
		
		if (keycode == Keys.J)
		{
			if(CURRENT_SELECTION != -1)
			{
				Ship actorShip = ActorState.shipList.get(CURRENT_SELECTION);
				AbstractShip modelShip = actorShip.ship; 
				
				// Draw the fire range.
				drawCannonRange(actorShip, modelShip); 
			}
		}
		return false;
	}
	
	/**
	 * Helper method that draws the movement range. 
	 * @param actorShip
	 * @param modelShip
	 */
	private void drawCannonRange(Ship actorShip, AbstractShip modelShip) 
	{
		// Get the actor and model ships, and draw their appropriate rectangles. 
		int length = modelShip.getLength(); 
		int speed = modelShip.getSpeed(); 
		int cannonLength = modelShip.getCannonLength(); 
		int cannonWidth = modelShip.getCannonWidth(); 
		int xPos = modelShip.getX(); 
		int yPos = modelShip.getY(); 
		OrientationType orientation = modelShip.getOrientation(); 
		int shipBack = xPos; 
		
		// The drawing is different depending on the orientation. 
		switch(orientation)
		{
			case East:
			{
				if(modelShip instanceof TorpedoShip)
				{
					for(int i = xPos; i <= cannonLength; i++)
					{
						for(int k = yPos - 2; k <= yPos + 2; k++)
						{
							if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
							{
								ActorState.boardTiles[i][k].drawAsRed(); 
							}
							
						}
					}
				}
				else
				{
					// Get the front of the ship. 
					int shipFront = xPos + length - 1; 
					
					// Get the middle Tile. 
					int middleTile = (shipBack + shipFront) / 2; 
					
					for(int i = middleTile - (cannonLength / 2); i < middleTile + (cannonLength/2 + 1); i++)
					{
						for(int k = yPos - (cannonWidth / 2); k < yPos + (cannonWidth / 2 + 1); k++)
						{
							if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
							{
								ActorState.boardTiles[i][k].drawAsRed(); 
							}
						}
						
					}
				}
			}
			
			case West:
			{


			}
			
			case North:
			{
				
			}
			
			case South:
			{
				
			}
		
		}
	}


	/**
	 * Helper method that draws all the movement range. 
	 */
	private void drawMovementRange(Ship actor, AbstractShip model)
	{
		// Get the actor and model ships, and draw their appropriate rectangles. 
		Ship actorShip = ActorState.shipList.get(CURRENT_SELECTION); 
		AbstractShip modelShip = actorShip.ship; 
		int length = modelShip.getLength(); 
		int speed = modelShip.getSpeed(); 
		int cannonLength = modelShip.getCannonLength(); 
		int cannonWidth = modelShip.getCannonWidth(); 
		int xPos = modelShip.getX(); 
		int yPos = modelShip.getY(); 
		OrientationType orientation = modelShip.getOrientation(); 
		int shipBack = xPos; 
		
		// The drawing is different depending on the orientation. 
		switch(orientation)
		{
			case East:
			{
				// Get the front of the ship. 
				int shipFront = xPos + length - 1; 
				
				// Draw the movement range. 
				for(int i = xPos - 1; i < shipFront + speed; i++)
				{
					if(i >= shipBack && i <= shipFront)
					{
						for(int k = yPos - 1; k <= yPos + 1; k++)
						{
							if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
							{
								ActorState.boardTiles[i][k].drawAsGreen(); 
							}
						}
					}
					if((i >= 0 && i < ActorState.boardWidth))
					{
						ActorState.boardTiles[i][yPos].drawAsGreen(); 
					}
				}
			}
			
			case West:
			{


			}
			
			case North:
			{
				
			}
			
			case South:
			{
				
			}
		
		}
	}


	@Override
	public boolean keyUp(int keycode) 
	{
		// Cycle through current Ships 
		if(Keys.SPACE == keycode)
		{
			if(CURRENT_SELECTION < ActorState.shipList.size() - 1)
			{
				CURRENT_SELECTION ++;
				ActorState.currentSelection = CURRENT_SELECTION; 
			}
			else
			{
				CURRENT_SELECTION = 0; 
				ActorState.currentSelection = CURRENT_SELECTION; 
			}
		}
		
		if(Keys.H == keycode || Keys.J == keycode || Keys.K == keycode)
		{
			resetGameBoardBlue(); 
		}
		
		// Display no ship borders
		if(Keys.ALT_RIGHT == keycode)
		{
			CURRENT_SELECTION = -1; 
			ActorState.currentSelection = CURRENT_SELECTION; 
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
	public boolean scrolled(int amount) 
	{
		// TODO Auto-generated method stub
		return false;
	}
}
