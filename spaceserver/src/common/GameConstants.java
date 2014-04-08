package common;

public final class GameConstants {
	public static enum OrientationType { North, East, South, West }
	public static enum WeaponType { Cannon, Torpedo, Mine, Explosives }
	public static enum VisibilityType { None, Sonar, Radar, Both}
	public static enum ActionType { Move, Initialize, FireCannon, FireTorpedo, DropMine, PickupMine, Explode, TurnLeft, TurnRight, Turn180Left, Turn180Right, Repair, Yes, No, PlaceShip, ToggleRadar}
	public static enum ArmourType { Normal, Heavy }
	public static enum PlayerNumber { World, PlayerOne, PlayerTwo }
	public static enum SpaceThingType { CruiserShip, DestroyerShip, MineLayerShip, RadarBoatShip, KamikazeBoatShip, BaseTile, TorpedoShip, Asteroid, Mine }

	public static enum ActionEffect {Error, Nothing, MineExploded, ShipHit, BaseHit, ShipSunk, BaseDestroyed};
	public static enum WinState {Playing, Player0, Player1, Tie}
	
	public static final int BOARD_HEIGHT = 30;
	public static final int BOARD_WIDTH= 30;
	public static final int NUM_ASTEROIDS = 24;
}
