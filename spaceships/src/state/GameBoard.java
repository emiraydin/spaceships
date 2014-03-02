package state;

import java.util.ArrayList;

public class GameBoard {
	
	private final int PIXEL_WIDTH = 32;
	private final int BOARD_WIDTH = 10;
	private final int BOARD_HEIGHT = 10;
	
	/**
	 * LiveObjects are the *things* flying around in space.
	 */
	private ArrayList<SpaceThing> spaceThings = null;
	/**
	 * Gametiles are just empty space.
	 */
	private BaseTile[][] gameTiles = null;
	
	public GameBoard() {
		// Create the game board
		this.gameTiles = new BaseTile[BOARD_WIDTH][BOARD_HEIGHT];
		this.spaceThings = new ArrayList<SpaceThing>();
	}
	
	
	/**
	 * Get a specific tile.
	 * 
	 * @param column
	 * @param row
	 * @return
	 */
	public BaseTile getTile(int column, int row) {
		return this.gameTiles[column][row];
	}
	
	/**
	 * Get the tile occupying a specific "pixel" of the game space.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public BaseTile getTileByPixel(int x, int y) {
		int column = x / PIXEL_WIDTH;
		int row = y / PIXEL_WIDTH;
		
		return getTile(column, row);
	}
}
