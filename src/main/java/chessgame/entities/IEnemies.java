package chessgame.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * IEnemies, interface for the enemy classes, extends IEntities
 * @author Thorgal, Mikal
 *
 */
public interface IEnemies extends IEntities {
	
	/**
	 * Moves the entity towards a defined location
	 * @param target-location
	 */
	public abstract void moveTo(Vector2 target);
	
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
	
	
	
}
