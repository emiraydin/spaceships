package actors;

import gameLogic.Constants.OrientationType;
import gameLogic.Constants.PlayerNumber;

import java.util.LinkedList;

import state.ships.CruiserShip;
import state.ships.DestroyerShip;
import state.ships.MineLayerShip;
import state.ships.RadarBoatShip;

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
	private static int			boardHeight = 30, boardWidth = 30;					// The gameBoard Height and Width. 	
	private static int			maxAsteroids = 24; 									// The Maximum number of Asteroids available. 
	public static Tile[][] 		boardTiles = new Tile[boardHeight][boardWidth];		 // The game Tiles. 
	public static BaseTile[][] 	playerOneBase = new BaseTile[boardHeight][boardWidth]; 	// The current players base Tiles. 
	public static BaseTile[][]  playerTwoBase = new BaseTile[boardHeight][boardWidth];; // The opponent players base Tiles.
	public static ShipTile[][] playerOneFleet = new ShipTile[boardHeight][boardWidth];// The current players ship Tiles. 
	private static ShipTile[][] playerTwoFleet = new ShipTile[boardHeight][boardWidth]; // The opponent players ship Tiles. 
	private static Mine[][] 	playerOneMineField = new Mine[boardHeight][boardWidth];// The current players Mine Tiles. 
	private static Mine[][] 	playerTwoMineField = new Mine[boardHeight][boardWidth];// The opponent players Mine Tiles. 
	public static Asteroid[][]  asteroidField = new Asteroid[boardHeight][boardWidth]; // The locations of all the asteroids. 
	public static boolean[][] 	visibility	= new boolean[boardHeight][boardWidth];	   // The board visibility. 
	
	public static LinkedList<Ship> shipList = new LinkedList<Ship>(); 
	
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
				boardTiles[i][k] = new Tile(i,k); 
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
				asteroidField[randX][randY] = new Asteroid(randX, randY); 
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
			playerOneBase[0][i] = new BaseTile(0, i, 1);
		}
		
		// Create the current players tiles. 
		for(int i = 10; i < 20; i++)
		{
			playerOneBase[29][i] = new BaseTile(29, i, 0);
		}
	}
	
	/**
	 * Initializes default location for all ships. 
	 */
	public static void initializeShipDefault()
	{
		// Create the basic ships models 
		CruiserShip cruiserA = new CruiserShip(0, PlayerNumber.PlayerOne); 
		CruiserShip cruiserB = new CruiserShip(1, PlayerNumber.PlayerTwo); 
		DestroyerShip destroyerA = new DestroyerShip(2, PlayerNumber.PlayerOne); 
		DestroyerShip destroyerB = new DestroyerShip(3, PlayerNumber.PlayerOne); 
		DestroyerShip destroyerC = new DestroyerShip(4, PlayerNumber.PlayerOne); 
		MineLayerShip layerA = new MineLayerShip(5, PlayerNumber.PlayerOne); 
		MineLayerShip layerB = new MineLayerShip(6, PlayerNumber.PlayerOne); 
		RadarBoatShip radarA = new RadarBoatShip(7, PlayerNumber.PlayerOne); 
		
		// Setup all the orientation. 
		cruiserA.setOrientation(OrientationType.East);
		cruiserB.setOrientation(OrientationType.East);
		destroyerA.setOrientation(OrientationType.East);
		destroyerB.setOrientation(OrientationType.East);
		destroyerC.setOrientation(OrientationType.East);
		layerA.setOrientation(OrientationType.East);
		layerB.setOrientation(OrientationType.East);
		radarA.setOrientation(OrientationType.East);
		
		// Set the starting x and y positions. 
		cruiserA.setX(10);
		cruiserA.setY(10);
		cruiserB.setX(1);
		cruiserB.setY(11);
		destroyerA.setX(1);
		destroyerA.setY(12);
		destroyerB.setX(1);
		destroyerB.setY(13);
		destroyerC.setX(1);
		destroyerC.setY(14);
		layerA.setX(1);
		layerA.setY(15);
		layerB.setX(1);
		layerB.setY(16);
		radarA.setX(1);
		radarA.setY(17);

		// Create the basic ship actors. 
		Ship playerCruiserOne = new Ship(1,10,cruiserA);
		Ship playerCruiserTwo = new Ship(1,11,cruiserB);
		Ship playerDestroyerOne = new Ship(1,12,destroyerA);
		Ship playerDestroyerTwo = new Ship(1,13,destroyerB);
		Ship playerDestroyerThree = new Ship(1,14,destroyerC);
		Ship playerLayerOne = new Ship(1,15,layerA);
		Ship playerLayerTwo = new Ship(1,16,layerB);
		Ship playerRadarOne = new Ship(1,17,radarA);

		// Add all the Actors to the FleetList.
		shipList.add(0, playerCruiserOne); 
		shipList.add(1, playerCruiserTwo); 
		shipList.add(2, playerDestroyerOne); 
		shipList.add(3, playerDestroyerTwo); 
		shipList.add(4, playerDestroyerThree); 
		shipList.add(5, playerLayerOne); 
		shipList.add(6, playerLayerTwo); 
		shipList.add(7, playerRadarOne); 
		
		// Add all the ship tiles to the tile list. 
		addFleetTiles(shipList); 

	}
	
	/**
	 * Helper method that adds all the ships to the playerFleet
	 * @param shipList2 
	 */
	private static void addFleetTiles(LinkedList<Ship> shipList2)
	{
		for(Ship ship : shipList2)
		{
			for(Actor tile : ship.getChildren())
			{
				playerOneFleet[(int)tile.getX()][(int)tile.getY()] = (ShipTile) tile; 
			}
		}
	}


}
