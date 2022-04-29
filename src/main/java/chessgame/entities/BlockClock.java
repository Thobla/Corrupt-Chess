package chessgame.entities;

import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import chessgame.utils.EntityManager;

public class BlockClock implements IObjects {
	
	EntityManager entityManager;
	float timer = 0;
	float maxTime = 0;
	/**
	 * A helping class for RBlock
	 */
	public BlockClock(EntityManager entityManager, float maxTime) {
		this.entityManager = entityManager;
		this.maxTime = maxTime;
	}
	
	@Override
	public void createBody() {
	}

	@Override
	public Vector2 getPosition() {
		return null;
	}

	@Override
	public void move(Vector2 newPos) {
	}

	@Override
	public Sprite getSprite() {
		return null;
	}

	@Override
	public Body getBody() {
		return null;
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
 
	@Override
	public void removeBody() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateState(Batch batchs) {
		timer += Gdx.graphics.getDeltaTime();
		if(timer >= maxTime) {
			itemFunction(null);
		}
	}

	@Override
	public void initialize() {
		entityManager.addEntity(this);
	}

	@Override
	public void itemFunction(Player player) {
		for(Entry<Integer, BBlock> entry : entityManager.bBlockMap.entrySet()) {
			entry.getValue().bBlockState();
		}
		for(Entry<Integer, RBlock> entry : entityManager.rBlockMap.entrySet()) {
			entry.getValue().rBlockState();
		}
		timer = 0;
	}
	
}
