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

import chessgame.utils.Constants;
import chessgame.utils.EntityManager;
import chessgame.utils.Rumble;

public class Tower implements IEnemies {
	int health;
	int attack;
	
	Vector2 position;
	public Vector2 homePosition;
	World world;
	Body myBody;
	public EntityManager entityManager;
	public Sprite sprite;
	
	boolean hasTakenDamage = false;
	float dmgTime = 0;
	
	boolean facingRight;
	long thinkingTime = 400;
	long stoppedTime;
	boolean stopped;
	
	//Entity size
	float width = 0.8f;
	float height = 1.5f;
	
	public Tower (Vector2 position, World world, EntityManager entityManager) {
		homePosition = new Vector2(position.x/Constants.PixelPerMeter+width, position.y/Constants.PixelPerMeter+height);
		this.position = homePosition;
		this.world = world;
		this.entityManager = entityManager;
		health = 5;
		attack = 1;
		facingRight = true;
		stoppedTime = 0;
		stopped = true;
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
		
		//adding side sensors
		addNewBoxSensor(myBody, width * 0.1f, height * 0.95f, new Vector2(width, 0f), "stopper");
		addNewBoxSensor(myBody, width * 0.1f, height * 0.95f, new Vector2(-width, 0f), "stopper");
		

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
		
		keepWithinBounds();
		position = myBody.getPosition();
		if(batch != null) {
			dmgColorTime(Color.RED, 0.25f);
			sprite.setPosition(position.x - sprite.getWidth()/2 , position.y - sprite.getHeight()/2);
			sprite.setSize(1.6f, 3.2f);
			sprite.draw(batch);	
		}
		if(health <= 0)
			kill();	
		
		if(System.currentTimeMillis() > stoppedTime + thinkingTime && stopped) {
			Vector2 direction;
			if(facingRight)
				direction = new Vector2(1,0);
			else
				direction = new Vector2(-1,0);
			moveTo(direction);
		}
	}

	@Override
	public void initialize() {
		sprite = new Sprite(new Texture (Gdx.files.internal("assets/pawn/darkPawn.png").file().getAbsolutePath()));
		createBody();
		
		//Adds the pawn to the entityManager
    	entityManager.addEntity(this);
	}

	@Override
	public void moveTo(Vector2 target) {
		myBody.setLinearVelocity(target.setLength(20f));
		stopped = false;
	}
	
	public void stopped() {
		stopped = true;
		stoppedTime = System.currentTimeMillis();
		if (facingRight)
			facingRight = false;
		else
			facingRight = true;
		if (getClosestPlayer(20f) != null)
			Rumble.rumble(Math.min(1/(getClosestPlayer(20f).getPosition().x-getPosition().x),0.5f), 0.2f);
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void takeDamage(int damage) {
		dmgTime = 0;
		hasTakenDamage = true;
		if(damage <= health)
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
		return null;
	}

	@Override
	public void jump() {
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
	
	public void dmgColorTime(Color color, float time) {
		if(dmgTime > time && hasTakenDamage) {
			sprite.setColor(Color.WHITE);
			hasTakenDamage = false;
		} else if(hasTakenDamage) {
			sprite.setColor(color);
			dmgTime += Gdx.graphics.getDeltaTime();
		}
	}

}
