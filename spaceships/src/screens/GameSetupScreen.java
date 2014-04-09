package screens;

import gameLogic.Constants;
import gameLogic.Descriptions;

import java.util.LinkedList;

import messageprotocol.ActionMessage;
import messageprotocol.ServerMessageHandler;
import state.Asteroid;
import state.GameState;
import state.Mine;
import state.SpaceThing;
import state.ships.AbstractShip;
import state.ships.CruiserShip;
import state.ships.DestroyerShip;
import state.ships.KamikazeBoatShip;
import state.ships.MineLayerShip;
import state.ships.RadarBoatShip;
import state.ships.TorpedoShip;
import actors.AsteroidActor;
import actors.BackgroundActor;
import actors.GameSetupTileActor;
import actors.ShipActor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import common.GameConstants.ActionType;
import common.GameConstants.PlayerNumber;

/**
 * This is the screen where the players can setup the game. 
 * @author vikramsundaram
 *
 */
public class GameSetupScreen implements Screen 
{
	private Game currentGame;
	private GameSetupTileActor[][] gameBoard; 
	private AsteroidActor[][] asteroids; 
	private Stage stage, uiStage; 
	private OrthographicCamera cam; 
	private Table menuTable; 						// The Menu That allows the user to select shit (stuff). 
	private Skin skin; 
	private Label description, helpText; 
	private LinkedList<AbstractShip> unplacedShips;
	private String helpTextString = "Welcome, to Asteria: Battle for the Frontier. \n\n You are about to embark on the battle of a lifetime. \n\n Riches, power, and glory await the victor. \n\n Start by selecting your ships.\n Just click a highlighted tile and select a type.\n Once both players ships are selected, the action will commence. \n\n Select Default ships BEFORE setting other ships to let your \ncouncil build your arsenal.\n\nBeware, if you place ships and then ask the council to help\n they will get offended, and not lot you fight.\n\n Click J to hide this Dialogue and H to show it again. "; 
	public GameSetupTileActor currentSelectedTile = null; 
	public PlayerNumber currentPlayer; 
	public int cruisers = 0, destroyers = 0, radar = 0, layer = 0, torpedo = 0, kami = 0; 
	
	
	public GameSetupScreen(Game g)
	{
		stage = new Stage(); 
		cam = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_WIDTH); 
		cam.position.set(23, 15, 0); 
		cam.zoom = 6; 
		cam.update(); 
		stage.setCamera(cam); 
		currentGame = g; 
		asteroids = new AsteroidActor[30][30]; 
		

		gameBoard = new GameSetupTileActor[30][30]; 
		
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
		
		unplacedShips = new LinkedList<AbstractShip>(); 
		
		// Just used for Debugging Purposes. 
		if(GameState.getPlayerId() == 0)
		{
			System.out.println("I am PlayerOne");
			currentPlayer = PlayerNumber.PlayerOne; 
		}
		else
		{
			System.out.println("I am PlayerTwo"); 
			currentPlayer = PlayerNumber.PlayerTwo; 
		}
		
		
		for(int key  : GameState.getAllSpaceThings().keySet())
		{
			SpaceThing thing = GameState.getAllSpaceThings().get(key) ;
			
			if(thing instanceof AbstractShip)
			{	
				if(thing.getOwner() == currentPlayer)
				{
					unplacedShips.add((AbstractShip) thing); 
					
					if(thing instanceof CruiserShip)
					{
						cruisers++; 
					}
					else if(thing instanceof DestroyerShip)
					{
						destroyers++; 
					}
					else if(thing instanceof RadarBoatShip)
					{
						radar++; 
					}
					else if(thing instanceof TorpedoShip)
					{
						torpedo++; 
					}
					else if(thing instanceof MineLayerShip)
					{
						layer++; 
					}
					else if(thing instanceof KamikazeBoatShip)
					{
						kami++; 
					}
					else
					{
						System.out.println("LOOK SOME WEIRD SHIT: " + thing); 
					}
				}
			}
		}
		
		for(int i = 0; i < 30; i++)
		{
			for (int k = 0; k < 30; k++)
			{
				asteroids[i][k] = new AsteroidActor(i,k); 
				asteroids[i][k].setVisible(false); 
				stage.addActor(asteroids[i][k]); 
			}
		}
		
		
		resetAsteroids(); 
		
