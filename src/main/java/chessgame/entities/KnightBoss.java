package chessgame.entities;

import java.util.ArrayList;
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

import chessgame.entities.knightbossstates.KnightBossChase;
import chessgame.entities.knightbossstates.KnightBossHighJump;
import chessgame.entities.knightbossstates.KnightBossIdle;
import chessgame.entities.knightbossstates.KnightBossState;
import chessgame.entities.knightbossstates.KnightBossStunned;
import chessgame.entities.knightstates.*;
import chessgame.utils.Constants;
import chessgame.utils.EntityManager;
import chessgame.utils.Rumble;

public class KnightBoss implements IEnemies {
	int health;
	int attack;
	public float aggroRange = 5f;
	
	Vector2 position;
	public Vector2 homePosition;
	public World world;
	Body myBody;
	public EntityManager entityManager;
	public Sprite sprite;
	
	boolean invisFrame;
	long hitTime;
	
	boolean canJump;
	long jumpTime;
	boolean grounded;
	public boolean lookingRight;
	float preXVal;
	public boolean hit;
	public int allJumps;
	
	public ArrayList<IEntities> minions = new ArrayList<IEntities>();
	
	//State	
	public KnightBossState highJump = new KnightBossHighJump(this);
	public KnightBossState idleState = new KnightBossIdle(this);
	public KnightBossState stunnedState = new KnightBossStunned(this);
	public KnightBossState chaseState = new KnightBossChase(this);
	KnightBossState currentState = idleState;
	public KnightBossState prevState = stunnedState;
	
	//Entity size
	float width = 0.8f*2.6f;
	float height = 1.5f*2.6f;

	public KnightBoss (Vector2 position, World world, EntityManager entityManager) {
		homePosition = new Vector2(position.x/Constants.PixelPerMeter+width, position.y/Constants.PixelPerMeter+height);
		this.position = homePosition;
		this.world = world;
		this.entityManager = entityManager;
		health = 10;
		attack = 1;
		lookingRight = false;
		grounded = true;
		allJumps = 0;
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
		addNewBoxSensor(myBody, width * 0.95f, height / 6f, new Vector2(0f, height), "weakpoint");
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
			sprite.setSize(1.6f*2.6f, 3.2f*2.6f);
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
			hit = false;
		}
		
		if(System.currentTimeMillis() > jumpTime + 650 && grounded) {
			canJump = true;
		}
	}

	@Override
	public void initialize() {
		setSprite("assets/enemies/KnightBoss.png");
		createBody();
		
		//Adds the knight to the entityManager
    	entityManager.addEntity(this);
	}
	
	public void setSprite(String path) {
		sprite = new Sprite(new Texture (Gdx.files.internal(path).file().getAbsolutePath()));
	}

	@Override
	public void moveTo(Vector2 target) {
		if (canJump) {
			float xVal = position.x - target.x;
			float yVal = position.y - target.y;

			if(xVal > 0) {
				if (lookingRight) {
					sprite.flip(true, false);
					lookingRight = false;
				}
				myBody.setLinearVelocity(-xVal, 35);
			} else {
				if (!lookingRight) {
					sprite.flip(true, false);
					lookingRight = true;
				}
				myBody.setLinearVelocity(-xVal*1.5f, 35);
			}
			
			preXVal = xVal;
			canJump = false;
			grounded = false;
			allJumps += 1;
		}	
	}
	
	public void grounded() {
		if (!grounded) {
			myBody.setLinearVelocity(Vector2.Zero);
			grounded = true;
			Rumble.rumble(1f, 0.4f);
			jumpTime = System.currentTimeMillis();
		}
	}
	public boolean isGrounded() {
		return grounded;
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
		hit = true;

		List<Player> players = entityManager.playerList;
		for (Player player : players) {
			float targetDir = player.getPosition().x-getPosition().x;
			player.myBody.setLinearVelocity(new Vector2(Math.copySign(100f , targetDir), 20f));
		}
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
	public void changeState(KnightBossState state) {
		prevState = currentState;
		currentState = state;
		currentState.Enter();
	}

	@Override
	public void jump() {
		canJump = false;
		grounded = false;
		myBody.setLinearVelocity(0f, 28f);
		allJumps += 1;
	}
	
	public void superJump() {
		canJump = false;
		grounded = false;
		myBody.setLinearVelocity(0f, 50f);
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
