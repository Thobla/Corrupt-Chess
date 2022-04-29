package chessgame.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import chessgame.utils.Constants;
import chessgame.utils.EntityManager;

public class ThePope implements IEnemies {
	
	Vector2 position;
	World world;
	EntityManager entityManager;
	Body myBody;
	
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
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(position.x, position.y));
		
		myBody = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		myBody.createFixture(shape, 1f).setUserData("Enemy");;
		myBody.setFixedRotation(true);
		myBody.setUserData(this);
		
		//adding a weakpoint
		addNewBoxSensor(myBody, width * 0.95f, height / 3.5f, new Vector2(0f, height), "weakpoint");
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public void move(Vector2 newPos) {
	}
	
	void addNewBoxSensor(Body thisBody, float length, float height, Vector2 centerPosition, String userData) {
		PolygonShape shape = new PolygonShape();
		//creating a fixture that will serve as a sensor for the pawn
		FixtureDef fixDef = new FixtureDef();
		fixDef.isSensor = true;
		shape.setAsBox(length, height, centerPosition, 0);
		fixDef.shape = shape;
		
		myBody.createFixture(fixDef).setUserData(userData);
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
		entityManager.removeEntity(this);
	}

	@Override
	public void removeBody() {
		world.destroyBody(myBody);
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
