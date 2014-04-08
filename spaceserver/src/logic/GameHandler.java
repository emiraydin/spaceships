package logic;

import logic.spacethings.Asteroid;
import logic.spacethings.BaseTile;
import logic.spacethings.CruiserShip;
import logic.spacethings.DestroyerShip;
import logic.spacethings.KamikazeBoatShip;
import logic.spacethings.Mine;
import logic.spacethings.MineLayerShip;
import logic.spacethings.RadarBoatShip;
import logic.spacethings.SpaceThing;
import logic.spacethings.TorpedoBoatShip;
import messageprotocol.ActionMessage;
import messageprotocol.GameStateMessage;
import messageprotocol.MessageResponder;
import messageprotocol.NewTurnMessage;
import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;
import common.GameConstants.PlayerNumber;
import common.GameConstants.WeaponType;
import common.GameConstants.WinState;

public class GameHandler {
	
	private FleetCommander[] players;
	static int gameIDCount = 0;
	private int gameID;
	private StarBoard board;
	private MessageResponder responder;
	
	public GameHandler() {
		// Create new game!
		gameID = gameIDCount++;
		board = new StarBoard();
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
		
		players[0].createShips();
		players[1].createShips();
	}
	
	public GameHandler(NewTurnMessage message){
		board = new StarBoard();
		responder = new MessageResponder(this);
		players = new FleetCommander[2];
		players[0] = new FleetCommander(0, board, this);
		players[1] = new FleetCommander(1, board, this);
		
		for (GameStateMessage state : message.getState()){
			loadSpaceThing(state, message);
		}
	}
	
	private void loadSpaceThing(GameStateMessage state, NewTurnMessage message){
		SpaceThing s = null;
		FleetCommander owner = null;
		if (state.getOwner() != null){
			owner = players[convertPlayerNum(state.getOwner())];
		}
		switch (state.getType()){
		case Asteroid:
			s = new Asteroid(state.getPosX(), state.getPosY(), board);
			break;
		case BaseTile:
			s = new BaseTile(state.getPosX(), state.getPosY(), owner, board);
			break;
		case CruiserShip:
			s = new CruiserShip(state.getPosX(), state.getPosY(), state.getOrientation(), owner, board);
			break;
		case DestroyerShip:
			s = new DestroyerShip(state.getPosX(), state.getPosY(), state.getOrientation(), owner, board);
			break;
		case KamikazeBoatShip:
			s = new KamikazeBoatShip(state.getPosX(), state.getPosY(), state.getOrientation(), owner, board);
			break;
		case MineLayerShip:
			s = new MineLayerShip(state.getPosX(), state.getPosY(), state.getOrientation(), owner, board);
			break;
		case RadarBoatShip:
			s = new RadarBoatShip(state.getPosX(), state.getPosY(), state.getOrientation(), owner, board);
			if (message.getLongRadarEnabledShips().contains(state.getSpaceThingId())){
				((RadarBoatShip) s).turnOnLongRadar();
			}
			break;
		case TorpedoShip:
			s = new TorpedoBoatShip(state.getPosX(), state.getPosY(), state.getOrientation(), owner, board);
			break;
		case Mine:
			if (message.getAllMineParents().containsKey(state.getSpaceThingId())){
				s = new Mine(players[message.getMineParent(state.getSpaceThingId())], board)	;
			} else {
				s = new Mine(null, board);
				s.setX(state.getPosX());
				s.setY(state.getPosY());
			}
			break;
		default:
			break;
			
		}
		s.setID(state.getSpaceThingId());
		board.setSpaceThing(s);
	}
	
	private static int convertPlayerNum(PlayerNumber num){
		switch (num){
		case PlayerOne:
			return 0;
		case PlayerTwo:
			return 1;
		default:
			return -1;
		}
	}
	
	public NewTurnMessage[] doAction(ActionMessage aMessage, int playerID){
		responder.startMessage(aMessage, playerID);
		doAction(aMessage.getAction(), aMessage.getSpaceThingId(), 
				playerID, aMessage.getDestX(), aMessage.getDestY());
		return new NewTurnMessage[]{responder.getResponse(0), responder.getResponse(1)};
	}
	
	private void doAction(ActionType aType, int shipID, int playerID, int x, int y){
		if (playerID < 0 || playerID > 1){
			responder.moveFailed();
			return;
		}
		
		if (aType != ActionType.Initialize 
				&& players[playerID].getShip(shipID) == null){
			responder.moveFailed();
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
		case PlaceShip:
			if(!players[playerID].placeShip(shipID, x, y)) { 
				responder.moveFailed();
			}
			break;
		case Repair:
			if(!players[playerID].repairShip(shipID))
				responder.moveFailed();
			break;
		case ToggleRadar:
			if(!players[playerID].toggleRadar(shipID))
				responder.moveFailed();
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
