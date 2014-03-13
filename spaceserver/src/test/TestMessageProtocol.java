package test;

import static org.junit.Assert.*;

import java.util.ArrayList;


import logic.FleetCommander;
import logic.GameHandler;
import logic.spacethings.AbstractShip;
import messageprotocol.*;

import org.junit.Before;
import org.junit.Test;

import common.GameConstants.PlayerNumber;
import common.GameConstants.*;


public class TestMessageProtocol {
	
	ActionMessage         action  = null;
	GameStateMessage      state   = null;
	MatchRequestMessage   request = null;
	MessageResponder      respond = null;
	NewTurnMessage        turn    = null;
	PlayerCreatedMessage  created = null;
	PlayerOfflineMessage  offline = null;
	PlayerOnlineMessage   online  = null;
	
	GameHandler handler = null;
	
	int[] health = {1,2,3};
	
	boolean [][] radar = {{true,false},{false,true}};
	boolean [][] sonar = {{false,false},{false,false}};
	
	FleetCommander pZero = null;
	FleetCommander pOne = null;
	
	@Before
	public void setUp() {
		
		handler = new GameHandler();
		
		action = new ActionMessage(ActionType.Move, 12, 13, 14);
		
		
		state = new GameStateMessage(
				9,
				PlayerNumber.PlayerOne,
				SpaceThingType.CruiserShip,
				17,
				19,
				OrientationType.East,
				health
				);
		
		request = new MatchRequestMessage("Jorg", "Martin");

		respond = new MessageResponder(handler);
		
		turn = new NewTurnMessage(action, true, "test response", null, radar, sonar);
		turn.addStateMessage(state);
		
		created = new PlayerCreatedMessage("Jorg", "elevator");
		
		offline = new PlayerOfflineMessage("Martin");
		
		online = new PlayerOnlineMessage("Jorg");
		
		pZero = handler.getFleetCommander(0);
		pOne = handler.getFleetCommander(1);
	}
	
	@Test
	public void testMessages() {
		// ActionMessage
		assertEquals(ActionType.Move, action.getAction());
		assertEquals(12, action.getSpaceThingId());
		assertEquals(13, action.getDestX());
		assertEquals(14, action.getDestY());
		
		assertTrue(action.equals(action));
		assertFalse(action.equals(new ActionMessage(null, 0, 0, 0)));
		
		assertFalse(action.toString().equals((new ActionMessage(null, 0, 0, 0) ).toString()));
		assertFalse(action.hashCode() == (new ActionMessage(null, 0, 0, 0) ).hashCode());
		
		// State Message
		assertEquals(9, state.getSpaceThingId());
		assertEquals(PlayerNumber.PlayerOne, state.getOwner());
		assertEquals(SpaceThingType.CruiserShip, state.getType());
		assertEquals(17, state.getPosX());
		assertEquals(19, state.getPosY());
		assertEquals(OrientationType.East, state.getOrientation());
		
		assertTrue(state.equals(state));
		assertFalse(state.equals(new GameStateMessage(0, null, null, 0, 0, null, health)));
		
		assertFalse(state.toString().equals((new GameStateMessage(0, null, null, 0, 0, null, health)).toString()));
		assertFalse(state.hashCode() == (new GameStateMessage(0, null, null, 0, 0, null, health) ).hashCode());
		
		
		//Request
		try {
			created.execute();
			offline.execute();
			online.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("message executions failed!");
		}
		
	}
	
	@Test
	public void testMessageExec() {
		ArrayList<AbstractShip> ships = pZero.getShips();
		
		int shipId = ships.get(0).getID();		
		
		ActionMessage newMes = new ActionMessage(ActionType.Move, shipId, ships.get(0).getX() + 1, ships.get(0).getY());
		
		NewTurnMessage[] response = handler.doAction(newMes, 0);
		
		for (NewTurnMessage current : response) {
			System.out.println(current);
		}		
	}

	@Test
	public void testFleetCommander() {
		
		ArrayList<AbstractShip> ships = pZero.getShips();
		
		int shipId = ships.get(0).getID();
		
		pZero.moveShip(shipId, ships.get(0).getX() + 1, ships.get(0).getY());
		
		assertEquals(pZero.getHandler(), handler);
		
		assertEquals(pZero.getPlayer(), PlayerNumber.PlayerOne);
		
		pZero.getRadarVisibility();
		pZero.getSonarVisibility();
		pZero.getVisibility(0, 0);
		
		
	}

}
