package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/** 
 * The basic Splash Screen. 
 * @author vikramsundaram
 *
 */
public class SplashScreen implements Screen
{
	// The Splash Screen Texture
	private Texture splashScreenImage; 
	private SpriteBatch batch;
	private Game current; 
	
	public SplashScreen(Game currentGame)
	{
		current = currentGame; 
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        batch.draw(splashScreenImage, 0, 0);
        batch.end();
        
        if(Gdx.input.isKeyPressed(Keys.SPACE))
        {
        	current.setScreen(new MainMenuScreen(current)); 
        }

	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void show()
	{
		// Initialize the SpriteBatch and the Splash Screen. 
		batch = new SpriteBatch(); 
		splashScreenImage = new Texture("images/spaceBackground.png");

	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub

	}

}
