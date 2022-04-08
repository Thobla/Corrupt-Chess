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

import chessgame.entities.towerstates.TowerRest;
import chessgame.entities.towerstates.TowerChase;
import chessgame.entities.towerstates.TowerIdle;
import chessgame.entities.towerstates.TowerState;
import chessgame.utils.Constants;
import chessgame.utils.EntityManager;

public class Tower implements IEnemies {
	int health;
	int attack;
	public float aggroRange = 6f;
	
	Vector2 position;
	Vector2 homePosition;
	World world;
	Body myBody;
	public EntityManager entityManager;
	public Sprite sprite;
	
	public boolean dmgOff;
	long hitTime;
	int dir = -2;
	int speed = 3;
	
	//State	
	public TowerState idleState = new TowerIdle(this);
	public TowerState chaseState = new TowerChase(this);
	public TowerState restState = new TowerRest(this);
	TowerState currentState = idleState;
	
	//Entity size
	float width = 0.5f;
	float height = 1f;
	
	public Tower (Vector2 position, World world, EntityManager entityManager) {
		homePosition = new Vector2(position.x/Constants.PixelPerMeter+width, position.y/Constants.PixelPerMeter+height);
		this.position = homePosition;
		this.world = world;
		this.entityManager = entityManager;
		health = 3;
		attack = 1;
	}
	
	public void initialize() {
		sprite = new Sprite(new Texture (Gdx.files.internal("assets/Tower.png").file().getAbsolutePath()));
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
		
		//creating a fixture that will serve as the enemies weakpoint
		FixtureDef fixDef = new FixtureDef();
		fixDef.isSensor = true;
		//the shape should be lower than the players width and height
		shape.setAsBox(width * 1.05f, height / (2*3.5f), new Vector2(0f, height), 0);
		fixDef.shape = shape;
		
		myBody.createFixture(fixDef).setUserData("weakpoint");
		
		//creating a fixture that will serve as the left wall sensor
		fixDef = new FixtureDef();
		fixDef.isSensor = true;
		//the shape should be lower than the players width and height
		shape.setAsBox(width / 3f, height * 0.8f, new Vector2(-(width), 0), 0);
		fixDef.shape = shape;
		
		myBody.createFixture(fixDef).setUserData("Tleft");
		
		//creating a fixture that will serve as the enemies right 
		fixDef = new FixtureDef();
		fixDef.isSensor = true;
		//the shape should be lower than the players width and height
		shape.setAsBox(width / 3f, height * 0.8f, new Vector2((width), 0), 0);
		fixDef.shape = shape;
		
		myBody.createFixture(fixDef).setUserData("Tright");
		
	}
	
	public void removeBody() {
		world.destroyBody(myBody);
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public void move(Vector2 Dir) {
		if(!overlappingPlayer()) {
			if(dir == -2)
				dir = (int) Dir.x;
			if(dir == 1) {
				myBody.setLinearVelocity(speed, myBody.getLinearVelocity().y);
			} else {
				myBody.setLinearVelocity(-speed, myBody.getLinearVelocity().y);
			}
		} else {
			myBody.setLinearVelocity(Vector2.Zero);
		}
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
		if(damage <= health)
			health -= damage;
		else {
			kill();
			return;
		}
		sprite.setColor(Color.RED);
		hitTime = System.currentTimeMillis();
	}

	@Override
	public int getAttack() {
		if(!dmgOff)
			return attack;
		return 0;
	}

	@Override
	public void moveTo(Vector2 target) {
		float xVal = position.x - target.x;
		float yVal = position.y - target.y;
		
		if(!overlappingPlayer()) {
			System.out.println(overlappingPlayer());
			System.out.println("movingTo");
			if(xVal > 0) {
				myBody.setLinearVelocity(-speed, myBody.getLinearVelocity().y);
				dir = 0;
			} else {
				myBody.setLinearVelocity(speed, myBody.getLinearVelocity().y);
				dir = 1;
			}
		} else {
			myBody.setLinearVelocity(Vector2.Zero);
		}
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
		
		if(System.currentTimeMillis() > hitTime + 100) {
			if(sprite.getColor() == Color.RED)
				sprite.setColor(Color.WHITE);
		}
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
	 * changes the walking direction of the tower
	 */
	public void changeDirection() {
		if(dir == 1) {
			dir = 0;
		} else {
			dir = 1;
		}
	}
	/**
	 * sets the walking direction of the tower.
	 * @param dir
	 */
	public void setDirection(int dir) {
		this.dir = dir;
	}
	
	/**
	 * Changes between the states in the stateMachine
	 */
	public void changeState(TowerState state) {
		currentState = state;
		currentState.Enter();
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public IState getCurrentState() {
		return currentState;
	}
	
	/**
	 * checks whether or not the entity is overlapping with the players hitbox, if so return true.
	 * @return
	 */
	public boolean overlappingPlayer() {
		Player player = getClosestPlayer(1f);
			if(player != null) {

				if(Math.abs(player.position.x - position.x) < (width + player.width + 0.15f)) {
					return true;
				} else {
					
				}
			}
		return false;
	}
}
