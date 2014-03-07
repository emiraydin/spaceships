package tests;

import static org.junit.Assert.*;
import gameLogic.Constants.OrientationType;
import gameLogic.Constants.PlayerNumber;
import gameLogic.Constants.SpaceThingType;

import java.util.ArrayList;

import messageprotocol.AbstractMessage;
import messageprotocol.GameStateBuildMessage;
import messageprotocol.GameStateMessage;
import messageprotocol.ServerMessageHandler;

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
	ArrayList<AbstractMessage> messages = new ArrayList<AbstractMessage>();

	@Before
	public void setUp() throws Exception
	{
		// Build a message to construct a cruiser...
		messages.add(
				new GameStateBuildMessage(0, PlayerNumber.PlayerOne, SpaceThingType.CruiserShip, 1, 1, OrientationType.North)
				);
		messages.add(
				new GameStateBuildMessage(1, PlayerNumber.World, SpaceThingType.Asteroid, 5, 5, OrientationType.South)
				);
	
	}
	
//	/**
//	 * Test that 
//	 */
//	@Test
//	public void testAddThings()
//	{		
//		// No ships yet
//		assertEquals(0, GameState.getNumSpaceThings());
//		
//		// Add the one new ship...
//		ServerMessageHandler.handleMessages(messages);
//		
//		// Should now have 2 things...		
//		assertEquals(2, GameState.getNumSpaceThings());
//		
//		ArrayList<AbstractMessage> moreMessages = new ArrayList<AbstractMessage>();
//		
//		moreMessages.add(
//				new GameStateBuildMessage(2, PlayerNumber.PlayerTwo, SpaceThingType.Mine, 6, 6, OrientationType.South)
//				);
//		moreMessages.add(
//				new GameStateBuildMessage(3, PlayerNumber.PlayerTwo, SpaceThingType.BaseTile, 7, 8, OrientationType.North)
//				);
//		
//		ServerMessageHandler.handleMessages(moreMessages);
//		
//		assertEquals(4, GameState.getNumSpaceThings());
//	}
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
