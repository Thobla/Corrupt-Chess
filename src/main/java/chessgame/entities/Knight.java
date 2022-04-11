package chessgame.entities;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import chessgame.entities.knightstates.*;
import chessgame.utils.Constants;
import chessgame.utils.EntityManager;

public class Knight implements IEnemies {
	int health;
	int attack;
	public float aggroRange = 6f;
	
	Vector2 position;
	public Vector2 homePosition;
	World world;
	Body myBody;
	public EntityManager entityManager;
	public Sprite sprite;
	
	boolean invisFrame;
	long hitTime;
	
	boolean canJump;
	boolean grounded;
	boolean lookingRight;
	Vector2 jumpSpot;
	long jumpTime;
	
	//State	
	public KnightState idleState = new KnightIdle(this);
	public KnightState chaseState = new KnightChase(this);
	KnightState currentState = idleState;
	
	//Entity size
	float width = 0.5f;
	float height = 1f;

	public Knight (Vector2 position, World world, EntityManager entityManager) {
		homePosition = new Vector2(position.x/Constants.PixelPerMeter+width, position.y/Constants.PixelPerMeter+height);
		this.position = homePosition;
		this.world = world;
		this.entityManager = entityManager;
		health = 3;
		attack = 1;
		lookingRight = false;
		jumpSpot = Vector2.Zero;
	}	
		
	
	@Override
	public void createBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(position.x, position.y));
		
		myBody = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		myBody.createFixture(shape, 1000f).setUserData("Enemy");;
		myBody.setFixedRotation(true);
		myBody.setUserData(this);
		
		//adding a weakpoint
		addNewBoxSensor(myBody, width * 0.95f, height / 3.5f, new Vector2(0f, height), "weakpoint");
		addNewBoxSensor(myBody, width * 0.95f, height /16f, new Vector2(0f, -height), "hoof");
	}
	
	

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public void move(Vector2 newPos) {
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
		
		currentState.Update();
		
		keepWithinBounds();
		position = myBody.getPosition();
		if(batch != null) {
			sprite.setPosition(position.x - sprite.getWidth()/2 , position.y - sprite.getHeight()/2);
			sprite.setSize(1, 2);
			sprite.draw(batch);	
		}
		if(health <= 0)
			kill();
		
		if(System.currentTimeMillis() < hitTime + 100) {
			sprite.setColor(Color.RED);
			attack = 0;
		} else {
			sprite.setColor(Color.WHITE);
			attack = 1;
		}
		
		if(System.currentTimeMillis() > jumpTime + 500 && grounded) {
			canJump = true;
		}
	}

	@Override
	public void initialize() {
		sprite = new Sprite(new Texture (Gdx.files.internal("assets/enemies/Knight.png").file().getAbsolutePath()));
		createBody();
		
		//Adds the pawn to the entityManager
    	entityManager.addEntity(this);
	}

	@Override
	public void moveTo(Vector2 target) {
		if (canJump) {
			float xVal = position.x - target.x;
			float yVal = position.y - target.y;
			System.out.println(getPosition() +"  "+ jumpSpot);
			if (getPosition().x == jumpSpot.x) {
				xVal = -xVal/5;
			}
			jumpSpot = new Vector2(getPosition());
			if(xVal > 0) {
				if (lookingRight) {
					sprite.flip(true, false);
					lookingRight = false;
				}
				myBody.setLinearVelocity(-1-xVal, 20);
			} else {
				if (!lookingRight) {
					sprite.flip(true, false);
					lookingRight = true;
				}
				myBody.setLinearVelocity(+1-xVal, 20);
			}
			
			canJump = false;
			grounded = false;
		}	
	}
	
	public void grounded() {
		myBody.setLinearVelocity(Vector2.Zero);
		grounded = true;
		jumpTime = System.currentTimeMillis();
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void takeDamage(int damage) {
		attack = 0;
		if(damage <= health)
			health -= damage;
		else {
			kill();
			return;
		}
		
		invisFrame = true;
		hitTime = System.currentTimeMillis();

	}

	@Override
	public int getAttack() {
		return attack;
	}

	@Override
	public void keepWithinBounds() {
		if(myBody.getPosition().x > 100-width) {
			myBody.setTransform(new Vector2(100-width, myBody.getPosition().y), 0f);
		}
		else if(myBody.getPosition().x < (0+width)) {
			myBody.setTransform(new Vector2(0+width, myBody.getPosition().y), 0f);
		}
		if(myBody.getPosition().y < 0) {
			kill();
		}

	}

	@Override
	public Player getClosestPlayer(Float dist) {
		List<Player> playerList = entityManager.playerList;
		Player target = null;
		
		float finalDist = -1;
		for(Player player : playerList) {
			float distance = (position.dst2(player.getPosition().x, player.getPosition().y)/Constants.PixelPerMeter);
			if((target == null && distance <= dist)) {
				target = player;
				finalDist = distance;
			} else if((distance <= dist && distance < finalDist)) {
				target = player;
				finalDist = distance;
			}
		}
		return target;
	}

	@Override
	public IState getCurrentState() {
		return currentState;
	}
	
	/**
	 * Changes between the states in the stateMachine
	 */
	public void changeState(KnightState state) {
		currentState = state;
		currentState.Enter();
	}

	@Override
	public void jump() {
		canJump = false;
		grounded = false;
		myBody.setLinearVelocity(myBody.getLinearVelocity().x, 18f);
	}
	
	/**
	 * Method to give an existing body a new box-sensor
	 * @param thisBody - the body to add the sensor to
	 * @param length - the length of the sensor
	 * @param height - the height of the sensor
	 * @param centerPosition - the position of the box, relative to the body
	 * @param userData - the user-data to add to the added shape
	 */
	void addNewBoxSensor(Body thisBody, float length, float height, Vector2 centerPosition, String userData) {
		PolygonShape shape = new PolygonShape();
		//creating a fixture that will serve as a sensor for the pawn
		FixtureDef fixDef = new FixtureDef();
		fixDef.isSensor = true;
		shape.setAsBox(length, height, centerPosition, 0);
		fixDef.shape = shape;
		
		myBody.createFixture(fixDef).setUserData(userData);
		
		
	}

}
