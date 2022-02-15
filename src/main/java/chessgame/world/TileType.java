package chessgame.world;

import java.util.HashMap;

public enum TileType {
	
	//Tiles
	GRASS(1, true, "Grass"),
	SKY(2, false, "Sky"),
	CHECKERG(3, true, "Checker_green"),
	CHECKERGC(4, true, "Checker_green_TRCorner"),
	CHECKERGB(5, true, "Checker_green_TRCorner")
	;
	
	//Tile-size
	public static final int TILE_SIZE = 16;
	
	//Attributes of tiles
	private int id;
	private boolean collidable;
	private String name;
	private float damage;
	
	//Constructors
	private TileType (int id, boolean collidable, String name) {
		this(id, collidable, name, 0);
	}
	
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
		for (TileType tiletype : TileType.values()) {
			tileMap.put(tiletype.getId(), tiletype);
			
		}
	}
	//Gets the tile based on input Id
	public static TileType getTileById (int id) {
		return tileMap.get(id);
	}
}
