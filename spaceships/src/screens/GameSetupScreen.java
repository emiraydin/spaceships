package screens;

import state.GameState;
import state.SpaceThing;
import state.ships.AbstractShip;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * This is the screen where the players can setup the game. 
 * @author vikramsundaram
 *
 */
public class GameSetupScreen implements Screen 
{
	private Game currentGame; 

	public GameSetupScreen(Game g)
	{
		currentGame = g; 
		
		if(GameState.getPlayerId() == 0)
		{
			System.out.println("I am PlayerOne"); 
		}
		else
		{
			System.out.println("I am PlayerTwo"); 
		}
		
		
		for(int key  : GameState.getAllSpaceThings().keySet())
		{
			SpaceThing thing = GameState.getAllSpaceThings().get(key) ;
			
			if(thing instanceof AbstractShip)
			{	
				System.out.println(thing); 
			}
		}
	}
	
	@Override
	public void render(float delta)
	{
		// TODO Auto-generated method stub
		
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
