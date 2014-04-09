package test;

import java.awt.Point;
import java.util.ArrayList;

import logic.FleetCommander;
import logic.GameHandler;
import logic.StarBoard;
import logic.spacethings.AbstractShip;
import logic.spacethings.SpaceThing;
import messageprotocol.ActionMessage;
import messageprotocol.GameStateMessage;
import messageprotocol.MatchRequestMessage;
import messageprotocol.MessageResponder;
import messageprotocol.NewTurnMessage;
import messageprotocol.PlayerCreatedMessage;
import messageprotocol.PlayerOfflineMessage;
import messageprotocol.PlayerOnlineMessage;

import org.junit.Before;
import org.junit.Test;

import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.PlayerNumber;
import common.GameConstants.SpaceThingType;


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
		
		turn = new NewTurnMessage(action, true, "test response", null, radar, sonar, 0, 0, null);
		turn.addStateMessage(state);
		
		created = new PlayerCreatedMessage("Jorg", "elevator");
		
		offline = new PlayerOfflineMessage("Martin");
		
		online = new PlayerOnlineMessage("Jorg");
		
		pZero = handler.getFleetCommander(0);
		pOne = handler.getFleetCommander(1);
	}
	
//	@Test
//	public void testMessages() {
//		// ActionMessage
//		assertEquals(ActionType.Move, action.getAction());
//		assertEquals(12, action.getSpaceThingId());
//		assertEquals(13, action.getDestX());
//		assertEquals(14, action.getDestY());
//		
//		assertTrue(action.equals(action));
//		assertFalse(action.equals(new ActionMessage(null, 0, 0, 0)));
//		
//		assertFalse(action.toString().equals((new ActionMessage(null, 0, 0, 0) ).toString()));
//		assertFalse(action.hashCode() == (new ActionMessage(null, 0, 0, 0) ).hashCode());
//		
//		// State Message
//		assertEquals(9, state.getSpaceThingId());
//		assertEquals(PlayerNumber.PlayerOne, state.getOwner());
//		assertEquals(SpaceThingType.CruiserShip, state.getType());
//		assertEquals(17, state.getPosX());
//		assertEquals(19, state.getPosY());
//		assertEquals(OrientationType.East, state.getOrientation());
//		
//		assertTrue(state.equals(state));
//		assertFalse(state.equals(new GameStateMessage(0, null, null, 0, 0, null, health)));
//		
//		assertFalse(state.toString().equals((new GameStateMessage(0, null, null, 0, 0, null, health)).toString()));
//		assertFalse(state.hashCode() == (new GameStateMessage(0, null, null, 0, 0, null, health) ).hashCode());
//		
//		
//		//Request
//		try {
//			created.execute();
//			offline.execute();
//			online.execute();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			fail("message executions failed!");
//		}
//		
//	}
//	
//	@Test
//	public void testMessageExec() {
//		ArrayList<AbstractShip> ships = pZero.getShips();
//		
//		int shipId = 0;
//		
//		// We want to pick out the radar ship
//		for (AbstractShip current : ships) {
//			if (current.getShipType().equals(SpaceThingType.RadarBoatShip)) {
//				shipId = current.getID();
//			}
//		}	
//		
//		ActionMessage newMes = new ActionMessage(ActionType.FireCannon, shipId, pZero.getShip(shipId).getX() + 2, pZero.getShip(shipId).getY());
//		
//		NewTurnMessage[] response = handler.doAction(newMes, 0);
//		
//		for (NewTurnMessage current : response) {
//			System.out.println(current);
//		}
//		
//		
//		// TORPEDO
//		
//		// We want to pick out the torpedo ship
//		for (AbstractShip current : ships) {
//			if (current.getShipType().equals(SpaceThingType.TorpedoShip)) {
//				shipId = current.getID();
//			}
//		}	
//		
//		newMes = new ActionMessage(ActionType.FireTorpedo, shipId, pZero.getShip(shipId).getX() + 1, pZero.getShip(shipId).getY());
//		
//		response = handler.doAction(newMes, 0);
//		
//		for (NewTurnMessage current : response) {
//			System.out.println(current);
//		}
//		
//		// try to turn, should fail
//		newMes = new ActionMessage(ActionType.TurnRight, shipId, -1, -1);
//		response = handler.doAction(newMes, 0);
//		
//		for (NewTurnMessage current : response) {
//			System.out.println(current);
//		}
//		
//		newMes = new ActionMessage(ActionType.Move, shipId, pZero.getShip(shipId).getX() + 9, pZero.getShip(shipId).getY());
//		
//		response = handler.doAction(newMes, 0);
//		
//		for (NewTurnMessage current : response) {
//			System.out.println(current);
//		}	
//		
//		
//		//pZero.turnShip(shipId, ActionType.Turn180Left);
//	} 
//
//	@Test
//	public void testFleetCommander() {
//		
//		ArrayList<AbstractShip> ships = pZero.getShips();
//		
//		int shipId = ships.get(0).getID();
//		
//		pZero.moveShip(shipId, ships.get(0).getX() + 1, ships.get(0).getY());
//		
//		assertEquals(pZero.getHandler(), handler);
//		
//		assertEquals(pZero.getPlayer(), PlayerNumber.PlayerOne);
//		
//		pZero.getRadarVisibility();
//		pZero.getSonarVisibility();
//		pZero.getVisibility(0, 0);
//		
//		
//	}
	
	@Test
	public void testStuff() { 
		ArrayList<AbstractShip> ships = pZero.getShips();
		AbstractShip destroyer = ships.get(2);
		int shipId = destroyer.getID();
		
//		ActionMessage newMes = new ActionMessage(ActionType.Move, shipId, pZero.getShip(shipId).getX() + 7, pZero.getShip(shipId).getY());		
//		NewTurnMessage response = handler.doAction(newMes, 0)[0];		
//		System.out.println(response.getResponseString());
		
//		BaseTile base = new BaseTile(14, 12, pZero, pZero.getHandler().getBoard());
//		pZero.getHandler().getBoard().setSpaceThing(base, 14, 12);
//		System.out.println(base.getHealth());
//		
//		newMes = new ActionMessage(ActionType.FireTorpedo, shipId, pZero.getShip(shipId).getX() + 2, pZero.getShip(shipId).getY());		
//		response = handler.doAction(newMes, 0)[0];		
//		System.out.println(response.getResponseString());
//
//		System.out.println(base.getHealth());
//		
//		
		
		AbstractShip cruiser = ships.get(0);
//		for(AbstractShip ship : ships) { 
//			if(ship instanceof RadarBoatShip) { 
//				radarShip = ship;
//				break;
//			}
//		}
		shipId = cruiser.getID();
		
		System.out.println("here is where the ship originally is");
		Point[] coords = cruiser.getShipCoords();
		for(Point section : coords) { 
			System.out.println(section.x + "," + section.y);
		}
		
		System.out.println("Move forward");
		ActionMessage newMes = new ActionMessage(ActionType.Move, shipId, pZero.getShip(shipId).getX() + 5, pZero.getShip(shipId).getY());		
		NewTurnMessage response = handler.doAction(newMes, 0)[0];		
		//System.out.println(response.getResponseString());
		
		System.out.println("Turn right");
		newMes = new ActionMessage(ActionType.TurnRight, shipId, pZero.getShip(shipId).getX() + 2, pZero.getShip(shipId).getY());		
		response = handler.doAction(newMes, 0)[0];		
		//System.out.println(response.getResponseString());
		System.out.println(cruiser.getOrientation());
		
		System.out.println("here is where the ship occupies now");
		Point[] newcoords = cruiser.getShipCoords();
		for(Point section : newcoords) { 
			System.out.println(section.x + "," + section.y);
		}
		
		System.out.println("Try to turn right again");
		newMes = new ActionMessage(ActionType.TurnRight, shipId, pZero.getShip(shipId).getX() + 2, pZero.getShip(shipId).getY());		
		response = handler.doAction(newMes, 0)[0];	
		//System.out.println(response.getResponseString());
		System.out.println(cruiser.getOrientation());	// note that its still south - didnt turn
		
		// here is where a collision is happening
		StarBoard board = pZero.getHandler().getBoard();
		SpaceThing spaceThing = board.getSpaceThing(2, 10);
		System.out.println(spaceThing == cruiser);
		
		
	}

}