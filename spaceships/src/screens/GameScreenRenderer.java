package screens;

/**
 * Handles the rendering logic of the GameScreen 
 * @author Vikram
 *
 */
public class GameScreenRenderer
{
	/*
	 * Instance Variables
	 */
	private GameScreenController controller; 
	private GameScreenUiController uiController; 

	/**
	 * Constructor
	 * @param c : Requires a GameScreenController to handle logical changes in the game. 
	 */
	public GameScreenRenderer(GameScreenController c, GameScreenUiController u)
	{
		this.controller = c; 
		this.uiController = u; 
	}
	
	/**
	 * The render method. 
	 * Uses the GameScreenController to determine the placement of ships and various other actions 
	 */
	public void render()
	{
		controller.STAGE.draw(); 
		uiController.uiStage.draw();
		
	}
	
}
