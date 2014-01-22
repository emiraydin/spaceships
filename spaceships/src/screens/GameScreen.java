package screens;

import gameLogic.Assets;
import gameLogic.Spaceships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;

/**
 * The main gameplay screen. 
 * Where the actual gameplay occurs and the goal of winning the game takes place. 
 */
public class GameScreen implements Screen 
{
	//  Used for Logging purposes. 
	private final String TAG = GameScreen.class.getSimpleName();  
	
	// The Controller and Renderer for the Gamescreen
	private GameScreenRenderer renderer; 
	private GameScreenController controller; 
	
	// The Game instance
	private Spaceships game; 
	
	
	public GameScreen(Spaceships game)
	{
		this.game = game; 
	}
	
	@Override
	public void render(float delta)
	{
		controller.update(delta); 
		
		// Sets the Clear Screen Color as Cornflower Blue
		Gdx.gl.glClearColor(0x0/255.0f, 0x0/255.0f, 0x0/255.0f, 0x0/255.0f);
				
		// Actually clears the Screen
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
		
		renderer.render(); 
		
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show()
	{
		
		controller = new GameScreenController(); 
		renderer = new GameScreenRenderer(controller); 
		// Load Assets
		try
		{
			Assets.INSTANCE.init(new AssetManager()); 
		}
		catch(NullPointerException e)
		{
			
		}
		
		
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
		// Load and cache sprites again. 
		Assets.INSTANCE.init(new AssetManager()); 
		
	}

	@Override
	public void dispose()
	{
		// Dispose Assets
		Assets.INSTANCE.dispose(); 
		
	}
	
}
