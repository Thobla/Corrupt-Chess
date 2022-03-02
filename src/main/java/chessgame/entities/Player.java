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

public class Player implements Entities{
	Vector2 position;
	World world;
	Sprite sprite = new Sprite(new Texture (Gdx.files.internal("assets/player.png").file().getAbsolutePath()));
	Body playerBody;
	PlayerController controller;
	
	float width = 0.5f;
	float height = 0.5f;
	
	public Player (Sprite sprite, Vector2 position, World world) {
		this.position = new Vector2(position.x/32, position.y/32);
		this.world = world;
		createBody();
		playerBody.setUserData(sprite);
		
    	//PlayerController
    	controller = new PlayerController();
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
		
		//creating a fixture that will serve as the players groundCheck-platter.
		FixtureDef fixDef = new FixtureDef();
		fixDef.isSensor = true;
		//the shape should be lower than the players width and height
		shape.setAsBox(width * 0.95f, height / 20, new Vector2(0f, -height), 0);
		fixDef.shape = shape;
		
		playerBody.createFixture(fixDef).setUserData("foot");
		
	}
	
	
	/**
	 * Updates some aspects of the players data, such as: 
	 * Sprite,
	 * Position
	 * @author Mikal, Thorgal
	 * @param batch
	 */
	public void updatePlayer(Batch batch) {
		
    	controller.myController(this);
		position = playerBody.getPosition();
		sprite.setPosition(position.x - sprite.getWidth()/2 , position.y - sprite.getHeight()/2);
		sprite.setSize(1, 1);
		sprite.draw(batch);
	}
	
}
