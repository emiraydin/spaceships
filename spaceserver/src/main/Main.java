package main;

public class Main {
	public static enum WeaponType {CANNON, TORPEDO, MINE};
	public static enum VisibilityType {NONE, SONAR, RADAR, BOTH};
	public static enum OrientationType {NORTH, EAST, SOUTH, WEST};
	public static enum ActionType {MOVE, PLACE, FIRECANNON, FIRETORPEDO, 
		DROPMINE, PICKUPMINE, TURNLEFT, TURNRIGHT, TURN180, REPAIR};
	
	public static void main(String[] args) {
		System.out.println("Hello world.");
	}

}
