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

import chessgame.app.PlayerController;
import chessgame.menues.SaveFile;

public class Player implements Entities{
	Vector2 position;
	World world;
	Sprite sprite = new Sprite(new Texture (Gdx.files.internal("assets/player.png").file().getAbsolutePath()));
	public Body playerBody;
	public PlayerController controller;
	//PlayerStats
	int health = 3;
	int attack = 1;
	public boolean dead = false;
	
	//Player size
	float width = 0.5f;
	float height = 0.5f;
	
	public Player (Vector2 position, World world) {
		this.position = new Vector2(position.x/32, position.y/32);
		this.world = world;
		createBody();
		//sets the userData as a pointer to the player (this is used for groundCheck in ListnerClass and PlayerController)
		playerBody.setUserData(this);
		
    	//PlayerController
		byte[] controls = SaveFile.readSettings();
    	controller = new PlayerController(controls);
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}
	/**
	 * Moves the entity based on input vector2
	 * @param Vector2 - movement on the axises
	 * @author Thorgal, Mikal
	 */
	@Override
	public void move(Vector2 movement) {
		playerBody.setLinearVelocity(movement);
	}
	/**
	 * Applies upward force to the entity, making it "jump"
	 * @param jumpForce
	 * @author Mikal, Thorgal
	 */
	public void jump(float jumpForce) {
		playerBody.applyForceToCenter(new Vector2(0, jumpForce), true);
	}
	public Vector2 getVelocity() {
		return playerBody.getLinearVelocity();
	}
	
	@Override
	public Sprite getSprite() {
		return sprite;
	}
	
	
	@Override
	public void createBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(position.x, position.y));
		
		playerBody = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		playerBody.createFixture(shape, 10f);
		playerBody.setFixedRotation(true);
		playerBody.setUserData("Hello");
		
		//creating a fixture that will serve as the players groundCheck-platter.
		FixtureDef fixDef = new FixtureDef();
		fixDef.isSensor = true;
		//the shape should be lower than the players width and height
		shape.setAsBox(width * 0.95f, height / 20, new Vector2(0f, -height), 0);
		fixDef.shape = shape;
		
		playerBody.createFixture(fixDef).setUserData("foot");
		
		//creating a fixture that will serve as the players skyCheck
		fixDef = new FixtureDef();
		fixDef.isSensor = true;
		//the shape should be lower than the players width and height
		shape.setAsBox(width * 1.2f, height * 0.2f, new Vector2(0f, +height), 0);
		fixDef.shape = shape;
		playerBody.createFixture(fixDef).setUserData("sky");
		
	}
	
	
	/**
	 * Updates some aspects of the players data, such as: 
	 * Sprite,
	 * Position
	 * @author Mikal, Thorgal
	 * @param batch
	 */
	public void updatePlayer(Batch batch) {
		
		//Sets the maximum speed upward of the player.
		if(playerBody.getLinearVelocity().y > 30)
			playerBody.setLinearVelocity(new Vector2(playerBody.getLinearVelocity().x, 20));
		//Updates position vector2
		position = playerBody.getPosition();
		
    	controller.myController(this);
		keepWithinBounds();
    	
		sprite.setPosition(position.x - sprite.getWidth()/2 , position.y - sprite.getHeight()/2);
		sprite.setSize(1, 1);
		sprite.draw(batch);
		
		if(health == 0)
			kill();
	}
	
	public void controllerUpdate() {
		controller.myController(this);
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}

	@Override
	public void takeDamage(int damage) {
		if(damage < health)
			health -= damage;
		else
			kill();
	}

	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return attack;
	}

	@Override
	public void kill() {
		dead = true;
	}

	@Override
	public void removeBody() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keepWithinBounds() {
		if(playerBody.getPosition().x > 100-0.5f) {
			playerBody.setTransform(new Vector2(100-0.5f, playerBody.getPosition().y), 0f);
		}
		else if(playerBody.getPosition().x < (0+0.5f)) {
			playerBody.setTransform(new Vector2(0+0.5f, playerBody.getPosition().y), 0f);
		}
		if(playerBody.getPosition().y < 0) {
			kill();
		}
	}
}
