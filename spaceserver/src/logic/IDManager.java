package logic;

import java.util.ArrayList;

public final class IDManager {
	private static ArrayList<Integer> thingIDCount;
//	private static int gameIDCount = 0;
	
	public static int nextID(int gameID){
		if (gameID < thingIDCount.size() && gameID > -1){
			thingIDCount.set(gameID, thingIDCount.get(gameID));
			return thingIDCount.get(gameID) - 1;
		}
		return -1;
	}
	
	public static int nextGameID(){
		thingIDCount.add(0);
		return thingIDCount.size() - 1;
	}
}
