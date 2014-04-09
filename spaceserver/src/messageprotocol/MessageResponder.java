package messageprotocol;

import java.util.HashMap;
import java.util.LinkedList;

import logic.FleetCommander;
import logic.GameHandler;
import logic.StarBoard;
import logic.spacethings.AbstractShip;
import logic.spacethings.Mine;
import logic.spacethings.MineLayerShip;
import logic.spacethings.RadarBoatShip;
import logic.spacethings.SpaceThing;

import common.GameConstants;
import common.GameConstants.WinState;

public class MessageResponder {
	StarBoard board;
	GameHandler handler;
	ActionMessage aMessage;
	boolean success;
	String response;
	int responseTo;				// for failed messages
	WinState winState;
	
	/**
	 * 
	 * @param handler
	 */
	public MessageResponder(GameHandler handler) {
		this.handler = handler;
		this.board = handler.getBoard();
	}
	
	/**
	 * Call this method at the beginning of every turn.
	 * @param aMessage Action Message of the move
	 */
	public void startMessage(ActionMessage aMessage, int responseTo){
		success = true;
		this.aMessage = aMessage;
		response = null;
		this.responseTo = responseTo;
	}
	
	/**
	 * Call to recieve the response message to be sent to the player.
	 * @param playerID ID of player to recieve response.
	 * @return The message to be sent to player
	 */
	public NewTurnMessage getResponse(int playerID){
		if (!success){
			return new NewTurnMessage(aMessage, success, response, null, null, null, playerID, responseTo, winState);
		}
		FleetCommander fc = handler.getFleetCommander(playerID);
		
		NewTurnMessage turnMessage = new NewTurnMessage(aMessage, success, response, createStateMessages(),
				convertVisibility(fc.getRadarVisibility()), convertVisibility(fc.getSonarVisibility()), playerID, responseTo, winState);
		addMinesAndLR(turnMessage);
		return turnMessage;
	}
	
	/**
	 * Call when a move fails.
	 */
	public void moveFailed(){
		success = false;
	}
	
	public void updateGameState(WinState winState) { 
		this.winState = winState;
	}
	
	/**
	 * Call when there's a response the server needs to send the client
	 * (e.g. collision)
	 * @param message 
	 */
	public void setResponseMessage(String response) { 
		this.response = response;
	}
	
	private LinkedList<GameStateMessage> createStateMessages(){
		HashMap<Integer, GameStateMessage> states = new HashMap<>();
		for (int x = 0; x < GameConstants.BOARD_WIDTH; x++){
			for (int y = 0; y < GameConstants.BOARD_HEIGHT; y++){
				SpaceThing thing = board.getSpaceThing(x, y);
				if (thing != null && !states.containsKey(thing.getID())){
					states.put(thing.getID(), thing.genGameStateMessage());
					if (thing instanceof MineLayerShip){
						MineLayerShip mShip = (MineLayerShip) thing;
						if(mShip.hasMines()){
							for (Mine m : mShip.getMines()){
								states.put(m.getID(), m.genGameStateMessage());
							}
						}
					}
				}
			}
		}
		
		// add dead ships that are no longer on the game board
		for(AbstractShip deadShip : board.deadShips) { 
			states.put(deadShip.getID(), deadShip.genGameStateMessage());
		}
		
		// add dead ships that are no longer on the game board
		for(Mine detonatedMine : board.detonatedMines) { 
			states.put(detonatedMine.getID(), detonatedMine.genGameStateMessage());
		}
		
		// literally just for initialization pls dont touch
		if(!(board.unplacedCounter == 0)) { 
			for(AbstractShip ship : board.unplacedShips) { 
				states.put(ship.getID(), ship.genGameStateMessage());
			}
			board.unplacedCounter--; 
		}
		
		return new LinkedList<GameStateMessage>(states.values());
	}
	
	private void addMinesAndLR(NewTurnMessage message){
		LinkedList<Integer> lreShips = new LinkedList<>();
		for (int x = 0; x < GameConstants.BOARD_WIDTH; x++){
			for (int y = 0; y < GameConstants.BOARD_HEIGHT; y++){
				SpaceThing thing = board.getSpaceThing(x, y);
				if (thing instanceof MineLayerShip){
					MineLayerShip mShip = (MineLayerShip) thing;
					if(mShip.hasMines()){
						for (Mine m : mShip.getMines()){
							message.addMineParent(m.getID(), mShip.getID());
						}
					}
				} else if (thing instanceof RadarBoatShip){
					if(((RadarBoatShip) thing).isLongRadarEnabled()){
						lreShips.add(thing.getID());
					}
				}
			}
		}
	}
	
	private static boolean[][] convertVisibility(int[][] visibility){
		boolean[][] boolVis = new boolean[visibility.length][visibility.length];
		for (int i = 0; i < visibility.length; i++){
			for (int j = 0; j < visibility.length; j++){
				boolVis[i][j] = (visibility[i][j] > 0);
			}
		}
		return boolVis;
	}
	
	
}
