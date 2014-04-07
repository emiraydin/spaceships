package state;

import java.util.HashMap;
import java.util.Set;

import state.ships.*;

import common.GameConstants.*;

public class GameState
{
	/**
	 * Hashmap from unique ID to spaceThing.
	 */
	private static HashMap<Integer, SpaceThing> things = new HashMap<Integer, SpaceThing>();
	
	/**
	 * Visibility.
	 */
	private static boolean[][] radarVisibleTiles;
	private static boolean[][] sonarVisibleTiles;
	
	/**
	 * The parents of all the mines!
	 */
	private static HashMap<Integer,Integer> mineParents = new HashMap<Integer,Integer>();
	
	
	/**
	 * Get the entire State
	 * @return
	 */
	public static HashMap<Integer, SpaceThing> getAllSpaceThings() {
		return things;
	}
	
	/**
	 * The current player ID
	 */
	private static int currentPlayerID; 
	
	private static String responseString = ""; 
	
	/**
	 * Get a space thing by its id.
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public static SpaceThing getSpaceThing(int id) throws Exception {
		if (things.containsKey(id)) {
			return things.get(id);
		} else throw new Exception("Requested SpaceThing does not exist!");
	}
	
	/**
	 * Check if a SpaceThing with the specified id exists.
	 * 
	 * @param id
	 * @return
	 */
	public static boolean spaceThingExists(int id) {
		return things.containsKey(id);
	}
	
	/**
	 * Create a SpaceThing.
	 * 
	 * @throws IllegalArgumentException if bad ship type
	 */
	public static void createSpaceThing(
			int spaceThingId,
			PlayerNumber owner,
			SpaceThingType type,
			int posX,
			int posY,
			OrientationType orientation
			) throws IllegalArgumentException {
		
		// Switch in the event that it's a basetile, asteroid, or mine
		switch (type) {
			case BaseTile:
				BaseTile base = new BaseTile(spaceThingId, owner);
				base.setX(posX);
				base.setY(posY);
				things.put(spaceThingId, base);
				return;
			case Asteroid:
				Asteroid asteroid = new Asteroid(spaceThingId);
				asteroid.setX(posX);
				asteroid.setY(posY);
				things.put(spaceThingId, asteroid);
				return;
			case Mine:
				Mine mine = new Mine(spaceThingId, owner);
				mine.setX(posX);
				mine.setY(posY);
				things.put(spaceThingId, mine);
				return;
		}
		
		// If the function makes it this far, then it's a ship.
		
		AbstractShip newShip;
		
		switch (type) {
			case CruiserShip: 
				newShip = new CruiserShip(spaceThingId, owner);
				break;
			case DestroyerShip:
				newShip = new DestroyerShip(spaceThingId, owner);
				break;
			case MineLayerShip:
				newShip = new MineLayerShip(spaceThingId, owner);
				break;
			case RadarBoatShip:
				newShip = new RadarBoatShip(spaceThingId, owner);
				break;
			case KamikazeBoatShip:
				newShip = new KamikazeBoatShip(spaceThingId, owner);
				break;
			case TorpedoShip:
				newShip = new TorpedoShip(spaceThingId, owner);
				break;
			// If you get this far, something has gone wrong
			default: throw new IllegalArgumentException("Malformed SpaceThingType");
		}	
		
		newShip.setX(posX);
		newShip.setY(posY);
		newShip.setOrientation(orientation);
		
		things.put(spaceThingId, newShip);		
	}
	
	/**
	 * The number of SpaceThings currently instantiated.
	 * @return the number of SpaceThings
	 */
	public static int getNumSpaceThings() {
		return things.size();
	}
	
	public static Set<Integer> getIdSet() {
		return things.keySet();
	}

	/**
	 * @return the radarVisibleTiles
	 */
	public static boolean[][] getRadarVisibleTiles() {
		return radarVisibleTiles;
	}

	/**
	 * @param radarVisibleTiles the radarVisibleTiles to set
	 */
	public static void setRadarVisibleTiles(boolean[][] radarVisibleTiles) {
		GameState.radarVisibleTiles = radarVisibleTiles;
	}

	/**
	 * @return the sonarVisibleTiles
	 */
	public static boolean[][] getSonarVisibleTiles() {
		return sonarVisibleTiles;
	}

	/**
	 * @param sonarVisibleTiles the sonarVisibleTiles to set
	 */
	public static void setSonarVisibleTiles(boolean[][] sonarVisibleTiles) {
		GameState.sonarVisibleTiles = sonarVisibleTiles;
	}

	public static void setPlayerId(int playerID)
	{
		currentPlayerID = playerID; 
	}
	
	public static int getPlayerId()
	{
		return currentPlayerID; 
	}

	public static void setResponseString(String r)
	{
		responseString = r;
	}
	
	public static String getResponseString()
	{
		return responseString; 
	}


	/**
	 * Get the parent ship of a specific mine.
	 * 
	 * @param mineId id of mine
	 * @return
	 */
	public static int getMineParent(int mineId) {
		return mineParents.get(mineId);
	}

	/**
	 * Replace the mineParents hashmap with a new one.
	 * 
	 * @param newMap
	 */
	public static void replaceMineParentsMap(HashMap<Integer,Integer> newMap) {
		mineParents = newMap;
	}

	
	
		
}
