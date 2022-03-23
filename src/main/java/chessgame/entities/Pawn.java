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

import chessgame.entities.pawnstates.*;
import chessgame.utils.Constants;
import chessgame.utils.EntityManager;

public class Pawn implements IEnemies {
	int health;
	int attack;
	public float aggroRange = 6f;
	
	Vector2 position;
	World world;
	Body myBody;
	public EntityManager entityManager;
	public Sprite sprite;
	
	//State	
	public PawnState idleState = new PawnIdle(this);
	public PawnState chaseState = new PawnChase(this);
	public PawnState homeState = new PawnHome(this);
	public PawnState moveState = new PawnMove(this);
	PawnState currentState = idleState;
	
	//Entity size
	float width = 0.5f;
	float height = 0.5f;
	
	public Pawn (Vector2 position, World world, EntityManager entityManager) {
		this.position = new Vector2(position.x/Constants.PixelPerMeter+width, position.y/Constants.PixelPerMeter+height);
		this.world = world;
		this.entityManager = entityManager;
		health = 2;
		attack = 1;
	}
	
	public void initialize() {
		sprite = new Sprite(new Texture (Gdx.files.internal("assets/pawn/badguy.png").file().getAbsolutePath()));
		createBody();
		
		//Adds the pawn to the entityManager
    	entityManager.addEntity(this);
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
		
		//creating a fixture that will serve as the players groundCheck-platter.
		FixtureDef fixDef = new FixtureDef();
		fixDef.isSensor = true;
		//the shape should be lower than the players width and height
		shape.setAsBox(width * 0.95f, height / 10, new Vector2(0f, height), 0);
		fixDef.shape = shape;
		
		myBody.createFixture(fixDef).setUserData("weakpoint");
		
	}
	
	public void removeBody() {
		world.destroyBody(myBody);
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
	public int getHealth() {
		return health;
	}

	@Override
	public void takeDamage(int damage) {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		int timeSinceCollision = 0;
		timeSinceCollision += deltaTime;
		if(timeSinceCollision > 1.0f) {
			sprite.setColor(Color.RED);
		} else {
			sprite.setColor(Color.WHITE);
		}
		
		if(damage <= health)
			health -= damage;
		else {
			kill();
		}
	}

	@Override
	public int getAttack() {
		return attack;
	}

	@Override
	public void moveTo(Vector2 target) {
		float xVal = position.x - target.x;
		float yVal = position.y - target.y;
		
		if(xVal > 0) {
			myBody.setLinearVelocity(-3, myBody.getLinearVelocity().y);
		} else {
			myBody.setLinearVelocity(3, myBody.getLinearVelocity().y);
		}
	}

	@Override
	public void updateState(Batch batch) {
		
		currentState.Update();
		
		keepWithinBounds();
		position = myBody.getPosition();
		if(batch != null) {
			sprite.setPosition(position.x - sprite.getWidth()/2 , position.y - sprite.getHeight()/2);
			sprite.setSize(1, 1);
			sprite.draw(batch);	
		}
		if(health <= 0)
			kill();
	}

	@Override
	public void kill() {
		entityManager.removeEntity(this);
	}

	@Override
	public Body getBody() {
		return myBody;
	}
	
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
	
	/**
	 * Changes between the states in the stateMachine
	 */
	public void changeState(PawnState state) {
		currentState = state;
		currentState.Enter();
	}

	@Override
	public IState getCurrentState() {
		return currentState;
	}
}
