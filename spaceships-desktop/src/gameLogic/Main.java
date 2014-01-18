package gameLogic;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main 
{
	private static boolean rebuildAtlas = true; 
	private static boolean drawDebugOutline = true; 
	
	
	public static void main(String[] args) 
	{
		if(rebuildAtlas == true)
		{
			System.out.println("Creating Texture Atlas"); 
			Settings settings = new Settings(); 
			settings.maxWidth = 1024; 
			settings.maxHeight = 1024; 
			settings.debug = drawDebugOutline; 
			TexturePacker2.process(settings, "assets-raw/shipImages", "../spaceships-android/assets/images", "ships.pack"); 
		}
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "spaceships";
		cfg.useGL20 = false;
		cfg.width = Constants.APP_WIDTH;
		cfg.height = Constants.APP_HEIGHT;
		
		new LwjglApplication(new Spaceships(), cfg);
	}
}
