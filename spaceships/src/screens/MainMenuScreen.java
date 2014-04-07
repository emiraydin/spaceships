package screens;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import util.Database;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * The Main Menu of the Game. 
 * @author vikramsundaram
 *
 */
public class MainMenuScreen implements Screen
{
	// Screen Attributes
	private Skin skin; 
	private Stage stage; 
	private Game game; 
	private Table basicTable, loginTable, signUpTable; 
	private TextButton login, signup, quit; 
	private TextButtonStyle style; 
	private Texture backgroundImage; 
	private TextFieldStyle fStyle; 
	private TextField loginUser, loginPass, signupUser, signupPass; 

	
	/**
	 * Constructor
	 */
	public MainMenuScreen(Game g)
	{
		this.game = g; 
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1); 
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); 
		stage.act(delta); 
		stage.draw(); 
		Table.drawDebug(stage); 
		
	}

	
	private void setUpHeader()
	{
//		LabelStyle lStyle = new LabelStyle(); 
//		lStyle.font = skin.getFont("default"); 
//		header = new Label("Aseteria", lStyle);
//		header.setHeight(Gdx.graphics.getHeight() * 0.4f); 
//		header.setWidth(Gdx.graphics.getWidth() * 0.6f); 
//		header.setPosition(Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 10); 
//		
//		stage.addActor(header); 
		
		
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
	
	private void setUpTable(Table table)
	{
		// Set the Position of the Table to be the center of the screen. 
		table.setWidth(Gdx.graphics.getWidth() * 0.2f); 
		table.setHeight(Gdx.graphics.getHeight() * 0.5f ); 
		//table.setPosition(Gdx.graphics.getWidth()/2 - table.getWidth()/2, Gdx.graphics.getHeight()/2 - table.getHeight()/2);
		
		// Set the Background for the Table
		//setUpTableBackground(table); 
		
	}
	
	private void setUpBackground()
	{
		// Create and Setup the Background Image. 
		backgroundImage = new Texture("images/spaceBackground.png"); 
		Image bImage = new Image(backgroundImage); 
		bImage.setWidth(Gdx.graphics.getWidth()); 
		bImage.setHeight(Gdx.graphics.getHeight()); 
		stage.addActor(bImage); 
	}
	
	private void setUpTextButtonStyle()
	{
		// Create the Style Object. 
		style = new TextButtonStyle(); 
		
		// Create the basic drawable. 
		Pixmap pixmap = new Pixmap((int) 32, (int) 32, Format.RGBA8888); 
		pixmap.setColor(0, 0, 0, 0.2f);
		pixmap.fill(); 
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, 32, 32);
		Texture newTexture = new Texture(pixmap);
		TextureRegion newTexture2 = new TextureRegion(newTexture); 
		TextureRegionDrawable drawable = new TextureRegionDrawable(newTexture2);
		
		style.up = skin.newDrawable(drawable);
		
		// Create the over drawable. 
		pixmap = new Pixmap((int) 32, (int) 32, Format.RGBA8888); 
		pixmap.setColor(1,1,1,1.5f); 
		pixmap.fill(); 
		pixmap.setColor(0,1,1,1); 
		pixmap.drawRectangle(0, 0, 32, 32);
		newTexture = new Texture(pixmap);
		newTexture2 = new TextureRegion(newTexture); 
		drawable = new TextureRegionDrawable(newTexture2); 
		
		style.over = drawable; 
		
		style.font = skin.getFont("default"); 
	}
	
	private void setUpInitialTable()
	{
		basicTable = new Table(); 
		setUpTable(basicTable); 
		
		login = new TextButton("Login", style);
		signup = new TextButton ("Sign Up", style);
		quit = new TextButton("Exit", style); 
		
		login.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				// Remove the Root Table 
				basicTable.remove(); 
				
				// Create and Setup the Login Table 
				setUpLoginTable(); 
				
				return false; 
			}
		}); 
		
		signup.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				// Remove the Root Table 
				basicTable.remove(); 
				
				// Create and Setup the Login Table 
				setUpSignUpTable(); 
				return false; 
			}
		}); 
		
		quit.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				System.exit(0); 
				return false; 
			}
		}); 
		
		
		basicTable.add(login).pad(10f).height(80f).width(160f).row(); 
		basicTable.add(signup).pad(10f).height(80f).width(160f).row();
		basicTable.add(quit).pad(10f).height(80f).width(160f);
		
		

		
		stage.addActor(basicTable); 
	
	}
	
	private void setUpLoginTable()
	{
		loginTable = new Table(); 
		setUpTable(loginTable);	
	
		// User Name
		loginUser = new TextField("", fStyle);
		loginUser.setMessageText("UserName");
		
		// Password 
		loginPass = new TextField("", fStyle); 
		loginPass.setMessageText("Password");
		loginPass.setPasswordCharacter('*');
		loginPass.setPasswordMode(true); 
		
		// Submit Button. 
		TextButton submit = new TextButton("Submit", style); 
		submit.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				try
				{
					Database database = new Database();
					boolean loginSuccess = database.loginUser(loginUser.getText(), loginPass.getText()); 
					if(!loginSuccess)
					{
						return false; 
					}
					else
					{
						String data = loginUser.getText();
						System.out.println(data); 
						InputStream testInput = new ByteArrayInputStream( data.getBytes("UTF-8") );
						InputStream old = System.in;
						System.setIn( testInput );
					}
				}
				catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException | UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} 
				
				
				// If the check works and we are logged in then go to the PreGameScreen. 
				game.setScreen(new PreGameScreen(game)); 
				return false; 
			}
		}); 
		
		
		loginTable.add(loginUser).pad(10f).height(100f).width(200f).center().row();
		loginTable.add(loginPass).pad(10f).height(100f).width(200f).center().row();
		loginTable.add(submit).pad(10f).height(100f).width(200f).center().row();
		stage.addActor(loginTable); 
	}
	
	private void setUpSignUpTable()
	{
		signUpTable = new Table(); 
		setUpTable(signUpTable);	
		
		// User Name
		signupUser = new TextField("", fStyle);
		signupUser.setMessageText("Enter a Username");
		
		// Password
		signupPass = new TextField("", fStyle); 
		signupPass.setMessageText("Enter a Password");
		signupPass.setPasswordCharacter('*');
		signupPass.setPasswordMode(true); 
		
		// Submit Button. 
		TextButton submit = new TextButton("Submit", style);
		submit.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				try
				{
					Database database = new Database();
					boolean createSuccess = database.addUser(signupUser.getText(), signupPass.getText()); 
					if(!createSuccess)
					{
						
						return false; 
					}
				}
				catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException | UnsupportedEncodingException e)
				{
					e.printStackTrace();
				} 
				
				// If the check works and we are logged in then go to the PreGameScreen. 
				game.setScreen(new MainMenuScreen(game)); 
				return false; 
			}
		}); 
		

		
		signUpTable.add(signupUser).pad(10f).height(100f).width(200f).center().row();
		signUpTable.add(signupPass).pad(10f).height(100f).width(200f).center().row();
		signUpTable.add(submit).pad(10f).height(100f).width(200f).center().row();
		
		stage.addActor(signUpTable); 
	}

	private void setUpTableBackground(Table t)
	{
		float width = t.getWidth(), height = t.getHeight(); 
		
		// Create and generate the background. 
		Pixmap pixmap = new Pixmap((int) width, (int) height, Format.RGBA8888); 
		pixmap.setColor(0, 0, 0, 0.2f);
		pixmap.fill(); 
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, (int)width, (int)height);
		Texture newTexture = new Texture(pixmap);
		TextureRegion newTexture2 = new TextureRegion(newTexture); 
		TextureRegionDrawable drawable = new TextureRegionDrawable(newTexture2); 
	
		// Set t's Background to be the drawable. 
		t.setBackground(drawable);
		pixmap.dispose(); 
}

	private void setUpTextFieldStyle()
	{
		fStyle = new TextFieldStyle(); 	
		
		// Details regarding the Font. 
		fStyle.font = skin.getFont("default"); 
		fStyle.fontColor = Color.BLACK; 
		
		// The Background for the items. 
		fStyle.focusedBackground = skin.newDrawable("white", new Color(1,1,1,0.5f)); 
		fStyle.background = skin.newDrawable("white", new Color(1,1,1,0.7f)); 
		fStyle.background.setLeftWidth(fStyle.background.getLeftWidth() + 10);
		
		fStyle.cursor = skin.newDrawable("white", Color.BLACK); 
	}
	
	@Override
	public void show()
	{
		stage = new Stage(); 
		Gdx.input.setInputProcessor(stage);
		stage.getRoot().setColor(1, 1, 1, 0);
		
		// Setup the Basic Skin Stuff. 
		setUpSkin(); 
		
		// Create and Setup the Background Image. 
		setUpBackground(); 

		// Create and setup the basic styles
		setUpTextButtonStyle(); 
		setUpTextFieldStyle(); 
		
		// Setup and put the Initial Table on Screen. 
		setUpInitialTable(); 
		
		// Setup the Header
		setUpHeader(); 
		
		stage.getRoot().addAction(Actions.fadeIn(4));

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
		stage.dispose(); 
		skin.dispose(); 
		
	} 
	
	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
		
	}
	
	
}
