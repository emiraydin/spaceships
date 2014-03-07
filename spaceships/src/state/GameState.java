package state;

import gameLogic.Constants.*;

import java.util.HashMap;
import java.util.Set;

import state.ships.*;


public class GameState
{
	/**
	 * Hashmap from unique ID to spaceThing.
	 */
	public static HashMap<Integer, SpaceThing> things = new HashMap<Integer, SpaceThing>();
	
	/**
	 * Visibility.
	 */
	boolean[][] radarVisibleTiles;
	boolean[][] sonarVisibleTiles;
	
	/*
	 * Variables respecting Singleton
	 */
	private static GameState instance; 

	/**
	 * Constructor. 
	 * Create a new GameState instance. 
	 * There should only be one GameState at any point during the game. 
	 */
	private GameState()
	{
	}
	
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
		
}
