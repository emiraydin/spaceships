package screens;

import gameLogic.Descriptions;
import state.ships.AbstractShip;
import state.ships.CruiserShip;
import state.ships.DestroyerShip;
import state.ships.MineLayerShip;
import state.ships.RadarBoatShip;
import state.ships.TorpedoShip;
import state.weapons.AbstractWeapon;
import actors.ActorState;
import actors.ShipActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Handles everything on the UI side of things. 
 * @author Vikram
 *
 */
public class GameScreenUiController 
{
	private GameScreenController controller; 
	public  Stage uiStage; 
	private BitmapFont whiteFont, whiteFontSmall; 
	private Table rootTable; 
	
	// Root Table Variables
	private int height = Gdx.graphics.getHeight() - 90, width = Gdx.graphics.getWidth() / 2 - 90; 
	private int yCord = 55, xCord = Gdx.graphics.getWidth() - (width + 20);
	
	// Ship Table Variables.
	Label currentShip, description, speed, health, arsenal; 
	
	// Other Table Variables
	
	public GameScreenUiController(GameScreenController controller)
	{
		
		// Set the controller for the game. 
		this.controller = controller; 
		
		// Setup the font files. 
		whiteFont = new BitmapFont(Gdx.files.internal("fonts/whiteFont.fnt")); 
		whiteFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		whiteFontSmall = new BitmapFont(Gdx.files.internal("fonts/whiteFontSmall.fnt"));
		whiteFontSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		// Create and setup the stage. 
		uiStage = new Stage(); 
		
		// Create and setup the root Table. 
		rootTable = new Table(); 
		rootTable.setHeight(height);
		rootTable.setWidth(width);
		rootTable.setY(yCord);
		rootTable.setX(xCord);
		generateBasicBackgroundFor(rootTable);
		
		// Setup the Heading Layer. 
		Table headingTable = new Table(); 
		headingTable.setHeight(rootTable.getHeight() / 3);
		LabelStyle headingStyle = new LabelStyle(whiteFont, Color.WHITE); 
		LabelStyle typingStyle = new LabelStyle(whiteFontSmall, Color.WHITE); 
		Label title = new Label("Asteria", headingStyle);
		Label underTitle = new Label("Battle for the Frontier", typingStyle); 
		headingTable.add(title).height(50f);
		headingTable.row(); 
		headingTable.add(underTitle);
		
		// Setup the currentShip layer. 
		Table currentShipTable = new Table(); 
		currentShipTable.setWidth(rootTable.getWidth() - 20);
		currentShipTable.setHeight(rootTable.getHeight() / 3);
		generateComponentBackgroundFor(currentShipTable); 
		currentShip = new Label("", typingStyle); 
		description = new Label("", typingStyle); 
		speed = new Label("", typingStyle); 
		health = new Label("", typingStyle); 
		arsenal = new Label("", typingStyle); 
		description.setWrap(true);
		
		// Add all the ship layers. 
		currentShipTable.left().top();
		currentShipTable.add(currentShip).pad(10f).top().left();
		currentShipTable.row(); 
		currentShipTable.add(description).pad(10f).width(rootTable.getWidth() - 40).center();
		currentShipTable.row(); 
		currentShipTable.add(speed).left().padLeft(10f);
		currentShipTable.row(); 
		currentShipTable.add(health).left().padLeft(10f);
		currentShipTable.row(); 
		currentShipTable.add(arsenal).left().padLeft(10f);

		
		// Setup the currentTile Layer
		Table currentTileTable = new Table(); 
		currentTileTable.setWidth(rootTable.getWidth() - 20);
		currentTileTable.setHeight(rootTable.getHeight() / 3);
		generateComponentBackgroundFor(currentTileTable); 

		
		// Add all the sections to the root table.
		rootTable.top(); 
		rootTable.add(headingTable).padTop(10f);
		rootTable.row(); 
		rootTable.add(currentShipTable).padTop(20f);
		rootTable.row(); 
		rootTable.add(currentTileTable).padTop(20f);
		uiStage.addActor(rootTable);
	
	}

	
	/**
	 * Generates the Backgroudn for components. 
	 */
	private void generateComponentBackgroundFor(Table table)
	{
		float height = table.getHeight(), width = table.getWidth(); 
		Pixmap pixmap = new Pixmap((int)width, (int)height, Format.RGBA8888);

		pixmap.setColor(0, 0, 0, 0.5f);
		pixmap.fill(); 
		//pixmap.drawRectangle(0, 0, width, height);
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, (int)width, (int)height);
		Texture newTexture = new Texture(pixmap);
		TextureRegion newTexture2 = new TextureRegion(newTexture); 
		TextureRegionDrawable drawable = new TextureRegionDrawable(newTexture2); 
		
		table.setBackground(drawable);
		
	}
	
	/**
	 * Generates the background for root. 
	 */
	private void generateBasicBackgroundFor(Table table)
	{
		float height = table.getHeight(), width = table.getWidth(); 
		Pixmap pixmap = new Pixmap((int)width, (int)height, Format.RGBA8888);

		pixmap.setColor(0, 0, 0, 0.2f);
		pixmap.fill(); 
		//pixmap.drawRectangle(0, 0, width, height);
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, (int)width, (int)height);
		Texture newTexture = new Texture(pixmap);
		TextureRegion newTexture2 = new TextureRegion(newTexture); 
		TextureRegionDrawable drawable = new TextureRegionDrawable(newTexture2); 
		
		table.setBackground(drawable);
		
	}

	/**
	 * Update the current display based on selection. 
	 * @param delta
	 */
	public void update(float delta) 
	{
		if(ActorState.currentSelection != -1)
		{
			ShipActor ship = ActorState.shipList.get(ActorState.currentSelection); 
			AbstractShip aShip = ship.ship; 
			String type = aShip.getClass().getSimpleName();
			currentShip.setText(type);
			
			speed.setText("Speed: "+ aShip.getSpeed());
			
			int healthCount = aShip.getLength(); 
			for(int i = 0; i < aShip.getLength(); i++)
			{
				if(aShip.getSectionHealth()[i] < 2)
				{
					healthCount --; 
				}
			}
			
			health.setText("Health: " + healthCount);
			String arsenalText = "Arsenal : ";
			
			for(int i = 0; i < aShip.getWeapons().size(); i++)
			{
				if(i != 0)
				{
					arsenalText += (" , " + aShip.getWeapons().get(i).getClass().getSimpleName());
				}
				else
				{
					arsenalText += (aShip.getWeapons().get(i).getClass().getSimpleName());
				}
				
			}
			
			arsenal.setText(arsenalText);
			
			
			
			if(aShip instanceof CruiserShip)
			{
				currentShip.setText(Descriptions.CRUISER_TITLE);
				description.setText(Descriptions.CRUISER);
			}
			else if(aShip instanceof DestroyerShip)
			{
				currentShip.setText(Descriptions.DESTROYER_TITLE);
				description.setText(Descriptions.DESTROYER);
			}
			else if(aShip instanceof MineLayerShip)
			{
				currentShip.setText(Descriptions.LAYAR_TITLE);
				description.setText("Select a ship fool!");
			}
			else if(aShip instanceof RadarBoatShip)
			{
				currentShip.setText(Descriptions.RADAR_TITLE);
			}
			else if(aShip instanceof TorpedoShip)
			{
				currentShip.setText(Descriptions.TORPEDO_TITLE);
			}
			else
			{
				currentShip.setText("Please select a ship"); 
			}
		}
		
		uiStage.act(delta);
	}

}
