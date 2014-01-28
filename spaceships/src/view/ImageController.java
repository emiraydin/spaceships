package view;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
/**
 * ImageController implements the flyweight design pattern so that we aren't re-loading the same
 * images all the time.
 * 
 * @author Andrew
 *
 */
public class ImageController {

	static HashMap<String, Texture> images = new HashMap<String, Texture>();
	
	public static Texture getImage(String fileName) {
		// First, check if the image is already loaded.
		if ( images.containsKey(fileName) ) {
			return images.get(fileName);
		}
		
		// Image not loaded, so lets load it.
		//Texture newImage = new Texture(Gdx.files.internal("art/" + fileName ));
        Texture newImage = new Texture(Gdx.files.internal(fileName));

		images.put(fileName, newImage);
		return newImage;
		
	}
	
	/**
	 * Number of images currently loaded.
	 * 
	 * @return
	 */
	public static int countImages() {
		return images.size();
	}
	
}
