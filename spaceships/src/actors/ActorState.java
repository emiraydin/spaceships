package actors;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



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
	private static ShipTile[][] playerOneFleet = new ShipTile[boardHeight][boardWidth];// The current players ship Tiles. 
	private static ShipTile[][] playerTwoFleet = new ShipTile[boardHeight][boardWidth]; // The opponent players ship Tiles. 
	private static Mine[][] 	playerOneMineField = new Mine[boardHeight][boardWidth];// The current players Mine Tiles. 
	private static Mine[][] 	playerTwoMineField = new Mine[boardHeight][boardWidth];// The opponent players Mine Tiles. 
	public static Asteroid[][]  asteroidField = new Asteroid[boardHeight][boardWidth]; // The locations of all the asteroids. 
	public static boolean[][] 	visibility	= new boolean[boardHeight][boardWidth];	   // The board visibility. 
	
	
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
	
	


}
