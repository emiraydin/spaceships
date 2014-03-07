package logic.spacethings;

public class BaseTile extends SpaceThing {
	private int health;
	
	public BaseTile(int x, int y){
		super(x, y);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
