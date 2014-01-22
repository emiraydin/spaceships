package gameLogic.actionState;

/**
 * An abstract class to define an action state.  Ex: Moving ship, moving torpedo, etc.
 */
public abstract class abstractActionState {
    private Object actor = null;

    /**
     * Specify the actor.
     * @param pActor The actor whose state this is.
     */
    public abstractActionState(Object pActor) {
        this.actor = pActor;
    }

    /**
     * Perform the update loop.
     */
    public void update() {
        if (successCheck()) {
            successAction();
        } else if (interruptCheck()) {
            interruptAction();
        } else {
            updateAction();
        }
    }

    /**
     * The action to perform if neither success nor interrupt.
     */
    abstract void updateAction();

    /**
     * Test whether or not the State is "complete"
     * @return
     */
    abstract boolean successCheck();

    /**
     * Test whether or not the state needs to be interrupted.
     * @return
     */
    abstract boolean interruptCheck();

    /**
     * The action to perform when the state is complete.
     */
    abstract void successAction();

    /**
     * The action to perform if the state has been interrupted.
     */
    abstract void interruptAction();
}
