package gameLogic;

import screens.GameScreen;
import util.TriggerClient;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Main Class, However action is delegated to various screens. 
 */
public class Spaceships extends Game implements ApplicationListener 
{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private TriggerClient data; 
	
	// Used for Logging
	private static final String TAG = Spaceships.class.getSimpleName(); 

	public Spaceships() {
		data = new TriggerClient(); 
		new Thread(data).start();
	}
	
	@Override
	public void create() 
	{
		Texture.setEnforcePotImages(false);
		
		// Set the log to Debug Level Logs. 
		Gdx.app.setLogLevel(Application.LOG_DEBUG); 
		Gdx.app.log(TAG, "Starting new Instance");
		
		// Set the Screen to the Splash Screen
		this.setScreen(new GameScreen());
	}
}
