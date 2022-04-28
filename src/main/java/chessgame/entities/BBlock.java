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

import chessgame.utils.Constants;
import chessgame.utils.EntityManager;

public class BBlock implements IEntities {
	
	Vector2 position;
	Sprite sprite;
	Body myBody;
	World world;
	EntityManager entityManager;
	
	Sprite spriteOff;
	Sprite spriteOn;
	
	Boolean isOn;
	int activationCodeRB;
	
	float width = 0.49f;
	float height = 0.49f;
	
	
	public BBlock(Vector2 position, World world, EntityManager entityManager, int rbcode){
		this.position = new Vector2(position.x/Constants.PixelPerMeter+width, position.y/Constants.PixelPerMeter+height);
		this.world = world;
		this.entityManager = entityManager;
		this.activationCodeRB = rbcode;
		isOn = true;
	}
	
	public void initialize() {
		spriteOff = new Sprite(new Texture (Gdx.files.internal("assets/objects/bBlockOff.png").file().getAbsolutePath()));
		spriteOn = new Sprite(new Texture (Gdx.files.internal("assets/objects/bBlockOn.png").file().getAbsolutePath()));
		sprite = spriteOn;
		
		createBody();
		
		entityManager.addEntity(this);
		entityManager.bBlockMap.put(activationCodeRB, this);
	}
	
	@Override
	public void createBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(new Vector2(position.x, position.y));
		
		myBody = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		myBody.createFixture(shape, 1000f).setUserData("BBlock");;
		myBody.setFixedRotation(true);
		myBody.setUserData(this);
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
		
		if(isOn) {
			myBody.getFixtureList().get(0).setSensor(true);
			myBody.getFixtureList().get(0).setUserData("ColorBlockOff");
			sprite = spriteOff;
		} else {
			myBody.getFixtureList().get(0).setSensor(false);
			myBody.setUserData("BBlock");
			sprite = spriteOn;
		}
		
		if(batch != null) {
			sprite.setPosition(position.x - sprite.getWidth()/2 , position.y-sprite.getHeight()/2);
			sprite.setSize(1, 1);
			sprite.draw(batch);	
		}
		
	}
	/**
	 * Changes the door state based on previous state.
	 * If this is called when door is open, it closes
	 * If this is called when door is closed, it opens.
	 */
	public void bBlockState() {
		if(!isOn) {
			isOn = true;
		} else {
			isOn = false;
		}
	}
	/**
	 * checks whether the door is open or not, return boolean
	 * @return open
	 */
	public boolean isOn() {
		return isOn;
	}
}
