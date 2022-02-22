package chessgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player implements Entities{
	Vector2 position;
	World world;
	Sprite sprite = new Sprite(new Texture (Gdx.files.internal("assets/player.png").file().getAbsolutePath()));
	Body playerBody;
	
	public Player (Sprite sprite, Vector2 position, World world) {
		this.position = new Vector2(position.x/32, position.y/32);
		this.world = world;
		createBody();
		playerBody.setUserData(sprite);
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
		shape.setAsBox(0.5f, 0.5f);
		
		playerBody.createFixture(shape, 10);
		playerBody.setFixedRotation(true);
	}
	
	/**
	 * Updates some aspects of the players data, such as: 
	 * Sprite,
	 * Position
	 * @author Mikal, Thorgal
	 * @param batch
	 */
	public void updatePlayer(Batch batch) {
		position = playerBody.getPosition();
		sprite.setPosition(position.x - sprite.getWidth()/2 , position.y - sprite.getHeight()/2);
		sprite.setSize(1, 1);
		sprite.draw(batch);
	}
	
}
