package screens;

import gameLogic.Constants;
import gameLogic.Descriptions;
import state.GameState;
import state.SpaceThing;
import state.ships.AbstractShip;
import actors.ActorState;
import actors.BackgroundActor;
import actors.GameSetupTileActor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * This is the screen where the players can setup the game. 
 * @author vikramsundaram
 *
 */
public class GameSetupScreen implements Screen 
{
	private Game currentGame;
	private GameSetupTileActor[][] gameBoard; 
	private Stage stage, uiStage; 
	private OrthographicCamera cam; 
	private Table menuTable; 						// The Menu That allows the user to select shit (stuff). 
	private Skin skin; 
	private Label description; 
	public GameSetupTileActor currentSelectedTile = null; 
	
	
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
				
			}
		}
	}
	
	@Override
	public void render(float delta)
	{
		// Clear the Screen with black color. 
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1); 
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
		
		
		cam.update(); 
		stage.act(delta); 
		stage.draw(); 
		uiStage.act(delta); 
		uiStage.draw(); 
		
	}

	@Override
	public void resize(int width, int height)
	{
		cam.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		cam.update();
	}

	
	
	
	@Override
	public void show()
	{
		setUpSkin(); 
		gameBoard = new GameSetupTileActor[30][30]; 
		stage = new Stage(); 
		cam = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_WIDTH); 
		cam.position.set(23, 15, 0); 
		cam.zoom = 6; 
		cam.update(); 
		stage.setCamera(cam); 
		
		
		
		// Set the Background First; 
		stage.addActor(new BackgroundActor()); 
		
		for(int i = 0; i < 30; i++)
		{
			for(int k = 0; k < 30; k++)
			{
				gameBoard[i][k] = new GameSetupTileActor(i,k, this); 
				stage.addActor(gameBoard[i][k]); 
			}
		}
		
		if(GameState.getPlayerId() == 0)
		{
			drawPlayerOne();
		}
		else
		{
			//drawPlayerTwo(); 
		}
		
		setUpMenuTable(); 
		Gdx.input.setInputProcessor(new InputMultiplexer(stage, uiStage));
	}

	private void drawPlayerOne()
	{
		for(int x = 0; x < gameBoard.length; x++)
		{
			for(int y = 0; y < gameBoard[0].length; y++)
			{
				if((x == 0 && y == 10 )||(x == 0) && y == 20)
				{
					gameBoard[x][y].drawSelectable(); 
				}
				
				if(x == 1 && 11 <= y && y <= 19)
				{
					gameBoard[x][y].drawSelectable(); 
				}
			}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		
	}

	
	private void setUpSkin()
	{
		skin = new Skin(); 
		
		// Generate a 1x1 white texture and store it in the skin named "white" 
		Pixmap pixmap = new Pixmap(32,32,Format.RGBA8888); 
		pixmap.setColor(Color.WHITE);
		pixmap.fill(); 
		Texture texture = new Texture(pixmap); 
		
		// Add the Texture to Skin. 
		skin.add("white", texture); 
		
		// Create a Font and add it to the skin. 
		BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt")); 
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		skin.add("default", font);
		
		// Create a smaller font and add it to the skin. 
		BitmapFont font2 = new BitmapFont(Gdx.files.internal("fonts/whiteFontSmall.fnt")); 
		font2.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		skin.add("defaultSmall", font2);
	}
	
	private void setUpMenuTable()
	{
		uiStage = new Stage(); 
		menuTable = new Table(); 
		
		// The Basic Properties of the Table. 
		float width = Gdx.graphics.getWidth() * 0.3f, height = Gdx.graphics.getHeight() * 0.9f; 
		float positionx = Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() * 0.33f), positiony = Gdx.graphics.getHeight() * 0.05f; 
		menuTable.setSize(width,height);  
		menuTable.setPosition(positionx,positiony);
	
		// Create and setup the background. 
		menuTable.setBackground(generateMenuTableBackground(width, height)); 
		
		// The Basic Style for the header. 
		LabelStyle headerTextStyle = new LabelStyle(); 
		headerTextStyle.font = skin.getFont("default"); 
		headerTextStyle.background = skin.newDrawable("white", new Color(1,0,0,0.2f));
		
		// Set the Header for the GUI Table. 
		Label header = new Label("Asteria", headerTextStyle); 
		header.setAlignment(Align.center); 
		
		// Create the Description Label 
		LabelStyle lStyle = new LabelStyle(); 
		lStyle.font = skin.getFont("defaultSmall"); 
		lStyle.background = skin.newDrawable("white", new Color(1,1,1,0.4f));
		lStyle.fontColor = new Color(0,1,1,1f); 
		description = new Label(Descriptions.PLACE_INTRO_TEXT, lStyle); 
		description.setWrap(true); 
		description.setAlignment(Align.center); 
		
		// Create the Clickable Options. 
		TextButtonStyle style = new TextButtonStyle(); 
		style.font = skin.getFont("default"); 
		style.fontColor = Color.WHITE; 
		style.over = skin.newDrawable("white", Color.BLUE); 
		
		TextButton t1 = new TextButton("Use Default Setting", style); 
		t1.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				description.setText(Descriptions.CRUISER); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				description.setText(Descriptions.PLACE_INTRO_TEXT); 
			}
		}); 
		
		TextButton t2 = new TextButton("Place Destroyer", style);
		t2.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				description.setText(Descriptions.DESTROYER); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				description.setText(Descriptions.PLACE_INTRO_TEXT); 
			}
		}); 
		
		
		
		TextButton t3 = new TextButton("Place Mine Layer",style);
		t3.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				description.setText(Descriptions.LAYER); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				description.setText(Descriptions.PLACE_INTRO_TEXT); 
			}
		}); 
		
		
		
		TextButton t4 = new TextButton("Place RadarBoat",style);
		t4.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				description.setText(Descriptions.LAYER); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				description.setText(Descriptions.PLACE_INTRO_TEXT); 
			}
		}); 
		
		
		
		TextButton t5 = new TextButton("Place TorpedoBoat", style);
		t5.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				description.setText(Descriptions.LAYER); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				description.setText(Descriptions.PLACE_INTRO_TEXT); 
			}
		}); 
		
		
		
		TextButton t6 = new TextButton("Place KamikazeBoat",style);
		t6.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				description.setText(Descriptions.LAYER); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				description.setText(Descriptions.PLACE_INTRO_TEXT); 
			}
		}); 
		


		
		menuTable.add(header).width(width).height(height * 0.1f).row();
		menuTable.add(t1).width(width).height(height * 0.1f).row();
		menuTable.add(t2).width(width).height(height * 0.1f).row();
		menuTable.add(t3).width(width).height(height * 0.1f).row();
		menuTable.add(t4).width(width).height(height * 0.1f).row();
		menuTable.add(t5).width(width).height(height * 0.1f).row();
		menuTable.add(t6).width(width).height(height * 0.1f).row();
		menuTable.add(description).width(width).height(height * 0.3f); 
		
		uiStage.addActor(menuTable); 
	}

	private TextureRegionDrawable generateMenuTableBackground(float width, float height)
	{
		Pixmap pixmap = new Pixmap((int) width, (int) height, Format.RGBA8888);
		
		// The Tables Base Background Color 
		pixmap.setColor(1,1,1,0.2f); 
		pixmap.fill(); 
		
		// Not Sure if we have to do all this - but it works. 
		Texture newTexture = new Texture(pixmap);
		TextureRegion newTexture2 = new TextureRegion(newTexture); 
		TextureRegionDrawable drawable = new TextureRegionDrawable(newTexture2);
		
		pixmap.dispose(); 
		return drawable; 
	}

	public void changeSelectedTile(GameSetupTileActor newTile)
	{
		if(currentSelectedTile != null)
		{
			if(GameState.getPlayerId() == 0)
			{
				int x = (int) currentSelectedTile.getX(), y = (int) currentSelectedTile.getY(); 
				if(((x == 0 && y == 10 )||(x == 0) && y == 20) || (x == 1 && 11 <= y && y <= 19))
				{
					currentSelectedTile.drawSelectable(); 
				}
				else
				{
					currentSelectedTile.drawStandard(); 
				}
			}
		}
		
		currentSelectedTile = newTile; 
		newTile.drawNotSelectable(); 
	}

}
