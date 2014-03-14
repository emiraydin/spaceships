package screens;

import gameLogic.ActionValidator;
import gameLogic.Descriptions;
import messageprotocol.ActionMessage;
import messageprotocol.ServerMessageHandler;
import state.GameState;
import state.ships.AbstractShip;
import state.ships.CruiserShip;
import state.ships.DestroyerShip;
import state.ships.MineLayerShip;
import state.ships.RadarBoatShip;
import state.ships.TorpedoShip;
import actors.ActorState;
import actors.ShipActor;
import actors.TileActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import common.GameConstants.ActionType;
import common.GameConstants.OrientationType;

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
	TextButton moveShip, fireCannon, fireTorpedo, turnLeft, turnRight,turn180 ; 
	
	// Other Table Variables
	Label currentPlayerAction, serverMessage, chatBox; 
	
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
		Table buttonTable = new Table(); 
		buttonTable.setWidth(rootTable.getWidth() - 20); 
		buttonTable.setHeight(40f); 
		TextButtonStyle tbs = new TextButtonStyle(); 
		tbs.font = whiteFontSmall; 
		tbs.up = generateButtonBackground(); 
		tbs.down = generateDownButtonBackground(); 
		tbs.disabled = generateDisableButtonBackground(); 
		tbs.over = generateHoverButtonBackground(); 

		
		moveShip = new TextButton("Move", tbs); 
		setUpClickListnersForMove(moveShip); 
		moveShip.setVisible(false); 
		fireCannon = new TextButton("Fire", tbs); 
		setUpClickListnersForFire(fireCannon); 
		fireCannon.setVisible(false); 
		fireTorpedo = new TextButton("Torp", tbs);
		setUpClickListnersForTorp(fireTorpedo); 
		fireTorpedo.setVisible(false);  
		turnLeft = new TextButton("Turn\nLeft", tbs);
		setUpClickListnersForTurnLeft(turnLeft); 
		turnLeft.setVisible(false); 
		turnRight = new TextButton("Turn\nRight", tbs); 
		setUpClickListnersForTurnRight(turnRight); 
		turnRight.setVisible(false); 
		turn180 = new TextButton("180\nTurn", tbs); 
		setUpClickListnersForTurn180(turn180); 
		turn180.setVisible(false); 
		
		buttonTable.add(moveShip).pad(10f); 
		buttonTable.add(fireCannon).pad(10f); 
		buttonTable.add(fireTorpedo).pad(10f);
		buttonTable.add(turnLeft).pad(10f);
		buttonTable.add(turnRight).pad(10f);
		buttonTable.add(turn180).pad(10f); 
		
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
		currentShipTable.row(); 
		currentShipTable.add(buttonTable);  

		
		// Setup the chat table. 
		
		Table chatTable = new Table(); 
		chatTable.setWidth(rootTable.getWidth() - 20);
		chatTable.setHeight(rootTable.getHeight() / 3);
		generateComponentBackgroundFor(chatTable); 
		
		LabelStyle notificationLabelStyle = new LabelStyle(); 
		notificationLabelStyle.font = whiteFontSmall; 
		notificationLabelStyle.background = generateNotificationBackground(); 
		currentPlayerAction = new Label("", notificationLabelStyle); 
		currentPlayerAction.setAlignment(Align.center); 
		chatTable.add(currentPlayerAction).width(rootTable.getWidth() - 20).height(chatTable.getHeight() / 3).center(); 
		serverMessage = new Label("", notificationLabelStyle); 
		serverMessage.setAlignment(Align.center); 
		chatBox = new Label("", notificationLabelStyle); 
		chatBox.setAlignment(Align.center); 
		chatTable.row(); 
		chatTable.add(serverMessage).width(rootTable.getWidth() - 20).height(chatTable.getHeight() / 3).center().padTop(10f); 
		chatTable.row(); 
		chatTable.add(chatBox).width(rootTable.getWidth() - 20).height(chatTable.getHeight() / 3).center().padTop(10f); 

		ScrollPane scroller = new ScrollPane(chatTable);
		

		
		// Add all the sections to the root table.
		rootTable.top(); 
		rootTable.add(headingTable).padTop(10f);
		rootTable.row(); 
		rootTable.add(currentShipTable).padTop(20f);
		rootTable.row(); 
		rootTable.add(scroller).padTop(20f).fill().expand();
		uiStage.addActor(rootTable);

	}
	
	private void setUpClickListnersForMove(TextButton button)
	{
		button.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{				
				if(ActorState.currentSelectionShip != -1
						&& ActorState.currentTile != null
						&& ActionValidator.validateMove((int)ActorState.currentTile.getX(), (int)ActorState.currentTile.getY()))
				{
					ServerMessageHandler.currentAction = new ActionMessage(ActionType.Move, ActorState.getShipList(controller.cPlayer).get(ActorState.currentSelectionShip).ship.getUniqueId(), (int)ActorState.currentTile.getX(), (int)ActorState.currentTile.getY());
					ServerMessageHandler.hasChanged = true; 
					chatBox.setText(""); 
				}
				else
				{
					chatBox.setText("That move selection is invalid.\nPlease select a move in range"); 
				}
				return false; 
			}
		
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				currentPlayerAction.setText("Move the currently selected ship\nto the selected tile");
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				currentPlayerAction.setText(""); 
			}
		
		});
	}
	private void setUpClickListnersForFire(TextButton button)
	{
		button.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				if(ActorState.currentSelectionShip != -1
						&& ActorState.currentTile != null
						&& ActionValidator.validateShot((int)ActorState.currentTile.getX(),(int)ActorState.currentTile.getY()))
				{
					chatBox.setText(""); 
				}
				else
				{
					chatBox.setText("That shot was out of range.\nPlease select a shot in range"); 
				}
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				currentPlayerAction.setText("Fire the Ships Cannons.\nAutomatically determines heaviest cannon"); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				currentPlayerAction.setText(""); 
			}
		});
	}
	private void setUpClickListnersForTorp(TextButton button)
	{
		button.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				if(ActorState.currentSelectionShip != -1)
				{
					chatBox.setText(""); 
				}
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				currentPlayerAction.setText("Fire Torpedoes.\nWill hit the first object within 10 lightyears"); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				currentPlayerAction.setText(""); 
			}
		});
	}
	private void setUpClickListnersForTurnLeft(TextButton button)
	{
		button.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				if(ActorState.currentSelectionShip != -1)
				{
					ServerMessageHandler.currentAction = new ActionMessage(ActionType.TurnLeft, ActorState.getShipList(controller.cPlayer).get(ActorState.currentSelectionShip).ship.getUniqueId(), -1, -1); 
					ServerMessageHandler.hasChanged = true; 
					chatBox.setText(""); 
				}
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				currentPlayerAction.setText("Turn the ship 90 degrees leftwards."); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				currentPlayerAction.setText(""); 
			}
		});
	}
	private void setUpClickListnersForTurnRight(TextButton button)
	{
		button.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				if(ActorState.currentSelectionShip != -1)
				{
					ServerMessageHandler.currentAction = new ActionMessage(ActionType.TurnRight, ActorState.getShipList(controller.cPlayer).get(ActorState.currentSelectionShip).ship.getUniqueId(), -1, -1); 
					ServerMessageHandler.hasChanged = true;  
					chatBox.setText(""); 
				}
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				currentPlayerAction.setText("Turn the ship 90 degrees rightwards."); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				currentPlayerAction.setText(""); 
			}
		});
	}
	private void setUpClickListnersForTurn180(TextButton button)
	{
		button.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				if(ActorState.currentSelectionShip != -1)
				{
					//ActorState.shipList.get(ActorState.currentSelectionShip).ship.setOrientation(OrientationType.South); 
					chatBox.setText(""); 
				}
				return false; 
			}
			
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{ 
				currentPlayerAction.setText("Turn the ship right around."); 
			}
			
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				currentPlayerAction.setText(""); 
			}
		});
	}
	
	
	/**
	 * Generates a background for the button. 
	 * @return 
	 */
	private TextureRegionDrawable generateButtonBackground()
	{
		float height = 55f, width = 70f; 
		Pixmap pixmap = new Pixmap((int)width, (int)height, Format.RGBA8888);

		pixmap.setColor(0/255f, 0/255f, 250/255f, 0.15f);
		pixmap.fill(); 
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, (int)width, (int)height);
		Texture newTexture = new Texture(pixmap);
		TextureRegion newTexture2 = new TextureRegion(newTexture); 
		TextureRegionDrawable drawable = new TextureRegionDrawable(newTexture2); 
		
		return drawable; 
	}
	private TextureRegionDrawable generateDownButtonBackground()
	{
		float height = 55f, width = 70f; 
		Pixmap pixmap = new Pixmap((int)width, (int)height, Format.RGBA8888);

		pixmap.setColor(0/255f, 0/255f, 250/255f, 0.6f);
		pixmap.fill(); 
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, (int)width, (int)height);
		Texture newTexture = new Texture(pixmap);
		TextureRegion newTexture2 = new TextureRegion(newTexture); 
		TextureRegionDrawable drawable = new TextureRegionDrawable(newTexture2); 
		
		return drawable; 
	}
	private TextureRegionDrawable generateDisableButtonBackground()
	{
		float height = 55f, width = 70f; 
		Pixmap pixmap = new Pixmap((int)width, (int)height, Format.RGBA8888);

		pixmap.setColor(128/255f, 128/255f, 128/255f, 0.5f);
		pixmap.fill(); 
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, (int)width, (int)height);
		Texture newTexture = new Texture(pixmap);
		TextureRegion newTexture2 = new TextureRegion(newTexture); 
		TextureRegionDrawable drawable = new TextureRegionDrawable(newTexture2); 
		
		return drawable; 
	}
	private TextureRegionDrawable generateHoverButtonBackground()
	{
		float height = 55f, width = 70f; 
		Pixmap pixmap = new Pixmap((int)width, (int)height, Format.RGBA8888);

		pixmap.setColor(0/255f, 0/255f, 250/255f, 0.3f);
		pixmap.fill(); 
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0, 0, (int)width, (int)height);
		Texture newTexture = new Texture(pixmap);
		TextureRegion newTexture2 = new TextureRegion(newTexture); 
		TextureRegionDrawable drawable = new TextureRegionDrawable(newTexture2); 
		
		return drawable; 
	}
	private TextureRegionDrawable generateNotificationBackground()
	{
		float height = 55f, width = 70f; 
		Pixmap pixmap = new Pixmap((int)width, (int)height, Format.RGBA8888);

		pixmap.setColor(0/255f, 0/255f, 255/255f, 0.05f);
		pixmap.fill(); 
		pixmap.setColor(0, 1, 0, 0.5f);
		pixmap.drawRectangle(0, 0, (int)width, (int)height);
		Texture newTexture = new Texture(pixmap);
		TextureRegion newTexture2 = new TextureRegion(newTexture); 
		TextureRegionDrawable drawable = new TextureRegionDrawable(newTexture2); 
		
		return drawable; 
	}
	
	/**
	 * Generates the Background for components. 
	 */
	private void generateComponentBackgroundFor(Table table)
	{
		float height = table.getHeight(), width = table.getWidth(); 
		Pixmap pixmap = new Pixmap((int)width, (int)height, Format.RGBA8888);

		pixmap.setColor(0, 0, 0, 0.3f);
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
		if(ActorState.currentSelectionShip != -1)
		{
			ShipActor ship = ActorState.getShipList((controller.cPlayer)).get(ActorState.currentSelectionShip); 
			AbstractShip aShip = ship.ship; 
			
			if(!(GameState.getResponseString() == null))
			{
				serverMessage.setText(GameState.getResponseString());
			}
			else
			{
				serverMessage.setText("Nothing to report, all is well!"); 
			}
			
			// Set the buttons 
			moveShip.setVisible(true); 
			fireCannon.setVisible(true); 
			fireTorpedo.setVisible(true);
			turnLeft.setVisible(true); 
			turnRight.setVisible(true); 
			turn180.setVisible(true); 
			if(aShip instanceof TorpedoShip || aShip instanceof DestroyerShip)
			{
				fireTorpedo.setDisabled(false);  
			}
			else
			{
				fireTorpedo.setDisabled(true); 
			}
			if(aShip instanceof RadarBoatShip || aShip instanceof TorpedoShip)
			{
				turn180.setDisabled(false); 
			}
			else
			{
				turn180.setDisabled(true); 
			}
			
			String type = aShip.getClass().getSimpleName();
			currentShip.setText(type);
			
			speed.setText("Speed: "+ aShip.getSpeed());
			
			int[] healthCount = aShip.getSectionHealth(); 
			String healthText = ""; 
			for(int i : healthCount)
			{
				healthText += (" "+ i); 
			}
			
			
			health.setText("Health: " + healthText);
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
		else
		{
			moveShip.setVisible(false); 
			fireCannon.setVisible(false); 
			fireTorpedo.setVisible(false);
		}
		
		uiStage.act(delta);
	}

}
