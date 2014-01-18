/**
 * 
 */
package gameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author vikramsundaram
 * Asset Manager Class, eases loading and tracking of Assets. 
 * Singleton pattern, for universal access. 
 */
public class Assets implements Disposable, AssetErrorListener
{
	
	// Various nested AssetClasses - used for easy access and caching. 
	public AssetShip ships; 

	// Used for Logging
	private static String TAG = Assets.class.getSimpleName(); 
	
	// Singleton accessor. 
	public static Assets INSTANCE = new Assets(); 
	
	// Asset Manager Class. (Libgdx standard for managing assets). 
	private AssetManager assetManager; 
	
	/**
	 * Singleton Constructor. 
	 * Is private, so only one can be instanciated at one time. 
	 */
	private Assets()
	{
		
	}

	/**
	 * Load all the Assets pointed to by Constants.TEXTURE_ATLAS_OBJECTS
	 * TEXTURE_ATLAS_OBJECTS contains the location to just ONE set of assets. 
	 * I.e. It expects that all assets, including all classes can be loaded from that package. 
	 * TODO: Determine if this is enough, or we must allow for multiple locations. 
	 * @param assetManager: The Asset Manager to store the assets in. 
	 */
	public void init(AssetManager assetManager)
	{
		
		this.assetManager = assetManager; 
		
		// Set asset manager error handler
		assetManager.setErrorListener(this); 
		
		// Load Texture Atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		
		// Load all atlas, blocks use of Assets untill completion. 
		assetManager.finishLoading(); 
		
		// Log the output for testing
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size); 
		for(String name : assetManager.getAssetNames())
		{
			Gdx.app.debug(TAG, "Loaded: " + name); 
		}
		
		// Load the Assets. 
		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS); 
		for(Texture t : atlas.getTextures())
		{
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear); 
		}
		
		// Cache the ship assets. 
		ships = new AssetShip(atlas); 
		
	}
	
	/**
	 * Executes if some error exists in loading the Assets. 
	 */
	@Override
	public void error(AssetDescriptor asset, Throwable throwable)
	{
		Gdx.app.log(TAG, "Error loading asset: " + asset + " Error "+ throwable);

	}

	/**
	 * Last call before disposal of assetManager. 
	 */
	@Override
	public void dispose()
	{
		assetManager.dispose(); 

	}

	
	// Nested Classes, allows for easy access to different types of Assets. 
	
	/**
	 * Ships nested class. 
	 * Allows easy access to the various ship assets. 
	 * Different classes of ships can be loaded by passing in different TextureAtlas'. 
	 * Also allows for caching to speed up loadtimes, once initial load is completed. 
	 * @author vikramsundaram
	 *
	 */
	public class AssetShip
	{
		public final AtlasRegion CRUISER; 
		public final AtlasRegion DESTROYER; 
		public final AtlasRegion MINELAYER; 
		public final AtlasRegion RADARSHIP; 
		public final AtlasRegion TORPEDOESHIP; 
		
		public AssetShip(TextureAtlas atlas)
		{
			CRUISER = atlas.findRegion("cruiser"); 
			DESTROYER = atlas.findRegion("destroyer"); 
			MINELAYER = atlas.findRegion("minelayer"); 
			RADARSHIP = atlas.findRegion("radarship"); 
			TORPEDOESHIP = atlas.findRegion("torpedoeship"); 
		}
	}
}
