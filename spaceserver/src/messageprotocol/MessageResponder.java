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
	
	public MessageResponder(GameHandler handler) {
		this.handler = handler;
		this.board = handler.getBoard();
	}
	
	LinkedList<GameStateMessage> createStateMessages(){
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
	
	
	private NewTurnMessage createNewTurnMessage(int playerID, ActionMessage action, LinkedList<GameStateMessage> state){
		FleetCommander fc = handler.getFleetCommander(playerID);
		return new NewTurnMessage(action, state,
				convertVisibility(fc.getRadarVisibility()), convertVisibility(fc.getSonarVisibility()));
	}
	
}
