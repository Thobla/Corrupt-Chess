package chessgame.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public interface Enemies extends Entities {
	
	/**
	 * Moves the entity towards a defined location
	 * @param target-location
	 */
	public abstract void moveTo(Vector2 target);
	
	/**
	 * updates the current state of the Enemy.
	 */
	public abstract void updateState(Batch batchs);
	
	public abstract Body getBody();
	
}
