package chessgame.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import chessgame.utils.Constants;
import chessgame.utils.EntityManager;

public class ThePope implements IEnemies {
	
	Vector2 position;
	World world;
	EntityManager entityManager;
	
	float health;
	float attack;
	
	float width;
	float height;
	
	public ThePope(Vector2 position, World world, EntityManager entityManager) {
		this.position = new Vector2(position.x/Constants.PixelPerMeter+width, position.y/Constants.PixelPerMeter+height);
		this.world = world;
		this.entityManager = entityManager;
		
		health = 1;
		attack = 1;
	}
	
	@Override
	public void createBody() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void move(Vector2 newPos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Body getBody() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveTo(Vector2 target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void takeDamage(int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void keepWithinBounds() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Player getClosestPlayer(Float dist) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IState getCurrentState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void jump() {
		// TODO Auto-generated method stub
		
	}

}
