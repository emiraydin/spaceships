package screens;

import gameLogic.Spaceships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class HomeScreen implements Screen
{
	private Spaceships theGame; 
	
	// Default Constructor 
	public HomeScreen(Spaceships game)
	{
		this.theGame = game; 
	}

	@Override
	public void render(float delta)
	{
		// Sets the Clear Screen Color as Black
		Gdx.gl.glClearColor(1, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show()
	{
		// TODO Auto-generated method stub
		
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
