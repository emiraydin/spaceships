package messageprotocol;

/**
 * An array of visibilities.
 *
 */
public class VisibilityMessage extends AbstractMessage
{
	boolean[][] visibleTiles;
	
	/**
	 * Construct the VisibilityMessage by cloning the input array.
	 * 
	 * @param visibleTiles boolean array that will be cloned
	 */
	public VisibilityMessage(boolean[][] visibleTiles) {
		this.visibleTiles = visibleTiles.clone();
	}
	
	/**
	 * Get the visibility of a specific tile.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean getVisibility(int x, int y) {
		return this.visibleTiles[x][y];
	}
	
	/**
	 * Get the entire array of visibilities.
	 * 
	 * @return a clone of the entire array
	 */
	public boolean[][] getVisibleTiles() {
		return this.visibleTiles.clone();		
	}

	/**
	 * Update the player's visible tiles.
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub
		
	}
}
