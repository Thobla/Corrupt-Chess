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

import chessgame.utils.EntityManager;

public class Pawn implements Enemies {
	int health = 2;
	int attack;
	
	Vector2 position;
	World world;
	Body myBody;
	EntityManager entityManager;
	Sprite sprite;
	
	//Entity size
	float width = 0.5f;
	float height = 0.5f;
	
	public Pawn (Vector2 position, World world, EntityManager entityManager) {
		this.position = new Vector2(position.x/32, position.y/32);
		this.world = world;
		this.entityManager = entityManager;
		
		//if(Gdx.files.internal("assets/badguy.png").file()!= null)
		sprite = new Sprite(new Texture (Gdx.files.internal("assets/badguy.png").file().getAbsolutePath()));
		
		createBody();
		//sets the userData as a pointer to the pawn (this is used for collisionchecking)
		myBody.setUserData(this);
		
		//Adds the pawn to the entityManager
    	entityManager.addEnemy(this);
	}
	
	@Override
	public void createBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(position.x, position.y));
		
		myBody = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		myBody.createFixture(shape, 1000f);
		myBody.setFixedRotation(true);
		myBody.setUserData("Pawn");
		
		//creating a fixture that will serve as the players groundCheck-platter.
		FixtureDef fixDef = new FixtureDef();
		fixDef.isSensor = true;
		//the shape should be lower than the players width and height
		shape.setAsBox(width * 0.95f, height / 10, new Vector2(0f, height), 0);
		fixDef.shape = shape;
		
		myBody.createFixture(fixDef).setUserData("weakpoint");
		
	}
	
	public void removeBody() {
		world.destroyBody(myBody);
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
	public int getHealth() {
		return health;
	}

	@Override
	public void takeDamage(int damage) {
		if(damage <= health)
			health -= damage;
	}

	@Override
	public int getAttack() {
		return attack;
	}

	@Override
	public void moveTo(Vector2 target) {
		
	}

	@Override
	public void updateState(Batch batch) {
		position = myBody.getPosition();
		if(batch != null) {
			sprite.setPosition(position.x - sprite.getWidth()/2 , position.y - sprite.getHeight()/2);
			sprite.setSize(1, 1);
			sprite.draw(batch);	
		}
		if(health <= 0)
			kill();
	}

	@Override
	public void kill() {
		entityManager.removeEnemy(this);
	}

	@Override
	public Body getBody() {
		return myBody;
	}

	@Override
	public void keepWithinBounds() {
		// TODO Auto-generated method stub
		
	}
}
