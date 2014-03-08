package logic;

import java.awt.Window;

import common.GameConstants.ActionType;
import common.GameConstants.WeaponType;
import common.GameConstants.WinState;

public class GameHandler {
	
//	FleetCommander fc1, fc2;
	FleetCommander[] players;
	GameBoard board;
	
	public GameHandler() {
		// Create new game!
		//TODO: Technically GameBoard expects a gameID, but it doesn't really need one
		// So we could take it out of the constructor...
		board = new GameBoard(0);
		players = new FleetCommander[2];
		players[0] = new FleetCommander(0, board);
		players[1] = new FleetCommander(1, board);
	}
	
	public GameHandler(int gameID){
		//TODO: load game
	} 
	
	public void doAction(ActionType aType, int shipID, int playerID, int x, int y){
		//TODO: finish doAction method
		if (playerID < 0 || playerID > 1){
			// TODO: Notification for invalid playerid
			return;
		}
		switch (aType){
		case DropMine:
			players[playerID].useWeapon(WeaponType.Mine, shipID, x, y);
			break;
		case FireCannon:
			players[playerID].useWeapon(WeaponType.Cannon, shipID, x, y);
			break;
		case FireTorpedo:
			players[playerID].useWeapon(WeaponType.Torpedo, shipID, x, y);
			break;
		case Move:
			players[playerID].moveShip(shipID, x, y);
			break;
		case PickupMine:
			break;
		case Place:
			break;
		case Repair:
			break;
		case Turn180:
		case TurnLeft:
		case TurnRight:
			players[playerID].turnShip(shipID, aType);
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
}
