package chessgame.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class TiledGameMap extends GameMap{
    
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	MapProperties properties;
	
	public TiledGameMap(String map) {
		
		tiledMap = new TmxMapLoader().load(Gdx.files.internal("assets/"+map+".tmx").file().getAbsolutePath());
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		properties = tiledMap.getProperties();
	}
	
	@Override
	public void render(OrthographicCamera camera) {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		tiledMap.dispose();
		
	}
	/**
	 * Gets the tileType in the map, based on its layer, column and row
	 * 
	 * @return TileType of said tile
	 */
	@Override
	public TileType getTileTypeByCoordinate(int layer, int col, int row) {
		//Gets the cell at the wanted location
		Cell cell = (((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col, row));
		//checks that the cell is not empty, or out of bounds
		if(cell != null) {
			//Gets the tile inside the cell
			TiledMapTile tile = cell.getTile();
			
			//If the tile is not empty ->
			if (tile != null) {
				int id = tile.getId();
				//return the TileType, with said id.
				return TileType.getTileById(id);
			}
		}
		return null;
	}

	@Override
	/**
	 * Returns the width of the map in Tiles.
	 */
	public int getWidth() {
		// TODO Auto-generated method stub
		return properties.get("width", Integer.class);
	}
	
	/**
	 * Returns the width of the map in Pixels.
	 */
	public int getWidthPixels() {
		return getWidth()*32;
	}
	
	/**
	 * Returns the height of the map in Tiles.
	 */
	@Override
	public int getHeight() {
		return properties.get("height", Integer.class);
	}
	
	/**
	 * Returns the height of the map in Pixels.
	 */
	public int getHeightPixels() {
		return getWidth()*32;
	}

	@Override
	public int getLayers() {
		return tiledMap.getLayers().size();
	}

	@Override
	public void changeMap(String name) {
		tiledMap = new TmxMapLoader().load(Gdx.files.internal("assets/"+name+".tmx").file().getAbsolutePath());
		tiledMapRenderer.setMap(tiledMap);
	}

	@Override
	public Vector2 getStartPoint() {
		return new Vector2(200,200);
	}

}
