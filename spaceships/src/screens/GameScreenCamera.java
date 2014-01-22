package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Helps with handling the camera controls for the GameScreen. 
 */
public class GameScreenCamera
{
	// Used for Logging purposes
	private final String TAG = GameScreenCamera.class.getSimpleName(); 
	
	// The Max and Min the Camera can zoom. 
	private final float MAX_ZOOM_IN = 1f; 
	private final float MAX_ZOOM_OUT = 25.0f; 
	private final float STANDARD_ZOOM = 1f; 
	
	// Various variables the effect the placement of the camera / relation to a sprite. 
	private Vector2 position;
	private Sprite target; 
	private float zoom;
	
	/**
	 * Constructor for a GameScreen Camera Helper
	 */
	public GameScreenCamera()
	{
		position = new Vector2(); 
		zoom = STANDARD_ZOOM; 
	}
	
	/**
	 * Update the camera so it continues to follow the current target. 
	 * @precondition: The Target must be set. 
	 * @postcondition: The camera will follow the position of said target: AS THE TARGET moves. 
	 * @param deltaTime: The timelapse between consecutive frame renderings. 
	 */
	public void update(float deltaTime)
	{
		if(target == null)
		{
			return; 
		}
		
		position.x = target.getX() + target.getOriginX(); 
		position.y = target.getY() + target.getOriginY(); 
	}
	
	
	/**
	 * Sets the camera position. 
	 * @postcondition: The Camera will now focus its position on the input (x,y) coordinates. 
	 * @param x: The 'x' position on the plane. 
	 * @param y: The 'y' position on the plane. 
	 */
	public void setPosition(float x, float y)
	{
		this.setPosition(x, y); 
	}
	
	/**
	 * Returns the current position as a Vector2 object
	 * @return: A vector2 object with (x,y) pair indicating current position. 
	 */
	public Vector2 getPosition()
	{
		return position; 
	}
	
	/**
	 * Sets the camera zoom to the input zoom variable.
	 * @param zoom: The amount to zoom by. 
	 * @precondition: Will not zoom by more then the Max and Min zoom. 
	 * @postcondition: The camera will have zoomed in by an appropriate amount. 
	 */
	public void setZoom(float zoom)
	{
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT); 
	}
	
	/**
	 * Returns the current Zoom. 
	 * @return: The amount the camera is currently zoomed in 
	 */
	public float getZoom()
	{
		return zoom; 
	}
	
	
	/**
	 * Sets the current target: I.e. the sprite the camera is currently following. 
	 * @param Sprite target: A sprite that the Camera will now follow. 
	 * @postcondition: The camera in-game will now follow that specified target. 
	 */
	public void setTarget(Sprite target)
	{
		this.target = target; 
	}
	
	/**
	 * Returns the current target for debugging purposes. 
	 * @return: The current target Sprite. 
	 * @precondition: The target must be set, or null will be returned. 
	 */
	public Sprite getTarget()
	{
		Gdx.app.log(TAG, "Returning the current target: " + target); 
		return target; 
	}
	
	/**
	 * Helps assert that the Camera has a current target. 
	 * @return: Boolean representing whether the current target is set to null or not. 
	 */
	public boolean hasTarget()
	{
		return target != null; 
	}
	
	/**
	 * Asserts that the input sprite is the current target
	 * @param: Sprite anotherTarget, the Sprite to check for if duplicates exists. 
	 */
	public boolean hasTarget(Sprite anotherTarget)
	{
		return target.equals(anotherTarget); 
	}
	
	/**
	 * Applies the position of the input OrthographicCamera to the current camera. 
	 * @param camera: The new camera to duplicate. 
	 */
	public void applyTo (OrthographicCamera camera)
	{
		camera.position.x = position.x; 
		camera.position.y = position.y; 
		camera.zoom = zoom; 
		camera.update(); 
	}
	
	
	
}
