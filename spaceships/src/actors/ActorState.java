package actors;

import gameLogic.Constants.OrientationType;
import gameLogic.Constants.PlayerNumber;

import java.util.HashMap;
import java.util.LinkedList;

import state.Asteroid;
import state.BaseTile;
import state.SpaceThing;
import state.ships.AbstractShip;
import state.ships.CruiserShip;
import state.ships.DestroyerShip;
import state.ships.MineLayerShip;
import state.ships.RadarBoatShip;
import state.ships.TorpedoShip;

import com.badlogic.gdx.scenes.scene2d.Actor;



/**
 * Class that holds all the necessary Game State to be rendered to Screen. 
 * This is the Class that gets queried multiple times by the GameScreenController in order to know the position and locations of the actors. 
 * @author Vikram
 *
 */
public class ActorState 
{
	/*
	 * Instance variables.
	 * These are the various objects to which we are interested. 
	 */
	public static int			boardHeight = 30, boardWidth = 30;						// The gameBoard Height and Width. 	
	private static int			maxAsteroids = 24; 										// The Maximum number of Asteroids available. 
	public static TileActor[][] 		boardTiles = new TileActor[boardHeight][boardWidth];		 	// The game Tiles. 
	public static BaseTileActor[][] 	playerOneBase = new BaseTileActor[boardHeight][boardWidth]; 	// The current players base Tiles. 
	public static BaseTileActor[][]  playerTwoBase = new BaseTileActor[boardHeight][boardWidth];; // The opponent players base Tiles.
	public static ShipTileActor[][] playerOneFleet = new ShipTileActor[boardHeight][boardWidth];	// The current players ship Tiles. 
	private static ShipTileActor[][] playerTwoFleet = new ShipTileActor[boardHeight][boardWidth]; // The opponent players ship Tiles. 
	private static MineActor[][] 	playerOneMineField = new MineActor[boardHeight][boardWidth];	// The current players Mine Tiles. 
	private static MineActor[][] 	playerTwoMineField = new MineActor[boardHeight][boardWidth];	// The opponent players Mine Tiles. 
	public static AsteroidActor[][]  asteroidField = new AsteroidActor[boardHeight][boardWidth]; 	// The locations of all the asteroids. 
	public static boolean[][] 	visibility	= new boolean[boardHeight][boardWidth];	   	// The board visibility. 
	public static int 			currentSelectionShip = -1; 								   	// The currently selected player ship. 
	public static TileActor 	currentTile; 
	
	public static LinkedList<ShipActor> shipList = new LinkedList<ShipActor>(); 
	
	/*
	 * Variables respecting Singleton
	 */
	private static ActorState instance; 

	/**
	 * Constructor. 
	 * Create a new GameState instance. 
	 * There should only be one GameState at any point during the game. 
	 */
	private ActorState()
	{
	}
	
	/**
	 * Create the boardTiles. 
	 */
	public static void initializeBoardTiles()
	{
		for(int i = 0; i < boardHeight; i++)
		{
			for(int k = 0; k < boardWidth; k++)
			{
				boardTiles[i][k] = new TileActor(i,k); 
			}
		}
	}
	
	/**
	 * Generate Random Asteroids. 
	 * Calling this method will replace the locations of all asteroids on Screen.
	 */
	public static void initializeAsteroidTiles()
	{
		// Reset the Asteroid Map
		for(int i = 0; i < boardHeight; i++)
		{
			for(int k = 0; k < boardWidth; k++)
			{
				asteroidField[i][k] = null; 
			}
		}
		
		
		// The Count of the number of displayed Asteroids. 
		int count = 0; 
		
		while(count < maxAsteroids)
		{
			int randX = 10 + (int) (Math.random() * ((20 - 10) + 1)); 
			int randY = 3 + (int) (Math.random() * ((27 - 3) + 1));
			
			if(asteroidField[randX][randY] == null)
			{
				asteroidField[randX][randY] = new AsteroidActor(randX, randY); 
				count++; 
			}
		}
	}

	/**
	 * Initializes the BaseTiles for both Players
	 */
	public static void initializeBaseTiles()
	{
		// Create the current players tiles. 
		for(int i = 10; i < 20; i++)
		{
			playerOneBase[0][i] = new BaseTileActor(0, i, PlayerNumber.PlayerOne);
		}
		
		// Create the current players tiles. 
		for(int i = 10; i < 20; i++)
		{
			playerOneBase[29][i] = new BaseTileActor(29, i, PlayerNumber.PlayerTwo);
		}
	}
	
