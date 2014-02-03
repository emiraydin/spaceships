package model;

public class Board {
	
	private final int PIXEL_WIDTH = 32;
	private final int BOARD_WIDTH = 10;
	private final int BOARD_HEIGHT = 10;
	
	private Tile[][] gameTiles = null;
	
	public Board() {
		// Create the game board
		this.gameTiles = new Tile[BOARD_WIDTH][BOARD_HEIGHT];
	}
	
	/**
	 * Get a specific tile.
	 * 
	 * @param column
	 * @param row
	 * @return
	 */
	public Tile getTile(int column, int row) {
		return this.gameTiles[column][row];
	}
	
	/**
	 * Get the tile occupying a specific "pixel" of the game space.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTileByPixel(int x, int y) {
		int column = x / PIXEL_WIDTH;
		int row = y / PIXEL_WIDTH;
		
		return getTile(column, row);
	}
}
