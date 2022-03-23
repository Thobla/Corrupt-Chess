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

public class Door implements IObjects {
	
	Vector2 position;
	Sprite sprite;
	Body myBody;
	World world;
	EntityManager entityManager;
	
	Sprite spriteOpen;
	Sprite spriteClosed;
	
	Boolean open;
	int activationCode;
	
	float width = 0.5f;
	float height = 1f;
	
	
	public Door(Vector2 position, World world, EntityManager entityManager, int code){
		this.position = new Vector2(position.x/Constants.PixelPerMeter+width, position.y/Constants.PixelPerMeter+height);
		this.world = world;
		this.entityManager = entityManager;
		this.activationCode = code;
		
		spriteOpen = new Sprite(new Texture (Gdx.files.internal("assets/dooropen.png").file().getAbsolutePath()));
		spriteClosed = new Sprite(new Texture (Gdx.files.internal("assets/doorclosed.png").file().getAbsolutePath()));
		sprite = spriteClosed;
		
		open = false;
		createBody();
		
		entityManager.addEntity(this);
		entityManager.doorMap.put(activationCode, this);
	}
	
	@Override
	public void createBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(new Vector2(position.x, position.y));
		
		myBody = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		myBody.createFixture(shape, 1000f).setUserData("Door");;
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
		
		if(batch != null) {
			sprite.setPosition(position.x - sprite.getWidth()/2 , position.y-sprite.getHeight()/2);
			sprite.setSize(1, 2);
			sprite.draw(batch);	
		}
		
	}
	
	public void doorState() {
		if(!open) {
			myBody.getFixtureList().get(0).setSensor(true);
			sprite = spriteOpen;
			open = true;
		} else {
			myBody.getFixtureList().get(0).setSensor(false);
			sprite = spriteClosed;
			open = false;
		}
	}

	@Override
	public void itemFunction(Player player) {
		// TODO Auto-generated method stub
		
	}

}
