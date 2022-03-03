package chessgame.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public interface Entities {
	/**
	 * Creates the entity body
	 * @author Thorgal, Mikal
	 * 
	 * 
	 */
	abstract void createBody();
	/**
	 * Gets the position of the entity according to the gameMap.
	 * @return the position.
	 */
	public abstract Vector2 getPosition();
	
	/**
	 * moves the entity on the gameMap.
	 */
	public abstract void move(Vector2 newPos);
	
	/**
	 * Gets the current sprite of the entity
	 * @return the sprite.
	 */
	public abstract Sprite getSprite();
	/**
	 * Returns the amount of health of the entity
	 * @return - health
	 * @author Mikal, Thorgal
	 */
	public abstract int getHealth();
	
	/**
	 * Reduces the HP of the entity by the input value.
	 * @param damage
	 * @author Mikal, Thorgal
	 */
	public abstract void takeDamage(int damage);
	
	/**
	 * Gets the Attack-Damage of the entity.
	 * @return damage
	 * @author Thorgal, Mikal
	 */
	public abstract int getAttack();
	
	/**
	 * Kills the entity.
	 */
	public abstract void kill();
	
	/**
	 * removes the Box2D body of the entity
	 */
	public void removeBody();
}
