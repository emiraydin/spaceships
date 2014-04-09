package screens;

import gameLogic.ActionValidator;
import gameLogic.Constants;

import java.util.LinkedList;

import state.GameState;
import state.Mine;
import state.SpaceThing;
import state.ships.AbstractShip;
import actors.ActorState;
import actors.AsteroidActor;
import actors.BackgroundActor;
import actors.BaseTileActor;
import actors.MineActor;
import actors.RadarDisplayActor;
import actors.ShipActor;
import actors.ShipTileActor;
import actors.TileActor;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import common.GameConstants.OrientationType;
import common.GameConstants.PlayerNumber;

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
	private boolean debugMode = false; 								// Whether we are debugging or not. 
	public final PlayerNumber cPlayer; 
	//private PlayerNumber otherPlayer; 
	/**
	 * Constructor for GameScreenController. 
	 */
	public GameScreenController(PlayerNumber currentPlayer)
	{
		this.cPlayer = currentPlayer; 
		
		// Initialize Camera. 
		initCamera(); 
		
		// Initialize Stage. 
		if(debugMode)
		{
			initStage(); 
		}
		else
		{
			initLegitStage(); 
		}
		
		STAGE.getRoot().setColor(1, 1, 1, 0);
		STAGE.getRoot().addAction(Actions.fadeIn(4));
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
		updateMovementAndFireAndHealth(delta); 
		
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
		// Rest the ShipTile Actors to null 
		ShipTileActor[][] oponentLocation = ActorState.getOtherFleetArray(cPlayer); 
		LinkedList<ShipActor> oponentShips = ActorState.getOtherFleet(cPlayer);
		LinkedList<ShipActor> playerShips = ActorState.getShipList(cPlayer); 
		boolean[][] radar = GameState.getRadarVisibleTiles(); 
		boolean[][] sonar = GameState.getSonarVisibleTiles(); 
		
		for(ShipActor aShip : oponentShips)
		{
			for(ShipTileActor tile : aShip.tiles)
			{
				int xLoc = (int) Math.round(((ShipTileActor) tile).getX());
				int yLoc = (int) Math.round(((ShipTileActor) tile).getY());
				if(radar[xLoc][yLoc] == false)
				{
					tile.setVisible(false); 
				}
				else
				{
					tile.setVisible(true); 
				}
			}
		}
		
		for(RadarDisplayActor r[] : ActorState.radarRange)
		{
			for(RadarDisplayActor q : r)
			{
				int xLoc = (int) Math.round((q).getX());
				int yLoc = (int) Math.round((q).getY()); 
				
				if(radar[xLoc][yLoc] == false)
				{
					q.setVisible(false); 
				}
				else
				{
					q.setVisible(true); 
				}
			}
		}
		
		for(MineActor m : ActorState.mineList)
		{
			//System.out.println(m.mine.getX() + " " + m.mine.getY()); 
			
			if(m.mine.getX() == -1 || m.mine.getY() == -1 || m.mine.getX() == -2 || m.mine.getY() == -2)
			{
				m.setVisible(false); 
			}
			else
			{
				m.setPosition(m.mine.getX(), m.mine.getY()); 
				m.setVisible(true); 
			}
		}
		
		//System.out.println("-------------------------");
		
		
		for(ShipActor actor : playerShips)
		{
			if(!isDeadShip(actor.ship))
			{
				//actor.setVisible(true);
			}
			else
			{ 
				actor.setVisible(false); 
			}
		}
		
		for(ShipActor actor : oponentShips)
		{
			if(!isDeadShip(actor.ship))
			{
				
			}
			else
			{
				actor.setVisible(false); 
			}
		}
	
		
//		for(ShipActor actor : playerShips)
//		{
//			if(isDeadShip(actor.ship))
//			{
//				actor.setVisible(false);
//			}
//			else
//			{
//				actor.setVisible(true); 
//			}
//			
//		}
//		
//		for(ShipActor actor : oponentShips)
//		{
//			if(isDeadShip(actor.ship))
//			{
//				actor.setVisible(false); 
//			}
//			else
//			{
//				actor.setVisible(true); 
//			}
//			
//		}
	}

	private boolean isDeadShip(AbstractShip ship)
	{
		for(int i : ship.getSectionHealth())
		{
			if(i > 0)
			{
				return false; 
			}
		}
		System.out.println(ship + " is dead "); 
		return true; 
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
			ShipActor currentShip = ActorState.getShipList(cPlayer).get(CURRENT_SELECTION); 
			currentShip.setCurrentShip(true);
			
			for(ShipActor ship : ActorState.getShipList(cPlayer))
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
			for(ShipActor ship : ActorState.getShipList(cPlayer))
			{
				ship.drawAsNonCurrent(); 
				ship.setCurrentShip(false);
			}
		}
	}
	

	/**
	 * Draws the entire gameBoard as blue tiles. 
	 */
	public void resetGameBoardBlue() 
	{
		// Reset all the game tiles to blue. 
		for(int i = 0; i < ActorState.boardHeight; i ++)
		{
			for(int k = 0; k < ActorState.boardWidth; k++)
			{
				ActorState.boardTiles[i][k].drawAsBlue(); 
			}
		}		
		
		if(ActorState.currentTile != null)
		{
			ActorState.currentTile.drawAsGreen(); 
		}
	}
	


	/**
	 * This method compares the ACTOR ship with the MODEL ship.
	 * It determines if the two are in the same location, and if not,
	 * it moves them to the proper place. 
	 * Also determines how much health the ship has and render it based on that.
	 */
	private void updateMovementAndFireAndHealth(float delta) 
	{
		
		// Update all the ships Based on location. 
		// Update the ships orientation. 
		// if there was something to update then just return. 
		for(ShipActor currentShip : ActorState.getShipList(PlayerNumber.PlayerOne))
		{
			// Draw the section health as destroyed if it is. 
			for(int i = 0; i < currentShip.ship.getSectionHealth().length; i++)
			{
				if(currentShip.ship.getSectionHealth()[i] <= 0)
				{
					currentShip.drawDestroyedSection(i); 
				}
			}
			
			// If the entire ship is destroyed, draw remove from screen. 
			int count = currentShip.ship.getLength(); 
			for(int i : currentShip.ship.getSectionHealth())
			{
				if(i <= 0) count --; 
			}
			
			if(currentShip.getOrientation() != currentShip.ship.getOrientation())
			{
				currentShip.setOrientation(currentShip.ship.getOrientation()); 
				return; 
			}
			
			// Moving forward or backward. 
			if(currentShip.getX() < (float) currentShip.ship.getX())
			{
				currentShip.moveBy(0.25f, 0);
			}
			else if(currentShip.getX() > (float) currentShip.ship.getX())
			{
				currentShip.moveBy(-0.25f, 0);
			}
			if(currentShip.getY() < (float) currentShip.ship.getY())
			{
				currentShip.moveBy(0, 0.25f);
			}
			else if(currentShip.getY() > (float) currentShip.ship.getY())
			{
				currentShip.moveBy(0, -0.25f);
			}
		}
		
		for(ShipActor currentShip : ActorState.getShipList(PlayerNumber.PlayerTwo))
		{
			// Draw the section health as destroyed if it is. 
			for(int i = 0; i < currentShip.ship.getSectionHealth().length; i++)
			{
				if(currentShip.ship.getSectionHealth()[i] <= 0)
				{
					currentShip.drawDestroyedSection(i); 
				}
			}
			
			// If the entire ship is destroyed, draw remove from screen. 
			int count = currentShip.ship.getLength(); 
			for(int i : currentShip.ship.getSectionHealth())
			{
				if(i <= 0) count --; 
			}
			
			if(currentShip.getOrientation() != currentShip.ship.getOrientation())
			{
				currentShip.setOrientation(currentShip.ship.getOrientation()); 
				return; 
			}
			
			// Moving forward or backward. 
			
			float currentX = currentShip.getX(), stateX = (float) currentShip.ship.getX(); 
			float currentY = currentShip.getY(), stateY = (float) currentShip.ship.getY(); 
			
			
			
			if(currentShip.getX() < (float) currentShip.ship.getX())
			{
				currentShip.moveBy(0.25f, 0);
			}
			else if(currentShip.getX() > (float) currentShip.ship.getX())
			{
				currentShip.moveBy(-0.25f, 0);
			}
			if(currentShip.getY() < (float) currentShip.ship.getY())
			{
				currentShip.moveBy(0, 0.25f);
			}
			else if(currentShip.getY() > (float) currentShip.ship.getY())
			{
				currentShip.moveBy(0, -0.25f);
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
		
		backDrop.addActor(new BackgroundActor());
		
		// Initialize the GameTiles 
		ActorState.initializeBoardTiles(); 
		

		for(TileActor[] xArray : ActorState.boardTiles)
		{
			for(TileActor tile : xArray)
			{
				fg.addActor(tile);
			}
		}
		
		// Create the RadarView
		for(RadarDisplayActor[] rTile : ActorState.radarRange)
		{
			for(RadarDisplayActor tile : rTile)
			{
				bg.addActor(tile); 
			}
		}
		
		// Initialize the Asteroids. 
		ActorState.initializeAsteroidTiles(); 
		

		for(AsteroidActor[] xArray : ActorState.asteroidField)
		{
			for(AsteroidActor asteroid : xArray)
			{
				if(asteroid != null) bg.addActor(asteroid);
			}
		}
		
		// Initialize the Bases. 
		ActorState.initializeBaseTiles(); 
		
		for(BaseTileActor[] xArray : ActorState.playerOneBase)
		{
			for(BaseTileActor base : xArray)
			{
				if(base != null) bg.addActor(base);
			}
		}
		
		for(BaseTileActor[] xArray : ActorState.playerTwoBase)
		{
			for(BaseTileActor base : xArray)
			{
				if(base != null) bg.addActor(base);
			}
		}
		
		// Add the ships. 
		ActorState.initializeShipDefault(); 
		
		for(ShipTileActor[] xArray : ActorState.playerOneFleet)
		{
			for(ShipTileActor ship : xArray)
			{
				if(ship != null) bg.addActor(ship);
			}
		}
		
		STAGE.addActor(backDrop);
		STAGE.addActor(bg); 
		STAGE.addActor(fg); 
	}

	/**
	 * The proper initialization from the GameState singleton. 
	 */
	private void initLegitStage()
	{
		
		Group backdrop = new Group(); 
		Group background = new Group(); 
		Group foreground = new Group(); 
		
		STAGE = new Stage(); 
		STAGE.setCamera(CAMERA); 
		STAGE.setViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, true);
		
		// Query the Model State to create all the actor objects. 
		ActorState.initializeGame(); 
		
		// Add the background. 
		backdrop.addActor(new BackgroundActor());
		
		// Create the Game Tiles. 
		for(TileActor[] xArray : ActorState.boardTiles)
		{
			for(TileActor tile : xArray)
			{
				foreground.addActor(tile);
			}
		}
	
		// Create the RadarView
		for(RadarDisplayActor[] rTile : ActorState.radarRange)
		{
			for(RadarDisplayActor tile : rTile)
			{
				background.addActor(tile); 
			}
		}
		
		// Create the Asteroid Objects. 
		for(AsteroidActor[] xArray : ActorState.asteroidField)
		{
			for(AsteroidActor asteroid : xArray)
			{
				if(asteroid != null) background.addActor(asteroid);
			}
		}
		
		// Add the ships. 
		for(ShipTileActor[] xArray : ActorState.playerOneFleet)
		{
			for(ShipTileActor ship : xArray)
			{
				if(ship != null) background.addActor(ship);
			}
		}
		
		for(ShipTileActor[] xArray : ActorState.playerTwoFleet)
		{
			for(ShipTileActor ship : xArray)
			{
				if(ship != null) background.addActor(ship);
			}
		}
		
		// Initialize the Bases
		for(BaseTileActor[] xArray : ActorState.playerOneBase)
		{
			for(BaseTileActor base : xArray)
			{
				if(base != null) background.addActor(base);
			}
		}
		
		for(BaseTileActor[] xArray : ActorState.playerTwoBase)
		{
			for(BaseTileActor base : xArray)
			{
				if(base != null) background.addActor(base);
			}
		}
		
		// Initialize the Mines
		for(MineActor m : ActorState.mineList)
		{
			background.addActor(m); 
		}
		
		
		STAGE.addActor(backdrop); 
		STAGE.addActor(background); 
		STAGE.addActor(foreground); 
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
				ShipActor actorShip = ActorState.getShipList(cPlayer).get(CURRENT_SELECTION);
				AbstractShip modelShip = actorShip.ship; 
				
				// Draw the movement Range
				drawMovementRange(actorShip, modelShip); 
			}
		}
		
		if (keycode == Keys.J)
		{
			if(CURRENT_SELECTION != -1)
			{
				ShipActor actorShip = ActorState.getShipList(cPlayer).get(CURRENT_SELECTION);
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
	public void drawCannonRange(ShipActor actorShip, AbstractShip modelShip) 
	{
		int ownerX = modelShip.getX();
		int ownerY = modelShip.getY();
		int cannonWidth = modelShip.getCannonWidth();
		int cannonLength = modelShip.getCannonLength();
		int cannonLengthOffset = modelShip.getCannonLengthOffset();
		
		int minX = -1;
		int maxX = -1;
		int minY = -1;
		int maxY = -1;
		// UPDATED TO NEW ORIGIN SYSTEM 
		switch(modelShip.getOrientation()) { 
		case East: 
			minX = ownerX + cannonLengthOffset;
			maxX = minX + cannonLength - 1;
			minY = ownerY - cannonWidth/2;
			maxY = ownerY + cannonWidth/2;
			break;
		case West:
			maxX = ownerX - cannonLengthOffset;
			minX = maxX - cannonLength + 1;
			minY = ownerY - cannonWidth/2;
			maxY = ownerY + cannonWidth/2;
			break;
		case North: 
			minX = ownerX - cannonWidth/2;
			maxX = ownerX + cannonWidth/2;
			minY = ownerY + cannonLengthOffset;
			maxY = minY + cannonLength - 1;
			break;
		case South: 
			minX = ownerX - cannonWidth/2;
			maxX = ownerX + cannonWidth/2;
			maxY = ownerY - cannonLengthOffset;
			minY = maxY - cannonLength + 1;
			break;
		} 
		
		for(int i = minX; i <= maxX; i++)
		{
			for(int k = minY; k <= maxY; k++)
			{
				if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
				{
					ActorState.boardTiles[i][k].drawAsRed();
				}
			}
		}
		
		
//		// Get the actor and model ships, and draw their appropriate rectangles. 
//		int length = modelShip.getLength(); 
//		int speed = modelShip.getSpeed(); 
//		int cannonLength = modelShip.getCannonLength(); 
//		int cannonWidth = modelShip.getCannonWidth(); 
//		int xPos = modelShip.getX(); 
//		int yPos = modelShip.getY(); 
//		OrientationType orientation = modelShip.getOrientation(); 
//		int shipBack = xPos; 
//		
//		// The drawing is different depending on the orientation. 
//		if(orientation == OrientationType.East)
//		{
//			if(modelShip instanceof TorpedoShip)
//			{
//				for(int i = xPos; i <= cannonLength; i++)
//				{
//					for(int k = yPos - 2; k <= yPos + 2; k++)
//					{
//						if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
//						{
//							ActorState.boardTiles[i][k].drawAsRed(); 
//						}
//					}
//				}
//			}
//			else
//			{
//				// Get the front of the ship. 
//				int shipFront = xPos + length - 1; 
//				
//				// Get the boundaries
//				int middleTile = (shipBack + shipFront) / 2; 
//				int xStart = middleTile - cannonLength / 2; 
//				int xEnd = xStart + cannonLength; 
//				int yStart = yPos - (cannonWidth / 2); 
//				int yEnd = yStart + cannonWidth; 
//								
//				for(int i = xStart; i < xEnd; i++)
//				{
//					for(int k = yStart; k < yEnd; k++)
//					{
//						if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
//						{
//							ActorState.boardTiles[i][k].drawAsRed(); 
//						}
//					}
//				}
//			}
//		}
//		
//		if(orientation == OrientationType.West)
//		{
//			if(modelShip instanceof TorpedoShip)
//			{
//				for(int i = xPos; i >= xPos - cannonLength; i--)
//				{
//					for(int k = yPos - 2; k <= yPos + 2; k++)
//					{
//						if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
//						{
//							ActorState.boardTiles[i][k].drawAsRed(); 
//						}
//					}
//				}
//			}
//			else
//			{
//				// Get the front of the ship. 
//				int shipFront = xPos - length + 1; 
//				
//				// Get the boundaries
//				int middleTile = (shipBack + shipFront) / 2; 
//				int xStart = middleTile + cannonLength / 2; 
//				int xEnd = xStart - cannonLength; 
//				int yStart = yPos - (cannonWidth / 2); 
//				int yEnd = yStart + cannonWidth; 
//								
//				for(int i = xStart; i > xEnd; i--)
//				{
//					for(int k = yStart; k < yEnd; k++)
//					{
//						if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
//						{
//							ActorState.boardTiles[i][k].drawAsRed(); 
//						}
//					}
//				}
//			}
//		}
//		
//		if(orientation == OrientationType.North)
//		{
//			if(modelShip instanceof TorpedoShip)
//			{
//				for(int i = yPos; i <= yPos + cannonLength; i++)
//				{
//					for(int k = xPos - 2; k <= xPos + 2; k++)
//					{
//						if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
//						{
//							ActorState.boardTiles[k][i].drawAsRed(); 
//						}
//					}
//				}
//			}
//			else
//			{
//				// Get the front of the ship. 
//				int shipFront = yPos + length - 1; 
//				shipBack = yPos; 
//				
//				// Get the boundaries
//				int middleTile = (shipBack + shipFront) / 2; 
//				int yStart = middleTile - cannonLength / 2; 
//				int yEnd = yStart + cannonLength; 
//				int xStart = xPos - (cannonWidth / 2); 
//				int xEnd = xStart + cannonWidth; 
//								
//				for(int i = xStart; i < xEnd; i++)
//				{
//					for(int k = yStart; k < yEnd; k++)
//					{
//						if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
//						{
//							ActorState.boardTiles[i][k].drawAsRed(); 
//						}
//					}
//				}
//			}
//		}
//		
//		if(orientation == OrientationType.South)
//		{
//			if(modelShip instanceof TorpedoShip)
//			{
//				for(int i = yPos; i >= yPos - cannonLength; i--)
//				{
//					for(int k = xPos - 2; k <= xPos + 2; k++)
//					{
//						if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
//						{
//							ActorState.boardTiles[k][i].drawAsRed(); 
//						}
//					}
//				}
//			}
//			else
//			{
//				// Get the front of the ship. 
//				int shipFront = yPos - length + 1; 
//				
//				// Get the boundaries
//				int middleTile = (shipBack + shipFront) / 2; 
//				int yStart = middleTile + cannonLength / 2; 
//				int yEnd = yStart - cannonLength; 
//				int xStart = xPos - (cannonWidth / 2); 
//				int xEnd = xStart + cannonWidth; 
//								
//				for(int i = xStart; i < xEnd; i++)
//				{
//					for(int k = yStart; k > yEnd; k--)
//					{
//						if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
//						{
//							ActorState.boardTiles[i][k].drawAsRed(); 
//						}
//					}
//				}
//			}
//		}
	}


	/**
	 * Helper method that draws all the movement range. 
	 */
	public void drawMovementRange(ShipActor actor, AbstractShip model)
	{
		// Get the actor and model ships, and draw their appropriate rectangles. 
		ShipActor actorShip = ActorState.getShipList(cPlayer).get(CURRENT_SELECTION); 
		AbstractShip modelShip = actorShip.ship; 
		int length = modelShip.getLength(); 
		int speed = modelShip.getSpeed(); 
		int cannonLength = modelShip.getCannonLength(); 
		int cannonWidth = modelShip.getCannonWidth(); 
		int xPos = modelShip.getX(); 
		int yPos = modelShip.getY(); 
		OrientationType orientation = modelShip.getOrientation(); 
		int shipBack = xPos; 
		
		// The drawing is different depening on the orientaiton. 
		if(orientation == OrientationType.East)
		{
			// Get the front of the ship. 
			int shipFront = xPos + length - 1; 
			
			// Draw the movement range. 
			for(int i = xPos - 1; i <= xPos + speed; i++)
			{
				if( i == xPos)
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
		else if(orientation == OrientationType.West)
		{
			// Get the front of the ship. 
			int shipFront = xPos - length - 1; 
			
			// Draw the movement range. 
			for(int i = xPos + 1; i >= xPos - speed; i--)
			{
				if(i == xPos)
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
		else if(orientation == OrientationType.North)
		{
			// Get the front of the ship. 
			int shipFront = yPos + length - 1; 
			
			// Draw the movement range. 
			for(int k = yPos - 1; k <= yPos + speed; k++)
			{
				shipBack = yPos; 
				if(k == yPos)
				{
					for(int i = xPos - 1; i <= xPos + 1; i++)
					{
						if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
						{
							ActorState.boardTiles[i][k].drawAsGreen(); 
						}
					}
				}
				if(k >= 0 && k < ActorState.boardHeight)
				{
					ActorState.boardTiles[xPos][k].drawAsGreen(); 
				}
			}
		}
		else if(orientation == OrientationType.South)
		{
			// Get the front of the ship. 
			int shipFront = yPos - length + 1; 
			shipBack = yPos; 
			
			// Draw the movement range. 
			for(int k = shipBack + 1; k >= yPos - speed; k--)
			{
				if(k == yPos)
				{
					for(int i = xPos - 1; i <= xPos + 1; i++)
					{
						if((i >= 0 && i < ActorState.boardWidth) && (k >= 0 && k <= ActorState.boardHeight))
						{
							ActorState.boardTiles[i][k].drawAsGreen(); 
						}
					}
				}
				if(k >= 0 && k < ActorState.boardHeight)
				{
					ActorState.boardTiles[xPos][k].drawAsGreen(); 
				}
			}
		}
	}


	@Override
	public boolean keyUp(int keycode) 
	{
		// Cycle through current Ships 
		if(Keys.SPACE == keycode)
		{
			if(CURRENT_SELECTION < ActorState.getShipList(cPlayer).size() - 1)
			{
				CURRENT_SELECTION ++;
				ActorState.currentSelectionShip = CURRENT_SELECTION; 
				ActionValidator.setCurrentShip(ActorState.getShipList(cPlayer).get(CURRENT_SELECTION).ship); 
			}
			else
			{
				CURRENT_SELECTION = 0; 
				ActorState.currentSelectionShip = CURRENT_SELECTION; 
				ActionValidator.setCurrentShip(ActorState.getShipList(cPlayer).get(CURRENT_SELECTION).ship); 
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
			ActorState.currentSelectionShip = CURRENT_SELECTION; 
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
