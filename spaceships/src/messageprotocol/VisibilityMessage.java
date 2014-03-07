package messageprotocol;

/**
 * An array of visibilities.
 *
 */
public class VisibilityMessage extends AbstractMessage
{
	boolean[][] radarVisibleTiles;
	boolean[][] sonarVisibleTiles;
	
	/**
	 * Construct the VisibilityMessage by cloning the input array.
	 * 
	 * @param radarVisibleTiles boolean array that will be cloned
	 */
	public VisibilityMessage(boolean[][] radarVisibleTiles, boolean[][] sonarVisibleTiles) {
		this.radarVisibleTiles = radarVisibleTiles.clone();
		this.sonarVisibleTiles = sonarVisibleTiles.clone();
	}
	
	/**
	 * Get the visibility of a specific tile.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean getVisibility(int x, int y) {
		return this.radarVisibleTiles[x][y];
	}
	
	/**
	 * Get the entire array of visibilities.
	 * 
	 * @return a clone of the entire array
	 */
	public boolean[][] getVisibleTiles() {
		return this.radarVisibleTiles.clone();		
	}

	/**
	 * Update the player's visible tiles.
	 */
	public void execute()
	{
		// Update the player's visible tiles.
		
	}
}
