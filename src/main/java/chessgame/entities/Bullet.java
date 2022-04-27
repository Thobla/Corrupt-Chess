package chessgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import chessgame.app.Game;
import chessgame.utils.Direction;
import chessgame.utils.EntityManager;

public class Bullet implements IEntities{
	
	Vector2 position;
	World world;
	Body myBody;
	Sprite sprite;
	EntityManager entityManager;
	
	float width = .5f;
	float height = .5f;
	float bulletSpeed;
	
	double spawnTime;
	
	//direction of the bullet
	Direction direction;
	
	public Bullet (Vector2 position, World world, EntityManager entityManager, Direction direction) {
		this.position = position;
		this.world = world;
		this.entityManager = entityManager;
		this.direction = direction;
	}
	
	@Override
	public void initialize() {
		sprite = new Sprite(new Texture (Gdx.files.internal("assets/bullet.png").file().getAbsolutePath()));
		if (!Game.gameStart)
			entityManager.addEntity(this);
		else
			entityManager.addRuntimeEntity(this);
		createBody();
		spawnTime = System.currentTimeMillis();
	}
	
	@Override
	public void createBody() {
		BodyDef bodyDef = new BodyDef();
		
		//creating a fixture that will serve as the players groundCheck-platter.
		FixtureDef fixDef = new FixtureDef();
		
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(position.x, position.y));
		
		myBody = world.createBody(bodyDef);
		myBody.setGravityScale(0);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		fixDef.shape = shape;
		fixDef.density = 10f;
		fixDef.isSensor = true;
		
		myBody.createFixture(fixDef).setUserData("Bullet");
		myBody.setFixedRotation(true);
		myBody.setUserData(this);	
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public void move(Vector2 newPos) {

	}
	
	public void bulletMove(Direction dir) {
		Vector2 movement = new Vector2(0, 0);
		if(dir == Direction.LEFT || dir == Direction.DOWNLEFT || dir == Direction.UPLEFT)
			movement.x = -bulletSpeed;
		else if(dir == Direction.RIGHT || dir == Direction.DOWNRIGHT || dir == Direction.UPRIGHT)
			movement.x = bulletSpeed;
		if(dir == Direction.UP || dir == Direction.UPLEFT || dir == Direction.UPRIGHT)
			movement.y = bulletSpeed;
		else if (dir == Direction.DOWN || dir == Direction.DOWNLEFT || dir == Direction.DOWNRIGHT)
			movement.y = -bulletSpeed;
		
		myBody.setLinearVelocity(movement);
	}
	
	public void setBulletSpeed(float speed) {
		bulletSpeed = speed;
	}
	
	public void setBulletDirection(Direction dir) {
		direction = dir;
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public Body getBody() {
		return myBody;
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
	public void updateState(Batch batch) {
		if(System.currentTimeMillis() > spawnTime + 1000)
			kill();
		//Updates the position
		position = myBody.getPosition();
		bulletMove(direction);
		
		sprite.setPosition(position.x - sprite.getWidth()/2 , position.y - sprite.getHeight()/2);
		sprite.setSize(1, 1);
		sprite.draw(batch);
	}
	
}
