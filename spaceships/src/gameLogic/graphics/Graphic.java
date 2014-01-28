package gameLogic.graphics;

import com.badlogic.gdx.math.Vector2;

public abstract class Graphic {

    // How many pixels in 1 metre
    public final static int METRE_LENGTH = 16;
    protected Vector2 volume = new Vector2();
    protected Vector2 position = new Vector2();
	protected float rotation = 0;


    // Getters and setters

    // Dimensions
	public int getWidth() { return (int) this.volume.x; }
	public int getHeight() { return (int) this.volume.y; }
	public void setWidth(int x) {
		this.volume.x = x;
	}
	public void setHeight(int y) {
		this.volume.y = y;
	}

    // Position
    public Vector2 getPos() { return this.position; }
	public float getPosX() { return position.x; }
    public float getPosY() { return this.position.y; }
	public void setPosX(float posX) {
        this.position.x = posX;
    }
	public void setPosY(float posY) {
        this.position.y = posY;
    }
}
