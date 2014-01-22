package gameLogic;

import screens.GameScreen;
import screens.SplashScreen;

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
	
	// Used for Logging
	private static final String TAG = Spaceships.class.getSimpleName(); 

	@Override
	public void create() 
	{
		// Set the log to Debug Level Logs. 
		Gdx.app.setLogLevel(Application.LOG_DEBUG); 
		Gdx.app.log(TAG, "Starting new Instance");
		
		// Set the Screen to the Splash Screen
		this.setScreen(new GameScreen(this));
		Gdx.app.log(TAG, "Initializing the Splash Screen"); 
	}
}
