package chessgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import chessgame.app.PlayerController;
import chessgame.menues.SaveFile;
import chessgame.utils.Constants;
import chessgame.utils.Rumble;

public class Player implements IEntities{
	Vector2 position;
	public World world;
	Sprite sprite;
	public Body myBody;
	public PlayerController controller;
	//PlayerStats
	int health = 3;
	int attack = 1;
	
	//Player prompt
	Sprite prompt;
	public boolean isPrompt;
	BitmapFont font;
	
	public boolean sprint = false;
	public boolean dead = false;
	private int ratingScore = 0;
	
	//Player size
	float width = 0.5f;
	float height = 0.5f;
	
	public Player (Vector2 position, World world) {
		this.position = new Vector2(position.x/Constants.PixelPerMeter+width, position.y/Constants.PixelPerMeter+height);
		this.world = world;
		isPrompt = false;
	}
	
	public void initialize() {
		sprite = new Sprite(new Texture (Gdx.files.internal("assets/player/player.png").file().getAbsolutePath()));
		//prompt = new Sprite(new Texture (Gdx.files.internal("assets/prompt.png").file().getAbsolutePath()));
		createBody();
		
		//Load rating from saveFile
		
    	//PlayerController
		int[] controls = SaveFile.readSettings();
    	controller = new PlayerController(controls);
    	
    	font = new BitmapFont();
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
		float maxSpeed;
		float moveForce;
		
		if(sprint) {
			maxSpeed = 20f;
			moveForce = 400f;
		}
		else {
			maxSpeed = 10f;
			moveForce = 200f;
		}
		
		Vector2 playerVelocity = myBody.getLinearVelocity();
		
		if(movement.x > 0) {
			if (playerVelocity.x < 0) {
				myBody.applyForce(new Vector2(moveForce-playerVelocity.x*50, 0), this.position, true);
			}
			else if(playerVelocity.x < maxSpeed) {
				myBody.applyForce(new Vector2(moveForce, 0), this.position, true);
			} else {
				myBody.setLinearVelocity(new Vector2(maxSpeed, playerVelocity.y));
			}
		}
		else if(movement.x < 0) {
			if (playerVelocity.x > 0) {
				myBody.applyForce(new Vector2(-moveForce-playerVelocity.x*50, 0), this.position, true);
			}
			else if(-playerVelocity.x < maxSpeed) {
				myBody.applyForce(new Vector2(-moveForce, 0), this.position, true);
			} else {
				myBody.setLinearVelocity(new Vector2(-maxSpeed, playerVelocity.y));
			}
		}
		else if(movement.x == 0 && controller.isGrounded) {
			myBody.applyForce(new Vector2(-playerVelocity.x*50, 0), this.position, true);
		} 
		else if(movement.x == 0 && !controller.isGrounded) {
			myBody.applyForce(new Vector2(-playerVelocity.x*10, 0), this.position, true);
		}
	}
	/**
	 * Applies upward force to the entity, making it "jump"
	 * @param jumpForce
	 * @author Mikal, Thorgal
	 */
	public void jump(float jumpForce) {	
		myBody.applyLinearImpulse(new Vector2(0, jumpForce),this.position ,true);

	}
	public Vector2 getVelocity() {
		return myBody.getLinearVelocity();
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
		
		myBody = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		myBody.createFixture(shape, 10f).setUserData("Player");
		myBody.setFixedRotation(true);
		myBody.setUserData(this);
		
		//creating a fixture that will serve as the players groundCheck-platter.
		FixtureDef fixDef = new FixtureDef();
		fixDef.isSensor = true;
		//the shape should be lower than the players width and height
		shape.setAsBox(width * 0.95f, height / 8, new Vector2(0f, -height), 0);
		fixDef.shape = shape;
		
		myBody.createFixture(fixDef).setUserData("foot");
		
		//creating a fixture that will serve as the players skyCheck
		fixDef = new FixtureDef();
		fixDef.isSensor = true;
		//the shape should be lower than the players width and height
		shape.setAsBox(width * 0.95f, height * 0.2f, new Vector2(0f, +height), 0);
		fixDef.shape = shape;
		myBody.createFixture(fixDef).setUserData("sky");
		
	}	

	public int getHealth() {
		return health;
	}

	public void takeDamage(int damage) {
		if(damage < health)
			health -= damage;
		else {
			health = 0;
			kill();
		}
			
	}

	public int getAttack() {
		return attack;
	}
	
	public int getScore() {
		return getRatingScore();
	}

	@Override
	public void kill() {
		dead = true;
	}

	@Override
	public void removeBody() {
		// TODO Auto-generated method stub
		
	}
	
	public void updatePosition() {
		position = myBody.getPosition();
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
	
	/**
	 * Updates some aspects of the players data, such as: 
	 * Sprite,
	 * Position
	 * @author Mikal, Thorgal
	 * @param batch
	 */
	@Override
	public void updateState(Batch batch) {
	//Sets the maximum speed upward of the player.
			if(myBody.getLinearVelocity().y > 30)
				myBody.setLinearVelocity(new Vector2(myBody.getLinearVelocity().x, 20));
			//Updates position vector2
			updatePosition();
			
	    	controller.myController(this);
			keepWithinBounds();
	    	
			sprite.setPosition(position.x - sprite.getWidth()/2 , position.y - sprite.getHeight()/2);
			sprite.setSize(1, 1);
			sprite.draw(batch);
			
			if(health == 0)
				kill();
			
			if(isPrompt) {
				//prompt.setPosition(position.x - sprite.getWidth()/2, position.y + sprite.getHeight()*1.5f);
				//prompt.setSize(1, 1);
				//prompt.draw(batch);
				
				//font.getData().setScale(0.1f);
				//font.draw(batch, "E", position.x - sprite.getWidth()/2, position.y + sprite.getHeight()*3f);
			}
	
	}
	
	public void renderPlayer(Batch batch) {
		controller.myController(this);
		keepWithinBounds();
    	
		sprite.setPosition(position.x - sprite.getWidth()/2 , position.y - sprite.getHeight()/2);
		sprite.setSize(1, 1);
		sprite.draw(batch);
	}

	@Override
	public Body getBody() {
		return myBody;
	}
	
	public void setPrompt(Sprite sprite) {
		//prompt = sprite;
		isPrompt = true;
	}
	public void endPrompt() {
		isPrompt = false;
	}

	public int getRatingScore() {
		return ratingScore;
	}

	public void setRatingScore(int ratingScore) {
		this.ratingScore = ratingScore;
	}
}
