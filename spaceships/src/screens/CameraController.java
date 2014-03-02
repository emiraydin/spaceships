package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Helps abstract a lot of the Camera Control logic out of the GameScreenController. 
 * @author Vikram
 *
 */
public class CameraController 
{
	/*
	 * Instance Variables
	 */
	private OrthographicCamera camera; 				// The Camera to apply changes to. 
	private float MAX_VIEWPORT;						// The maximum out we can zoom. 
	private float MIN_VIEWPORT; 					// The maximum in we can zoom. 
	
	/**
	 * Constructor 
	 */
	public CameraController(OrthographicCamera camera)
	{
		this.camera = camera; 
	}

	/**
	 * Translates the Camera on the coordinate plane. 
	 * @param x : The amount in the x direction
	 * @param y : The amount in the y direction. 
	 * @param delta : The System Delta Value 
	 */
	public void translate(float x, float y, float delta) 
	{
		camera.translate(x,y);
	}

	/**
	 * Updates the zoom. 
	 */
	private void updateZoom(float updateBy)
	{
		camera.zoom += updateBy; 
	}

	/**
	 * Updates the camera based on userinput. 
	 * @param delta : The System Delta Values
	 */
	public void updateCamera(float delta) 
	{
		float camMoveSpeed = 5 * delta; 
		float acceleration = 5f; 
		
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
		{
			camMoveSpeed *= acceleration; 
		}
		if(Gdx.input.isKeyPressed(Keys.P))
		{
			updateZoom(camMoveSpeed);
		}
		if(Gdx.input.isKeyPressed(Keys.O))
		{
			updateZoom(-camMoveSpeed);
		}
		if(Gdx.input.isKeyPressed(Keys.W))
		{
			translate(0,camMoveSpeed,delta);
		}
		if(Gdx.input.isKeyPressed(Keys.S))
		{
			translate(0,-camMoveSpeed, delta);
		}
		if(Gdx.input.isKeyPressed(Keys.D))
		{
			translate(camMoveSpeed, 0, delta);
		}
		if(Gdx.input.isKeyPressed(Keys.A))
		{
			translate(-camMoveSpeed,0,delta);
		}
	}
	

}
