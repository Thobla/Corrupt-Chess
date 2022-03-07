package chessgame.world;

import java.util.HashMap;
import chessgame.utils.Constants;

public enum TileType {
	//Tiles
	GRASS(1, true, "Grass"),
	SKY(2, false, "Sky"),
	SOME(3, true, "Something"),
	DARK(4, true, "Dark")
	;
	
	static int TILE_SIZE = Constants.PixelPerMeter;
	
	//Attributes of tiles
	private int id;
	private boolean collidable;
	private String name;
	private float damage;
	
	//Constructors
	private TileType (int id, boolean collidable, String name) {
		this(id, collidable, name, 0);
	}
	/**
	 * Gir tiletypen (Grass, sky etc) en id, navn collidable og damage
	 * @param id - Hver tile har en unik id representert med 1,2,3,4... som blir brukt til representere tile i map
	 * @param collidable - Kan entities collide med den eller ikkje
	 * @param name - navn av tilen (grass, sky ect)
	 * @param damage - eventuell damage en entity tar ved collision
	 */
	private TileType (int id, boolean collidable, String name, float damage) {
		this.id = id;
		this.collidable = collidable;
		this.name = name;
		this.damage = damage;
	}

	public int getId() {
		return id;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public String getName() {
		return name;
	}

	public float getDamage() {
		return damage;
	}
	
	//Makes it so that we can get tiles based on ID.
	private static HashMap<Integer, TileType> tileMap;
	
	//Is run when the class is run (At start of game)
	// Sets up a list of all tile-types
	static {
		tileMap = new HashMap<Integer, TileType>();
		for (TileType tiletype : TileType.values()) {
			tileMap.put(tiletype.getId(), tiletype);
			
		}
	}
	//Gets the tile based on input Id
	public static TileType getTileById (int id) {
		return tileMap.get(id);
	}
}
