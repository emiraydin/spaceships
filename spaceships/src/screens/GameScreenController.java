package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;


/**
 * Handles Logical Control for the Game Screen. 
 */
public class GameScreenController extends InputAdapter
{
	// Used for logging purposes. 
	private final String TAG = GameScreenController.class.getSimpleName(); 
	
	// Testing Utilities
	private Texture spriteTexture; 
	public Sprite sprite; 
	
	// Camera stuff 
	public GameScreenCamera cameraHelper; 
	
	public GameScreenController()
	{
		init(); 
	}
	
	private void init()
	{
		// Make this Screen start listening. 
		Gdx.input.setInputProcessor(this);
		
		// Initialize Sprites
		// TODO: Currently testing. Remake this so it uses the players choices. 
		initTestSprites(); 
		
		// Initialize Camera
		cameraHelper = new GameScreenCamera(); 
	}
	
	public void update(float deltaTime)
	{
		handleDebugInput(deltaTime); 
	}

	// Testing Stuff
	private void initTestSprites()
	{
		spriteTexture = new Texture(Gdx.files.internal("../spaceships-desktop/assets-raw/shipImages/F5S1.png")); 
		sprite = new Sprite(spriteTexture); 
		float randomX = MathUtils.random(-2.0f, 2.0f); 
		float randomY = MathUtils.random(-2.0f,2.0f); 
		System.out.println(sprite.getWidth()); 
		sprite.setSize(1f,4f); 
		sprite.setPosition(randomX, randomY); 
		sprite.setOrigin(sprite.getWidth() / 2.0f, sprite.getHeight() / 2.0f); 
		
	}
	
	private void handleDebugInput(float deltaTime)
	{
		// Selected Sprite Controls 
		float sprMoveSpeed = 5 * deltaTime; 
				
		if(Gdx.input.isKeyPressed(Keys.A))
		{
			sprite.translate(-sprMoveSpeed, 0); 		
		}
		if(Gdx.input.isKeyPressed(Keys.D))
		{
			sprite.translate(sprMoveSpeed, 0); 
	
		}
		if(Gdx.input.isKeyPressed(Keys.W))
		{
			sprite.translate(0, sprMoveSpeed); 

		}
		if(Gdx.input.isKeyPressed(Keys.S))
		{
			sprite.translate(0, -sprMoveSpeed); 
		}
		if(Gdx.input.isKeyPressed(Keys.E))
		{
			sprite.rotate(10); 
		}
		if(Gdx.input.isKeyPressed(Keys.Q))
		{
			sprite.rotate(-10); 
		}
	}
	
	@Override
	public boolean keyUp(int keycode)
	{
		if(keycode == Keys.ENTER)
		{
			System.exit(0); 
		}
		
		return false; 
	}
}
