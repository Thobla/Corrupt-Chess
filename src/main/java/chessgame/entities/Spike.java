package chessgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import chessgame.utils.Constants;
import chessgame.utils.EntityManager;

public class Spike implements IObjects {
	
	Vector2 position;
	Sprite sprite;
	Body myBody;
	World world;
	EntityManager entityManager;
	
	float width = 0.5f;
	float height = 0.5f;
	
	public Spike(Vector2 position, World world, EntityManager entityManager, float width){
		this.width = width/(32);
		this.position = new Vector2(position.x/Constants.PixelPerMeter+(this.width/2), position.y/Constants.PixelPerMeter+(height/2));
		this.world = world;
		this.entityManager = entityManager;
	}
	
	@Override
	public void initialize() {
		Texture spikeTexture = new Texture (Gdx.files.internal("assets/spike.png").file().getAbsolutePath());
		Texture portal = new Texture (Gdx.files.internal("assets/portal.png").file().getAbsolutePath());
		spikeTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		TextureRegion imgTextureRegion = new TextureRegion(spikeTexture);
		imgTextureRegion.setRegion(1,3, width,height*8);
		
		sprite = new Sprite(imgTextureRegion);
		
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
		shape.setAsBox(width*0.5f, height*0.5f);
		
		myBody.createFixture(shape, 1000f).setUserData("Object");;
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
			sprite.setPosition(position.x - sprite.getWidth()/2 , position.y-sprite.getHeight()/4);
			sprite.setSize(width, 1);
			sprite.setRotation(0);
			sprite.draw(batch);	
		}
	}

	@Override
	public void itemFunction(Player player) {
		player.kill();
	}
}
