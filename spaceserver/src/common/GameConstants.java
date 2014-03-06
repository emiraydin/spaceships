package common;

public final class GameConstants {
	public static enum OrientationType { North, East, South, West }
	public static enum WeaponType { Cannon, Torpedo, Mine }
	public static enum VisibilityType { None, Sonar, Radar, Both}
	public static enum ActionType { Move, Place, FireCannon, FireTorpedo, DropMine, PickupMine, TurnLeft, TurnRight, Turn180, Repair }
	public static enum ArmourType { Normal, Heavy }
	public static enum PlayerNumber { World, PlayerOne, PlayerTwo }
	public static enum SpaceThingType { CruiserShip, DestroyerShip, MineLayerShip, RadarBoatShip, BaseTile, TorpedoShip, Asteroid, Mine }

	public static final int BOARD_HEIGHT = 30;
	public static final int BOARD_WIDTH= 30;
}
