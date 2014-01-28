package gameLogic.graphics;

import com.badlogic.gdx.graphics.Texture;

import gameLogic.graphics.ImageController;
//import renderer.ObjectController;

public class Sprite extends Graphic {

	protected Texture image = null;
	
	public Sprite(String fileName, int pPosX, int pPosY) {
		this.image = ImageController.getImage(fileName);
		
		this.setWidth(image.getWidth());
		this.setHeight(image.getHeight());

        this.setPosX(pPosX);
        this.setPosY(pPosY);

//        ObjectController.addSprite(this);
	}
	
	public Texture getImage() {
		return image;
	}

}