		System.out.println("Cruisers: " + cruisers + " destroyers: "+ destroyers + " radar: " + radar + " Torpedo: " + torpedo + " Layer: " + layer + " kami: "+ kami); 
		System.out.println(unplacedShips.size()); 
	}
	
	public boolean isValidPlaceShipLocation(int x, int y) { 
		if(currentPlayer == PlayerNumber.PlayerOne) { 
			if(x == 0) { 
				if(y == 9 || y == 20) { 
					return true;
				}
			}
			if(x == 1) { 
				if(y >= 10 && y <= 19) { 
					return true;
				}
			}
		}		
		else if(currentPlayer == PlayerNumber.PlayerTwo) { 
			if(x == 29) { 
				if(y == 9 || y == 20) { 
					return true;
				}
			}
			if(x == 28) { 
				if(y >= 10 && y <= 19) { 
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void render(float delta)
	{
		// Clear the Screen with black color. 
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1); 
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
		
		if(Gdx.input.isKeyPressed(Keys.H)){
			helpText.setVisible(true); 
		}
		if(Gdx.input.isKeyPressed(Keys.J))
		{
			helpText.setVisible(false); 
		}
		if(Gdx.input.isKeyPressed(Keys.L))
		{
			currentGame.setScreen(new GameScreen(currentGame));
		}
		
		resetAsteroids(); 
		
		// Checks if both Players have placed their ships and agreed on Asteroids. 
		// If so, then the Game is Started. 
		advanceScreen(); 
		
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

	private void resetAsteroids()
	{
		for(int i = 0; i < 30; i++)
		{
			for (int k = 0; k < 30; k++)
			{
				if(asteroids[i][k]!= null)
				{ 
					asteroids[i][k].setVisible(false); 
				}
			}
		}
		for(int q : GameState.getAllSpaceThings().keySet())
		{
			SpaceThing thing = GameState.getAllSpaceThings().get(q); 
			
			if(thing instanceof Asteroid)
			{
				asteroids[thing.getX()][thing.getY()].setVisible(true); 
			}
		}
		
		System.out.println(stage.getActors().size); 
	}
	
	@Override
	public void show()
	{
		// Basic Stuff we need to setup first. 
		setUpSkin(); 
		
		// Draw the GameBoard Depending on the Player. 
		if(currentPlayer == PlayerNumber.PlayerOne)
		{
			drawPlayerOne();
		}
		else
		{
			drawPlayerTwo(); 
		}
		
		// Draw the Menu Table 
		setUpMenuTable(); 
		
		// Draw the Help Text. 
		setUpHelpText(); 
		
		Gdx.input.setInputProcessor(new InputMultiplexer(stage, uiStage));
	}

	private void drawPlayerOne()
	{
		for(int x = 0; x < gameBoard.length; x++)
		{
			for(int y = 0; y < gameBoard[0].length; y++)
			{
				if((x == 0 && y == 9 )||(x == 0) && y == 20)
				{
					gameBoard[x][y].drawSelectable(); 
				}
				
				if(x == 1 && 10 <= y && y <= 19)
				{
					gameBoard[x][y].drawSelectable(); 
				}
			}
		}
	}
	
	private void drawPlayerTwo()
	{
		for(int x = 0; x < gameBoard.length; x++)
		{
			for(int y = 0; y < gameBoard[0].length; y++)
			{
				if((x == 29 && y == 9) || (x == 29 && y == 20))
				{
					gameBoard[x][y].drawSelectable(); 
				}
				
				if(x == 28 && 10 <= y && y <= 19)
				{
					gameBoard[x][y].drawSelectable(); 
				}
			}
		}
	}

	private void setUpHelpText()
	{
		LabelStyle helpTextStyle = new LabelStyle(); 
		helpTextStyle.background = skin.newDrawable("white", new Color(0,0,0,1f)); 
		helpTextStyle.font = skin.getFont("default"); 
		helpTextStyle.fontColor = Color.WHITE; 
		
		helpText = new Label(helpTextString, helpTextStyle); 
		helpText.setWrap(true);
		helpText.setAlignment(Align.center); 
		helpText.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		helpText.setPosition(0, 0); 
		helpText.setVisible(true); 
		
		uiStage.addActor(helpText); 
	}

	private void advanceScreen()
	{
		for(int key : GameState.getAllSpaceThings().keySet())
		{
			SpaceThing thing = GameState.getAllSpaceThings().get(key); 
			if((thing.getX() < 0 || thing.getY() < 0) && (!(thing instanceof Mine)))
			{ 
				return; 
			}
		}
		currentGame.setScreen(new GameScreen(currentGame)); 
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
		menuTable.top(); // Set things to the top. 
		
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
		
		TextButton t1 = new TextButton("Use Default Settings", style); 
		t1.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				setDefaultShipLocations(); 
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				description.setText(Descriptions.CRUISER); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				description.setText("Let your council decide where the ships should be placed"); 
			}
		}); 
		
		TextButton t2 = new TextButton("Place Destroyer", style);
		t2.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{ 
				for(int i = 0; i < unplacedShips.size(); i++)
				{
					AbstractShip s = unplacedShips.get(i); 
					if(s instanceof DestroyerShip)
					{
						unplacedShips.remove(s);
						ServerMessageHandler.currentAction = new ActionMessage(ActionType.PlaceShip, s.getUniqueId(),(int)currentSelectedTile.getX(),(int)currentSelectedTile.getY()); 
						ServerMessageHandler.hasChanged = true; 
						ShipActor s1 = new ShipActor((int)currentSelectedTile.getX(), (int)currentSelectedTile.getY(), s, currentPlayer);
						stage.addActor(s1); 
						return false; 
					}
				}
			
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
				for(int i = 0; i < unplacedShips.size(); i++)
				{
					AbstractShip s = unplacedShips.get(i); 
					if(s instanceof MineLayerShip)
					{
						unplacedShips.remove(s);
						ServerMessageHandler.currentAction = new ActionMessage(ActionType.PlaceShip, s.getUniqueId(),(int)currentSelectedTile.getX(),(int)currentSelectedTile.getY()); 
						ServerMessageHandler.hasChanged = true; 
						ShipActor s1 = new ShipActor((int)currentSelectedTile.getX(), (int)currentSelectedTile.getY(), s, currentPlayer);
						stage.addActor(s1); 
						return false; 
					}
				}
				
				System.out.println("No more Layers"); 
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
		
		
		TextButton t4 = new TextButton("Place Radar Ship",style);
		t4.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{ 
				for(int i = 0; i < unplacedShips.size(); i++)
				{
					AbstractShip s = unplacedShips.get(i); 
					if(s instanceof RadarBoatShip)
					{
						unplacedShips.remove(s);
						ServerMessageHandler.currentAction = new ActionMessage(ActionType.PlaceShip, s.getUniqueId(),(int)currentSelectedTile.getX(),(int)currentSelectedTile.getY()); 
						ServerMessageHandler.hasChanged = true; 
						ShipActor s1 = new ShipActor((int)currentSelectedTile.getX(), (int)currentSelectedTile.getY(), s, currentPlayer);
						stage.addActor(s1); 
						return false; 
					}
				}
				
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				description.setText(Descriptions.RADAR); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				description.setText(Descriptions.PLACE_INTRO_TEXT); 
			}
		}); 
		
		
		TextButton t5 = new TextButton("Place Torpedo Ship", style);
		t5.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{ 
				for(int i = 0; i < unplacedShips.size(); i++)
				{
					AbstractShip s = unplacedShips.get(i); 
					if(s instanceof TorpedoShip)
					{
						unplacedShips.remove(s);
						ServerMessageHandler.currentAction = new ActionMessage(ActionType.PlaceShip, s.getUniqueId(),(int)currentSelectedTile.getX(),(int)currentSelectedTile.getY()); 
						ServerMessageHandler.hasChanged = true; 
						ShipActor s1 = new ShipActor((int)currentSelectedTile.getX(), (int)currentSelectedTile.getY(), s, currentPlayer);
						stage.addActor(s1); 
						return false; 
					}
				}
				
				System.out.println("No more Torpedo"); 
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				description.setText(Descriptions.TORP); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				description.setText(Descriptions.PLACE_INTRO_TEXT); 
			}
		}); 
		
		
		TextButton t6 = new TextButton("Place Kamikaze Ship",style);
		t6.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{ 
				for(int i = 0; i < unplacedShips.size(); i++)
				{
					AbstractShip s = unplacedShips.get(i); 
					if(s instanceof KamikazeBoatShip)
					{
						unplacedShips.remove(s);
						ServerMessageHandler.currentAction = new ActionMessage(ActionType.PlaceShip, s.getUniqueId(),(int)currentSelectedTile.getX(),(int)currentSelectedTile.getY()); 
						ServerMessageHandler.hasChanged = true; 
						ShipActor s1 = new ShipActor((int)currentSelectedTile.getX(), (int)currentSelectedTile.getY(), s, currentPlayer);
						System.out.println("I have been placed: "+ s.getUniqueId()); 
						stage.addActor(s1); 
						return false; 
					}
				}
				
				System.out.println("No more Kami"); 
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				description.setText(Descriptions.KAMA); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				description.setText(Descriptions.PLACE_INTRO_TEXT); 
			}
		}); 
		
		TextButton t7 = new TextButton("Place Cruiser", style); 
		t7.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{ 
				for(int i = 0; i < unplacedShips.size(); i++)
				{
					AbstractShip s = unplacedShips.get(i); 
					if(s instanceof CruiserShip)
					{
						unplacedShips.remove(s);
						ServerMessageHandler.currentAction = new ActionMessage(ActionType.PlaceShip, s.getUniqueId(),(int)currentSelectedTile.getX(),(int)currentSelectedTile.getY()); 
						ServerMessageHandler.hasChanged = true; 
						ShipActor s1 = new ShipActor((int)currentSelectedTile.getX(), (int)currentSelectedTile.getY(), s, currentPlayer);
						stage.addActor(s1); 
						return false; 
					}
				}
				
				System.out.println("No more Cruisers"); 
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

		TextButton t8 = new TextButton("Reset Asteroids", style); 
		t8.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{ 
				ServerMessageHandler.currentAction = new ActionMessage(ActionType.ResetAsteroids, 0, 0, 0); 
				ServerMessageHandler.hasChanged = true; 
				
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				resetAsteroids(); 

				
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				description.setText("Reset the Asteroids. Your partner automatically gets there's changed, so try not to abuse this.");
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				description.setText(Descriptions.PLACE_INTRO_TEXT); 
			}
		}); 
		
		menuTable.add(header).width(width).height(height * 0.1f).row();
		menuTable.add(t1).width(width).height(height * 0.05f).row();
		menuTable.add(t7).width(width).height(height * 0.05f).row(); 
		menuTable.add(t2).width(width).height(height * 0.05f).row();
		menuTable.add(t3).width(width).height(height * 0.05f).row();
		menuTable.add(t4).width(width).height(height * 0.05f).row();
		menuTable.add(t5).width(width).height(height * 0.05f).row();
		menuTable.add(t6).width(width).height(height * 0.05f).row();
		menuTable.add(t8).width(width).height(height * 0.05f).row(); 
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
			else
			{
				int x = (int) currentSelectedTile.getX(), y = (int) currentSelectedTile.getY(); 
				if(((x == 29 && y == 10) || (x == 29) && (y == 20) || (x == 28) && (11 <= y && y<= 19)))
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

	
	public void setDefaultShipLocations()
	{		
		if(currentPlayer == PlayerNumber.PlayerOne)
		{
			int x = 1, y = 10; 
			
			for(AbstractShip modelShip : unplacedShips)
			{
				ShipActor actorShip = new ShipActor(x, y, modelShip, currentPlayer);
				
				ServerMessageHandler.currentAction = new ActionMessage(ActionType.PlaceShip, modelShip.getUniqueId(), x, y); 
				ServerMessageHandler.hasChanged = true; 
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				} 
				stage.addActor(actorShip); 
				y++; 
			}
			
			unplacedShips = new LinkedList<AbstractShip>(); 
		}
		else
		{
			int x = 28, y = 10; 
			for(AbstractShip modelShip : unplacedShips)
			{
				ShipActor actorShip = new ShipActor(x, y, modelShip, currentPlayer); 
				
				ServerMessageHandler.currentAction = new ActionMessage(ActionType.PlaceShip, modelShip.getUniqueId(), x, y); 
				ServerMessageHandler.hasChanged = true; 
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				} 
				stage.addActor(actorShip); 
				y++; 
			}
			unplacedShips = new LinkedList<AbstractShip>(); 
		}
	}
}
