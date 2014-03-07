package logic.spacethings;

public abstract class SpaceThing {
	private int x, y;
	private int id;
	
	public SpaceThing(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getID(){
		return id;
	}
	
}
