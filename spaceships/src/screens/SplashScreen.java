package screens;

import gameLogic.Spaceships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * Splash Screen for the Game. 
 * Initial Screen that the player sees when logging into the game. 
 * TODO: Develop an image file for the Splash Screen. 
 * @author vikramsundaram
 *
 */
public class SplashScreen extends InputAdapter implements Screen 
{
	private static final String TAG = SplashScreen.class.getSimpleName(); 
	private Spaceships theGame;
	private SpriteBatch spriteBatch; 
	private Texture splashLogo; 
	
	public SplashScreen(Spaceships game)
	{
		Gdx.app.log(TAG, "New Splash Screen Creation"); 
		theGame = game; 
	}
	
	@Override
	public void render(float delta)
	{ 
		// Sets the Clear Screen Color as Black
		 Gdx.gl.glClearColor(0x0/255.0f, 0x0/255.0f, 0x0/255.0f, 0x0/255.0f);
		 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         
		 // Draws the appropriate Sprites. 
		 spriteBatch.begin();
         spriteBatch.draw(splashLogo, 0, 0);
         spriteBatch.end();

	}
	
	// This method is called as soon as the SplashScreen gets control of the application. 
	// Used to set the screen up. 
	// TODO: Use a proper splash image for this. Constants.APP_WIDTH x Constants.APP_HEIGHT size. 
	@Override
	public void show()
	{
		Gdx.input.setInputProcessor(this); 
		Gdx.app.log(TAG, "Setting up Splash Screen"); 
		spriteBatch = new SpriteBatch(); 
		splashLogo = new Texture(Gdx.files.internal("../spaceships-desktop/assets-raw/splash.png")); 
		Gdx.app.log(TAG, "Created Texture: "+ splashLogo); 
	}
	
	@Override
	public void resize(int width, int height)
	{
		Gdx.app.log(TAG, "Resizing Screen to: " + width + "x" + height); 
	}
	
	@Override
	public void hide()
	{
		Gdx.app.log(TAG, "Hiding Screen"); 
		
	}

	@Override
	public void pause()
	{
		Gdx.app.log(TAG, "Pausing Screen"); 
		
	}

	@Override
	public void resume()
	{
		Gdx.app.log(TAG, "Resuming Screen"); 
		
	}

	@Override
	public void dispose()
	{
		Gdx.app.log(TAG, "Disposing Screen"); 
	}
	
	@Override
	public boolean keyUp(int keycode)
	{
		if(keycode == Keys.ENTER)
		{
			theGame.setScreen(new GameScreen(theGame)); 
		}
		
		return false; 
	}
	
}
