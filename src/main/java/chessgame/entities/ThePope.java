package chessgame.entities;

import java.util.List;

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
import chessgame.entities.thePopeStates.ThePopeBowserState;
import chessgame.entities.thePopeStates.ThePopeDormantState;
import chessgame.entities.thePopeStates.ThePopeIdleState;
import chessgame.entities.thePopeStates.ThePopeLavaState;
import chessgame.entities.thePopeStates.ThePopeShootState;
import chessgame.entities.thePopeStates.ThePopeStates;
import chessgame.utils.Constants;
import chessgame.utils.Direction;
import chessgame.utils.EntityAnimation;
import chessgame.utils.EntityManager;

public class ThePope implements IEnemies {
	
	Vector2 position;
	Vector2 spawnPosition;
	World world;
	public EntityManager entityManager;
	public Body myBody;
	public Body circle;
	
	public boolean dormant = true;
	public boolean trigger = false;
	
	public EntityAnimation dormantAnimation;
	public EntityAnimation triggerAnimation;
	public EntityAnimation castingAnimation;
	public EntityAnimation magicCircle;
	public EntityAnimation magicCircleLockOn;
	
	public ThePopeStates currentState;
	public ThePopeStates dormantState = new ThePopeDormantState(this);
	public ThePopeStates idleState = new ThePopeIdleState(this);
	public ThePopeStates lavaState = new ThePopeLavaState(this);
	public ThePopeStates bowserState = new ThePopeBowserState(this);
	public ThePopeStates shootState = new ThePopeShootState(this);
	
	int health;
	int attack;
	
	//Difficulty scaling
	public int magicRoations = 3;
	public int bullets = 5;
	public int lavaSpeed;
	
	
	float width = 0.5f;
	float height = 1.75f;
	
	Sprite sprite;
	//Bowser attack
	public boolean useMagicCircle;
	Player magicTarget;
	public boolean magicLock = false;
	private int magicCounter;
	private int magicTime;
	Vector2 targetPosition = new Vector2(0,0);
	public boolean finishMagicCircle = true;
	
	public ThePope(Vector2 position, World world, EntityManager entityManager) {
		this.position = new Vector2(position.x/Constants.PixelPerMeter+width, position.y/Constants.PixelPerMeter+height);
		spawnPosition = this.position;
		this.world = world;
		this.entityManager = entityManager;
		
		dormant = true;
		health = 5;
		attack = 1;
	}
	
	@Override
	public void initialize() {
		currentState = dormantState;
		dormantState.Enter();
		
		entityManager.addEntity(this);
		//normal sprite
		Texture bishopSprite = new Texture (Gdx.files.internal("assets/enemies/bishop.png").file().getAbsolutePath());
		sprite = new Sprite(bishopSprite);
		//animations
		Texture dormantSheet = new Texture (Gdx.files.internal("assets/Enemies/ThePope/bishopDormant.png").file().getAbsolutePath());
		dormantAnimation = new EntityAnimation(dormantSheet, 8, 16f, this, new Vector2(64f, 128f));
		Texture triggerSheet = new Texture (Gdx.files.internal("assets/Enemies/ThePope/triggerSheet.png").file().getAbsolutePath());
		triggerAnimation = new EntityAnimation(triggerSheet, 13, 16f, this, new Vector2(64f, 128f));
		Texture magicSheet = new Texture (Gdx.files.internal("assets/Enemies/ThePope/magicCircle.png").file().getAbsolutePath());
		magicCircle = new EntityAnimation(magicSheet, 9, 9f, this, new Vector2(96f, 96f));
		Texture magicLockSheet = new Texture (Gdx.files.internal("assets/Enemies/ThePope/magicCircleLockOn.png").file().getAbsolutePath());
		magicCircleLockOn = new EntityAnimation(magicLockSheet, 4, 8f, this, new Vector2(96f, 96f));
		createBody();
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
		
		//MagicCircle hitbox
		shape = new PolygonShape();
		shape.setAsBox(1.5f, 1.5f);

		FixtureDef fixDef = new FixtureDef();
		fixDef.isSensor = true;
		fixDef.shape = shape;
		
		circle = world.createBody(bodyDef);
		circle.createFixture(fixDef).setUserData("circle");
		circle.setUserData(this);
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
	
	public void magicCircleAttack(int time) {
		useMagicCircle = true;
		magicTarget = null;
		magicLock = false;
		magicCounter = 0;
		magicTime = time;
	}
	public void magicCircleController(Batch batch) {
		Boolean insideLock = true;
		if(useMagicCircle) {
			if(magicTarget == null) {
				magicTarget = getClosestPlayer(100f);
			}
			if(!magicLock) {
				if(magicCounter < magicTime /2)
					targetPosition = new Vector2(magicTarget.getPosition().x, magicTarget.position.y);
				if(magicCounter < magicTime)
					insideLock = magicCircle.playOnce(batch, targetPosition.x - (96/32), targetPosition.y-(96/32), true);
				else
					magicLock = true;
				if(!insideLock)
					magicCounter++;
			} else {
				finishMagicCircle = magicCircleLockOn.playOnce(batch, targetPosition.x - (96/32), targetPosition.y -(96/32), true);
				magicCircleHitBox(batch);
			}
		}
	}
	public void magicCircleHitBox(Batch batch) {
		useMagicCircle = false;
		magicCircleLockOn.playOnce(batch, targetPosition.x - (96/32), targetPosition.y -(96/32), true);
		circle.setActive(true);
		circle.setTransform(new Vector2(targetPosition.x, targetPosition.y), 0);
		//circle.setActive(false);
	}
	
	public void magicShot() {
		Player player = getClosestPlayer(100f);
		Bullet b = new Bullet(this.position, world, entityManager, Direction.UP, player);
		b.initialize();
	}

	@Override
	public void updateState(Batch batch) {
		currentState.Update();
		
		position = myBody.getPosition();
		if(batch != null) {
			if(useMagicCircle) {
				magicCircleController(batch);
			}
			if(dormant) {
				if(trigger) {
					dormant = triggerAnimation.playOnce(batch, position.x - 2*width, position.y - height);
				} else {
					dormantAnimation.render(batch, position.x - 2*width, position.y - height, false);
				}
			} else {
			sprite.setPosition(position.x - 2*width , position.y - height);
			sprite.setSize(2, 4);
			sprite.draw(batch);	
			}
		}
	}

	@Override
	public void moveTo(Vector2 target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void takeDamage(int damage) {
		if(damage < health)
			health -= damage;
		else {
			kill();
			return;
		}
	}

	@Override
	public int getAttack() {
		return attack;
	}

	@Override
	public void keepWithinBounds() {
		if(myBody.getPosition().x > Game.mapSize.x-width) {
			myBody.setTransform(new Vector2(Game.mapSize.x-width, myBody.getPosition().y), 0f);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void jump() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Changes between the states in the stateMachine
	 */
	public void changeState(ThePopeStates state) {
		currentState = state;
		currentState.Enter();
	}

}
