package chessgame.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public interface Entities {
	
	/**
	 * Gets the position of the entity according to the gameMap.
	 * @return the position.
	 */
	public abstract Vector2 getPosition();
	
	/**
	 * moves the entity on the gameMap.
	 */
	public abstract void move();
	
	/**
	 * Gets the current sprite of the entity
	 * @return the sprite.
	 */
	public abstract Sprite getSprite();
}
