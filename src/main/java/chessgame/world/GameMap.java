package chessgame.world;

import com.badlogic.gdx.graphics.OrthographicCamera;

public abstract class GameMap {
	
	public abstract void render (OrthographicCamera camera);
	public abstract void update (float delta);
	public abstract void dispose ();
	
	/**
	 * Return a tile, by its pixel position  within the game world, at a specified layer.
	 * @param layer
	 * @param x
	 * @param y
	 * @return
	 */
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		return this.getTileTypeByCoordinate(layer, (int) (x /TileType.TILE_SIZE), (int) (y/TileType.TILE_SIZE));
	}
	
	/**
	 * Gets a tile based on coordinate within the map.
	 * @param layer
	 * @param col
	 * @param row
	 * @return
	 */
	public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);
	
	/**
	 * Changes the current map to another map
	 * @param name - the name of the map to change to.
	 */
	public abstract void changeMap(String name);
	
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getLayers();
}
