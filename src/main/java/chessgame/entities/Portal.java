package chessgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import chessgame.app.Game;
import chessgame.utils.Constants;
import chessgame.utils.EntityManager;

public class Portal implements IEntities {
	Vector2 position;
	World world;
	EntityManager entityManager;
	Body myBody;
	Sprite sprite;
	
	float width = 0.5f;
	float height = 0.5f;
	
	//ParticleTesting
	TextureAtlas particleAtlas;
	ParticleEffect particle = new ParticleEffect();
	
	public Portal(Vector2 position, World world, EntityManager entityManager){
		this.position = new Vector2(position.x/Constants.PixelPerMeter+width, position.y/Constants.PixelPerMeter+height);
		this.world = world;
		this.entityManager = entityManager;
	}
	public void initialize() {
		sprite = new Sprite(new Texture (Gdx.files.internal("assets/objects/portal.png").file().getAbsolutePath()));
		createBody();
		entityManager.addEntity(this);
	}
	
	@Override
	public void createBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(new Vector2(position.x, position.y));
		
		myBody = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.isSensor = true;
		shape.setAsBox(width * 0.95f, height / 10, new Vector2(0f, height), 0);
		fixDef.shape = shape;
		
		myBody.createFixture(fixDef).setUserData("Portal");
		myBody.setFixedRotation(true);
		myBody.setUserData(this);
	}
	
	public static void victory() {
		Game.victoryScreen();
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public void move(Vector2 newPos) {
		//Redundant for a portal to move
		//Unless we want it to be elusive :O
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
		//Portal does not need to get removed
	}

	@Override
	public void removeBody() {
		//Does not need to remove body, as it laods a new map when touched.
	}

	@Override
	public void updateState(Batch batch) {
		position = myBody.getPosition();
		if(batch != null) {
			sprite.setPosition(position.x - sprite.getWidth()/2 , position.y - sprite.getHeight()/2);
			sprite.setSize(1, 1);
			sprite.draw(batch);	
		}
	}
	
}
