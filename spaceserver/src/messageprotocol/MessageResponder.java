package messageprotocol;

import java.util.HashMap;
import java.util.LinkedList;

import logic.FleetCommander;
import logic.GameHandler;
import logic.StarBoard;
import logic.spacethings.Mine;
import logic.spacethings.MineLayerShip;
import logic.spacethings.SpaceThing;

import common.GameConstants;

public class MessageResponder {
	StarBoard board;
	GameHandler handler;
	ActionMessage aMessage;
	boolean success;
	String response;
	
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
	public void startMessage(ActionMessage aMessage){
		success = false;
		this.aMessage = aMessage;
		response = null;
	}
	
	/**
	 * Call to recieve the response message to be sent to the player.
	 * @param playerID ID of player to recieve response.
	 * @return The message to be sent to player
	 */
	public NewTurnMessage getResponse(int playerID){
		if (!success){
			return new NewTurnMessage(aMessage, success, response, null, null, null);
		}
		FleetCommander fc = handler.getFleetCommander(playerID);
		
		return new NewTurnMessage(aMessage, success, response, createStateMessages(),
				convertVisibility(fc.getRadarVisibility()), convertVisibility(fc.getSonarVisibility()));
	}
	
	/**
	 * Call when a move fails.
	 */
	public void moveFailed(){
		success = false;
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
								states.put(thing.getID(), m.genGameStateMessage());
							}
						}
					}
				}
			}
		}
		return new LinkedList<GameStateMessage>(states.values());
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
