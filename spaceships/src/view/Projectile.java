package view;

//import renderer.ObjectController;

/**
 * Created by Andrew on 22/12/13.
 */
public class Projectile extends Sprite {


    public Projectile(String fileName, int pPosX, int pPosY) {

        // Sprite constructor
        super(fileName, pPosX, pPosY);

    }


    /**
     * What happens when the projectile blows up!
     */
    public void destroy() {

    }

}