	/**
	 * Initializes default location for all ships. 
	 */
	public static void initializeShipDefault()
	{
		// Create the basic ships models 
		CruiserShip cruiserA = new CruiserShip(0, PlayerNumber.PlayerOne); 
		CruiserShip cruiserB = new CruiserShip(1, PlayerNumber.PlayerOne); 
		DestroyerShip destroyerA = new DestroyerShip(2, PlayerNumber.PlayerOne); 
		DestroyerShip destroyerB = new DestroyerShip(3, PlayerNumber.PlayerOne); 
		DestroyerShip destroyerC = new DestroyerShip(4, PlayerNumber.PlayerOne); 
		MineLayerShip layerA = new MineLayerShip(5, PlayerNumber.PlayerOne); 
		MineLayerShip layerB = new MineLayerShip(6, PlayerNumber.PlayerOne); 
		RadarBoatShip radarA = new RadarBoatShip(7, PlayerNumber.PlayerOne); 
		TorpedoShip torpedoA = new TorpedoShip(8, PlayerNumber.PlayerOne); 
		TorpedoShip torpedoB = new TorpedoShip(9, PlayerNumber.PlayerOne); 
		
		// Setup all the orientation. 
		cruiserA.setOrientation(OrientationType.East);
		cruiserB.setOrientation(OrientationType.East);
		destroyerA.setOrientation(OrientationType.East);
		destroyerB.setOrientation(OrientationType.East);
		destroyerC.setOrientation(OrientationType.East);
		layerA.setOrientation(OrientationType.East);
		layerB.setOrientation(OrientationType.East);
		radarA.setOrientation(OrientationType.East);
		torpedoA.setOrientation(OrientationType.East);
		torpedoB.setOrientation(OrientationType.East);
		
		// Set the starting x and y positions. 
		cruiserA.setX(10);
		cruiserA.setY(10);
		cruiserB.setX(5);
		cruiserB.setY(11);
		destroyerA.setX(8);
		destroyerA.setY(12);
		destroyerB.setX(3);
		destroyerB.setY(13);
		destroyerC.setX(19);
		destroyerC.setY(14);
		layerA.setX(1);
		layerA.setY(15);
		layerB.setX(1);
		layerB.setY(16);
		radarA.setX(1);
		radarA.setY(17);
		torpedoA.setX(1);
		torpedoA.setY(18); 
		torpedoB.setX(1);
		torpedoB.setY(19);

		// Create the basic ship actors. 
		ShipActor playerCruiserOne = new ShipActor(1,10,cruiserA);
		ShipActor playerCruiserTwo = new ShipActor(1,11,cruiserB);
		ShipActor playerDestroyerOne = new ShipActor(1,12,destroyerA);
		ShipActor playerDestroyerTwo = new ShipActor(1,13,destroyerB);
		ShipActor playerDestroyerThree = new ShipActor(1,14,destroyerC);
		ShipActor playerLayerOne = new ShipActor(1,15,layerA);
		ShipActor playerLayerTwo = new ShipActor(1,16,layerB);
		ShipActor playerRadarOne = new ShipActor(1,17,radarA);
		ShipActor playerTorpedoOne = new ShipActor(1,18,torpedoA); 
		ShipActor playerTorpedoTwo = new ShipActor(1,19,torpedoB); 

		// Add all the Actors to the FleetList.
		shipList.add(0, playerCruiserOne); 
		shipList.add(1, playerCruiserTwo); 
		shipList.add(2, playerDestroyerOne); 
		shipList.add(3, playerDestroyerTwo); 
		shipList.add(4, playerDestroyerThree); 
		shipList.add(5, playerLayerOne); 
		shipList.add(6, playerLayerTwo); 
		shipList.add(7, playerRadarOne); 
		shipList.add(8, playerTorpedoOne); 
		shipList.add(9, playerTorpedoTwo);
		
		// Add all the ship tiles to the tile list. 
		addFleetTiles(shipList); 

	}
	
	/**
	 * Helper method that adds all the ships to the playerFleet
	 * @param shipList2 
	 */
	private static void addFleetTiles(LinkedList<ShipActor> shipList2)
	{
		for(ShipActor ship : shipList2)
		{
			for(Actor tile : ship.getChildren())
			{
				playerOneFleet[(int)tile.getX()][(int)tile.getY()] = (ShipTileActor) tile; 
			}
		}
	}

	/**
	 * Another helper that just takes the actor. 
	 * @param ship
	 */
	private static void addFleetTile(ShipActor ship)
	{
		for(Actor tile : ship.getChildren())
		{
			playerOneFleet[(int)tile.getX()][(int)tile.getY()] = (ShipTileActor) tile; 
		}
	}
	
	/**
	 * Method that initializes the entire game from the GameState class. 
	 */
	public static void initializeGame()
	{
		// Create the board Tiles. 
		initializeBoardTiles(); 
		
		// Get the SpaceThing Hash. 
		HashMap<Integer, SpaceThing> things = new HashMap<Integer, SpaceThing>(); 
		
		// Initialize the rest of the objects. 
		for(Integer key : things.keySet())
		{
			SpaceThing object = things.get(key); 
			
			if(object instanceof Asteroid)
			{
				AsteroidActor actor = new AsteroidActor(object.getX(), object.getY()); 
				asteroidField[object.getX()][object.getY()] = actor; 
			}
			
			if(object instanceof AbstractShip)
			{
				ShipActor ship = new ShipActor(object.getX(), object.getY(), (AbstractShip) object); 
				if(((AbstractShip) object).getOwner() == PlayerNumber.PlayerOne)
				{
					addFleetTile(ship); 
				}
				else
				{
					//TODO ADD THE OTHER PLAYESRS SHIP. 
				}
			}
			
			if(object instanceof BaseTile)
			{
				BaseTileActor tile = new BaseTileActor(object.getX(), object.getY(), object.getOwner()); 
				if(object.getOwner() == PlayerNumber.PlayerOne)
				{
					playerOneBase[object.getX()][object.getY()] = tile; 
				}
				else
				{
					playerTwoBase[object.getX()][object.getY()] = tile; 
				}
			}
		}
		
		
	}

	public static void setCurrentTile(TileActor thisTile)
	{
		if(currentTile != null)
		{
			currentTile.drawAsBlue(); 
		}
		currentTile = thisTile; 
		thisTile.drawAsGreen(); 
		
	}


}
