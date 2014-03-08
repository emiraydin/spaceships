package tests;

import static org.junit.Assert.*;
import gameLogic.Constants.*;

import java.util.ArrayList;

import messageprotocol.*;

import org.junit.Before;
import org.junit.Test;

import state.Asteroid;
import state.GameState;
import state.ships.AbstractShip;


/**
 * Tests for setting up the GameState.
 * 
 */
public class TestGameState
{
	/**
	 * Some messages for setting up the game
	 */
	ActionMessage action = null;
	boolean[][] radarVisibleTiles = null;
	boolean[][]	sonarVisibleTiles = null;
	NewTurnMessage message = null;
	NewTurnMessage message2 = null;

	@Before
	public void setUp() throws Exception
	{
		action = new ActionMessage(ActionType.Move, 0, 0, 0);
		radarVisibleTiles = new boolean[5][5];
		sonarVisibleTiles = new boolean[5][5];
		
		message = new NewTurnMessage(action, null, radarVisibleTiles, sonarVisibleTiles);
		
		message.addStateMessage(new GameStateMessage(
				0,
				PlayerNumber.PlayerOne,
				SpaceThingType.CruiserShip,
				12,
				12,
				OrientationType.South,
				null)
		);
		
		message2 = new NewTurnMessage(null, null, null, null);
		message2.addStateMessage(new GameStateMessage(
				0,
				PlayerNumber.PlayerOne,
				SpaceThingType.CruiserShip,
				17,
				18,
				OrientationType.West,
				null)
		);	
		message2.addStateMessage(new GameStateMessage(
				5,
				PlayerNumber.World,
				SpaceThingType.Asteroid,
				17,
				18,
				null,
				null)
		);
		
	
	}
	
	/**
	 * Test that 
	 */
	@Test
	public void testAddThings()
	{		
		// No ships yet
		assertEquals(0, GameState.getNumSpaceThings());
		
		// Add the one new ship...
		ServerMessageHandler.executeNewTurnMessage(message);
		
		// Should now have 1 things...		
		assertEquals(1, GameState.getNumSpaceThings());
		
		try {
			System.out.println(GameState.getSpaceThing(0).toString());
		} catch (Exception e) {
			fail();
		}
		
		ServerMessageHandler.executeNewTurnMessage(message2);
		
		// Should now have 2 things
		assertEquals(2, GameState.getNumSpaceThings());
		
		try {
			System.out.println(GameState.getSpaceThing(0).toString());
			System.out.println(GameState.getSpaceThing(5).toString());
		} catch (Exception e) {
			fail();
		}
		
		
	}
//	
//	
//	/**
//	 * Test that the two SpaceThings added to the GameState have correct properties.
//	 */
//	@Test
//	public void testThingsNotMessedUp() {
//		ServerMessageHandler.handleMessages(messages);
//		try
//		{
//			AbstractShip ship = (AbstractShip) GameState.getSpaceThing(0);
//			Asteroid aster = (Asteroid) GameState.getSpaceThing(1);
//			
//			System.out.println(ship);		
//			System.out.println(aster);
//			
//		}
//		catch (Exception e)
//		{
//			fail("Exception thrown getting spacethings...");
//		}
//		
//	}
//	
//	/**
//	 * Test that updating a ship state works...
//	 */
//	@Test
//	public void testUpdateShip() {
//		ServerMessageHandler.handleMessages(messages);
//		
//		ArrayList<AbstractMessage> moreMessages = new ArrayList<AbstractMessage>();
//		int[] firstArray = {9,9,9,9,9};
//		moreMessages.add(new GameStateMessage(0, 42, 43, OrientationType.South, firstArray));
//		ServerMessageHandler.handleMessages(moreMessages);
//		
//		try
//		{
//			AbstractShip theShip = (AbstractShip) GameState.getSpaceThing(0);
//			int[] testArray = {9,9,9,9,9};
//			assertEquals(42, theShip.getX());
//			assertEquals(43, theShip.getY());
//			assertEquals(OrientationType.South, theShip.getOrientation());
//			int[] testArray2 = theShip.getSectionHealth();
//			for (int i = 0; i < testArray2.length; i++) {
//				assertEquals(testArray[i], testArray2[i]);
//			}
//			System.out.println(theShip);
//			
//		}
//		catch (Exception e)
//		{
//			fail("");
//		}
//		
//		
//		
//	}

}
