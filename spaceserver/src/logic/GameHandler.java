package logic;

import logic.spacethings.BaseTile;
import logic.spacethings.CruiserShip;
import logic.spacethings.DestroyerShip;
import logic.spacethings.MineLayerShip;
import logic.spacethings.RadarBoatShip;
import logic.spacethings.TorpedoBoatShip;
import messageprotocol.ActionMessage;
import messageprotocol.MessageResponder;
import messageprotocol.NewTurnMessage;
import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.WeaponType;
import common.GameConstants.WinState;

public class GameHandler {
	
//	FleetCommander fc1, fc2;
	private FleetCommander[] players;
	static int gameIDCount = 0;
	private int gameID;
	private StarBoard board;
	private MessageResponder responder;
	
	public GameHandler() {
		// Create new game!
		gameID = gameIDCount++;
		board = new StarBoard(0);
		responder = new MessageResponder(this);
		players = new FleetCommander[2];
		players[0] = new FleetCommander(0, board, this);
		players[1] = new FleetCommander(1, board, this);
		board.generateAsteroids(players);
		for (int playerId = 0; playerId <= 1; playerId++){
			for (int basenum = 0; basenum < 10; basenum++){
				board.setSpaceThing(new BaseTile(playerId*29, basenum+10, players[playerId], board));
			}
		}
		
		players[0].addShip(new CruiserShip(1, 10, OrientationType.East, players[0], board));
		players[0].addShip(new CruiserShip(1, 11, OrientationType.East, players[0], board));
		players[0].addShip(new DestroyerShip(1, 12, OrientationType.East, players[0], board));
		players[0].addShip(new DestroyerShip(1, 13, OrientationType.East, players[0], board));
		players[0].addShip(new DestroyerShip(1, 14, OrientationType.East, players[0], board));
		players[0].addShip(new TorpedoBoatShip(1, 15, OrientationType.East, players[0], board));
		players[0].addShip(new TorpedoBoatShip(1, 16, OrientationType.East, players[0], board));
		players[0].addShip(new MineLayerShip(1, 17, OrientationType.East, players[0], board));
		players[0].addShip(new MineLayerShip(1, 18, OrientationType.East, players[0], board));
		players[0].addShip(new RadarBoatShip(1, 19, OrientationType.East, players[0], board));

		players[1].addShip(new CruiserShip(28, 10, OrientationType.West, players[1], board));
		players[1].addShip(new CruiserShip(28, 11, OrientationType.West, players[1], board));
		players[1].addShip(new DestroyerShip(28, 12, OrientationType.West, players[1], board));
		players[1].addShip(new DestroyerShip(28, 13, OrientationType.West, players[1], board));
		players[1].addShip(new DestroyerShip(28, 14, OrientationType.West, players[1], board));
		players[1].addShip(new TorpedoBoatShip(28, 15, OrientationType.West, players[1], board));
		players[1].addShip(new TorpedoBoatShip(28, 16, OrientationType.West, players[1], board));
		players[1].addShip(new MineLayerShip(28, 17, OrientationType.West, players[1], board));
		players[1].addShip(new MineLayerShip(28, 18, OrientationType.West, players[1], board));
		players[1].addShip(new RadarBoatShip(28, 19, OrientationType.West, players[1], board));

	}
	
	public GameHandler(int gameID){
		//TODO: load game
	}
	
	public NewTurnMessage[] doAction(ActionMessage aMessage, int playerID){
		responder.startMessage(aMessage, playerID);
		doAction(aMessage.getAction(), aMessage.getSpaceThingId(), 
				playerID, aMessage.getDestX(), aMessage.getDestY());
		return new NewTurnMessage[]{responder.getResponse(0), responder.getResponse(1)};
	}
	
	private void doAction(ActionType aType, int shipID, int playerID, int x, int y){
		if (playerID < 0 || playerID > 1){
			// TODO: Notification for invalid playerid
			return;
		}
		switch (aType){
		case DropMine:
			if (!players[playerID].useWeapon(WeaponType.Mine, shipID, x, y))
				responder.moveFailed();
			break;
		case FireCannon:
			if (!players[playerID].useWeapon(WeaponType.Cannon, shipID, x, y))
				responder.moveFailed();
			break;
		case FireTorpedo:
			if (!players[playerID].useWeapon(WeaponType.Torpedo, shipID, x, y))
				responder.moveFailed();
			break;
		case Explode:
			if(!players[playerID].useWeapon(WeaponType.Explosives, shipID, x, y))
				responder.moveFailed();
			break;
		case Move:
			if (players[playerID].moveShip(shipID, x, y) == 0)
				responder.moveFailed();
			break;
		case PickupMine:
			if (!players[playerID].pickupMine(shipID, x, y))
				responder.moveFailed();
			break;
		case Place:
			// TODO
			break;
		case Repair:
			// TODO
			break;
		case Turn180Left:
		case Turn180Right:
		case TurnLeft:
		case TurnRight:
			if (!players[playerID].turnShip(shipID, aType))
				responder.moveFailed();
			break;
		}
		
	}
	
	/**
	 *  Checks if anyone won this turn.
	 * @return id of player that won, or -1 if nobody won.
	 */
	public WinState checkWin(){
		boolean alive0 = players[0].hasLivingShips();
		boolean alive1 = players[1].hasLivingShips();
		
		if (!alive0 && !alive1){
			return WinState.Tie;
		} else if (alive0 && !alive1){
			return WinState.Player0;
		} else if (!alive0 && alive1){
			return WinState.Player1;
		} else {
			return WinState.Playing;
		}
	}
	
	public FleetCommander getFleetCommander(int playerID){
		if (playerID == 0 || playerID == 1){
			return players[playerID];
		}
		return null;
	}
	
	public StarBoard getBoard() {
		return board;
	}
	
	/**
	 * Call when there's a response the server needs to send the client
	 * (e.g. collision)
	 * @param message 
	 */
	public MessageResponder getMessageResponder() { 
		return this.responder;
	}
	
}
