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

import chessgame.utils.Constants;
import chessgame.utils.EntityManager;

public class RBButton implements IObjects{
	
	Vector2 position;
	Sprite sprite;
	Body myBody;
	World world;
	EntityManager entityManager;
	
	boolean active = false;
	Sprite spriteOn;
	Sprite spriteOff;
	
	int activationCodeRB;
	
	float width = 1f;
	float height = 0.5f;
	
	public RBButton(Vector2 position, World world, EntityManager entityManager, int rbcode) {
		this.position = new Vector2(position.x/Constants.PixelPerMeter+width, position.y/Constants.PixelPerMeter+height);
		this.world = world;
		this.entityManager = entityManager;
		activationCodeRB = rbcode;
	}
	
	public void initialize() {

		spriteOff = new Sprite(new Texture (Gdx.files.internal("assets/objects/rbButtonTestRed.png").file().getAbsolutePath()));
		spriteOn = new Sprite(new Texture (Gdx.files.internal("assets/objects/rbButtonTestBlue.png").file().getAbsolutePath()));

		sprite = spriteOff;
		
		entityManager.addEntity(this);
		
		createBody();
	}
	
	@Override
	public void createBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(new Vector2(position.x, position.y-0.344f));
		
		myBody = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height*0.18f);
		
		myBody.createFixture(shape, 1000f).setUserData("ground");;
		myBody.setFixedRotation(true);
		myBody.setUserData(this);
		
		//creating a fixture that will serve as the players groundCheck-platter.
		FixtureDef fixDef = new FixtureDef();
		fixDef.isSensor = true;
		//the shape should be lower than the players width and height
		shape.setAsBox(width * 0.95f, height / 10, new Vector2(0f, height*0.344f), 0);
		fixDef.shape = shape;
		
		myBody.createFixture(fixDef).setUserData("Object");
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

	}

	@Override
	public void removeBody() {

	}

	@Override
	public void updateState(Batch batch) {
		position = myBody.getPosition();
		
		if(batch != null) {
			sprite.setPosition(position.x - sprite.getWidth()/2 , position.y- 0.19f);
			sprite.setSize(2, 1);
			sprite.draw(batch);	
		}
	}

	public void getActivationCode(int rbcode) {
		activationCodeRB = rbcode;
	}
	
	
	@Override
	public void itemFunction(Player player) {
		if(!active) {
			active = true;
			sprite = spriteOn;
		} else {
			active = false;
			sprite = spriteOff;
		}
		if(entityManager.rBlockMap.containsKey(activationCodeRB)) {
			RBlock rblock = entityManager.rBlockMap.get(activationCodeRB);
			rblock.rBlockState();
		}
		
		if(entityManager.bBlockMap.containsKey(activationCodeRB)) {
			BBlock bblock = entityManager.bBlockMap.get(activationCodeRB);
			bblock.bBlockState();
		}
	}
}
