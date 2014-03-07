package logic.spacethings;

public class BaseTile extends SpaceThing {
	private int health = 1;
	
	public BaseTile(int x, int y, int gameID){
		super(x, y, gameID);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
