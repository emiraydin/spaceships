package state.weapons;

public abstract class AbstractWeapon {
	int damage;
	
	public void fire(int x, int y) {
		
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	
	boolean validate(int x, int y) {
		return false;
	}

	public void doAction(int x, int y) {
		
	}

}
