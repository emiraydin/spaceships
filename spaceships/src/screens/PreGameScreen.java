package screens;

import java.sql.SQLException;

import state.GameState;
import util.Database;
import util.TCPClient;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.esotericsoftware.tablelayout.Cell;

/**
 * The Screen that displays all the players who are currently logged on. 
 * Allows the player to select who they want to have a match against. 
 * Yolo Hashtag Yolo Hashtag Swag. (Because it's a PreGame Screen. Get it?) 
 * @author vikramsundaram
 *
 */
public class PreGameScreen implements Screen
{
	// Screen Attributes 
	private Skin skin; 
	private Stage stage; 
	private Game game; 
	private Table table; 
	private Label contentLabel; 
	private LabelStyle labelStyle;
	private TextButton challenge; 
	private TextButtonStyle buttonStyle; 
	private ScrollPane pane; 
	private ScrollPaneStyle paneStyle; 
	private Texture backgroundImage;  
	private float width = Gdx.graphics.getWidth() * 0.8f, height = Gdx.graphics.getHeight() * 0.6f; 
	

	
	public PreGameScreen(Game game2)
	{
		this.game = game2; 
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); 
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
		stage.act(delta); 
		stage.draw(); 
		Table.drawDebug(stage); 
		
		if(Gdx.input.isKeyPressed(Keys.R))
		{
			resetPlayers(); 
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
		// Create and Setup the Stage. 
		stage = new Stage(); 
		Gdx.input.setInputProcessor(stage); 
		
		// Setup the Basic Skin. 
		setUpSkin(); 
		
		// Setup the Background. 
		setUpBackgroundImage();
		
		// Setup the TextButton Style
		setUpButtonStyle(); 
		
		// Create the Actual Table. 
		setupTable(); 
	}
	
	
	private void setupTable()
	{
		// Create the Table. 
		table = new Table(); 
		table.top(); // Make everything Align to the Top. 
		
		// Create and generate the background. 
		Pixmap pixmap = new Pixmap((int) width, (int) height, Format.RGBA8888); 
		pixmap.setColor(0, 0, 0, 0.2f);
		pixmap.fill(); 
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, (int)width, (int)height);
		Texture newTexture = new Texture(pixmap);
		TextureRegion newTexture2 = new TextureRegion(newTexture); 
		TextureRegionDrawable drawable = new TextureRegionDrawable(newTexture2); 
		
		// Create and generate the Scroll Thing. 
		paneStyle = new ScrollPaneStyle(); 
		paneStyle.vScroll = skin.newDrawable("white", Color.BLACK); 
		paneStyle.vScrollKnob = skin.newDrawable("white", new Color(0,1,1,1)); 
		paneStyle.background = drawable; 
		pane = new ScrollPane(table,paneStyle); 
		pane.setSize(width, height); 
		pane.setPosition(width * 0.15f, height * 0.2f); 
		
		// Loads the currently Logged In Players. 
		resetPlayers(); 
		
//		for(int i = 0; i < 5; i++)
//		{
//			table.add(new PlayerPanel("Vikram Sundaram", i, i - 5)); 
//			table.row(); 
//		}
//		
		// Add the Table to the Stage. 
		stage.addActor(pane); 
	}

	private void resetPlayers()
	{
		// Spent 1h trying to do this manually. 
		// Didn't realize it was already a thing lol. 
		table.clearChildren(); 

		// Pretty much just rebuild the entire GameBoard. 
		table.add(new PlayerPanel()); 
		table.row(); 
		
		try
		{
			Database d = new Database();
			
			for(String[] s : d.showStats(1))
			{ 
				PlayerPanel p = new PlayerPanel(s[1], s[3], s[4]); 
				table.add(p).row(); 
			}
		}
		catch (ClassNotFoundException | SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	private void setUpButtonStyle()
	{
		buttonStyle = new TextButtonStyle();  
		buttonStyle.up = skin.newDrawable("white",new Color(1,1,1,0.5f));
		buttonStyle.down = skin.newDrawable("white",new Color(1,1,1,1f));
		buttonStyle.font = skin.getFont("default"); 
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
		BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/whiteFontSmall.fnt")); 
		font.scale(0.1f); 
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		skin.add("default", font);
		
	}

	private void setUpBackgroundImage()
	{
		// Create and Setup the Background Image. 
		backgroundImage = new Texture("images/spaceBackground.png"); 
		Image bImage = new Image(backgroundImage); 
		bImage.setWidth(Gdx.graphics.getWidth()); 
		bImage.setHeight(Gdx.graphics.getHeight()); 
		stage.addActor(bImage); 
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

	
	/**
	 * Player Panel is literally a table.
	 * It holds the submitted information and displays it in a row. 
	 * @author vikramsundaram
	 *
	 */
	public class PlayerPanel extends Table
	{
		private final String name; 
		private String wins, losses; 
		
		public PlayerPanel()
		{
			setWidth(width); 
			name = ""; 
			
			setBackground(skin.newDrawable("white", new Color(1,0,0,0.2f))); 
			
			TextButtonStyle pButton = new TextButtonStyle();  
			pButton.font = skin.getFont("default");
			pButton.over = skin.newDrawable("white", Color.RED); 			
			
			LabelStyle pLabel = new LabelStyle(); 
			pLabel.font = skin.getFont("default"); 
			pLabel.fontColor = Color.RED; 
		
			String pad = "   "; 
			
			Label b = new Label(pad+"Player Name", pLabel);
			Label c = new Label("Wins", pLabel);
			Label d = new Label("Losses", pLabel);
			Label e = new Label("", pLabel); 
			
			add(b).width(this.getWidth() / 4); 
			add(c).width(this.getWidth() / 4); 
			add(d).width(this.getWidth() / 4);
			add(e).width(this.getWidth() / 4); 
			padBottom(10); 
			
		}
		
		public PlayerPanel(final String name, String wins, String losses)
		{
			this.name = name; 
			this.wins = wins; 
			this.losses = losses; 
			
			String pad = "   "; 
			setWidth(width); 
			
			TextButtonStyle pButton = new TextButtonStyle();  
			pButton.font = skin.getFont("default");
			pButton.over = skin.newDrawable("white", Color.RED); 
			
			LabelStyle pLabel = new LabelStyle(); 
			pLabel.font = skin.getFont("default"); 
			pLabel.fontColor = Color.WHITE; 
		
			Label b = new Label(pad+name, pLabel);
			Label c = new Label(wins, pLabel);
			Label d = new Label(losses, pLabel);
			TextButton e = new TextButton("Challenge", pButton); 
			
			e.addListener(new ClickListener()
			{
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
//					if (!TCPClient.canStart) {
//					TCPClient.inputString = name;
//					TCPClient.inputEntered = true;
//					}
					game.setScreen(new GameSetupScreen(game));
					return false; 
				}
			}); 
			
			add(b).width(this.getWidth() / 4); 
			add(c).width(this.getWidth() / 4); 
			add(d).width(this.getWidth() / 4);
			add(e).width(this.getWidth() / 4); 
			padBottom(10f); 
			
			
			
			// Define the background
			Pixmap p = new Pixmap(32,32,Format.RGBA8888); 
			p.setColor(new Color(0,0,0,0.4f)); 
			p.fill(); 
			p.setColor(new Color(0,1,1,1)); 
			p.drawLine(0, 0, 32, 0); 
			Texture newTexture = new Texture(p);
			TextureRegion newTexture2 = new TextureRegion(newTexture); 
			TextureRegionDrawable drawable = new TextureRegionDrawable(newTexture2);
			setBackground(drawable); 
			
		}
	}
}
