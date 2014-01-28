package gameLogic.actionState;


import com.badlogic.gdx.math.Vector2;

public class MovementActionState extends AbstractActionState {

    private Vector2 destination = null;

    /**
     * Specify the actor.
     *
     * @param pActor The actor whose state this is.
     */
    public MovementActionState(Object pActor, int destX, int destY) {
        super(pActor);

        this.destination.x = destX;
        this.destination.y = destY;
    }

    @Override
    void updateAction() {

    }

    @Override
    boolean successCheck() {
        return false;
    }

    @Override
    boolean interruptCheck() {
        return false;
    }

    @Override
    void successAction() {

    }

    @Override
    void interruptAction() {

    }
}
