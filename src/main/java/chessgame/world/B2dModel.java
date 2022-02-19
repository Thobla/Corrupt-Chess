package chessgame.world;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import chessgame.entities.BodyFactory;
public class B2dModel {
	public World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private Body bodyd;
	private Body bodys;
	private Body bodyk;
	
	public B2dModel() {
		world = new World(new Vector2(0, -9.81f), true);
		createFloor();
		createObject();
		createMovingObject();
		BodyFactory bodyFactory = BodyFactory.getInstance(world);
		
		bodyFactory.makeCirclePolyBody(1,1,2, BodyFactory.RUBBER, BodyType.DynamicBody,false);
		bodyFactory.makeCirclePolyBody(4,1,2, BodyFactory.STEEL, BodyType.DynamicBody,false);
		bodyFactory.makeCirclePolyBody(-4,1,2, BodyFactory.STONE, BodyType.DynamicBody,false);
		
	}
	
	public void logicStep(float delta) {
		world.step(delta,  3, 3);
	}
	
	private void createObject() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(0,0);
		
		bodyd = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1, 1);
		
		
		
		bodyd.createFixture(shape, 0.0f);
		
		shape.dispose();
		
	}
	private void createFloor() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0,-10);
		
		bodys = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50, 1);
		
		bodys.createFixture(shape, 0.0f);
		
		bodys.createFixture(shape, 0.0f);
		
		shape.dispose();
		
	}
	private void createMovingObject() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.KinematicBody;
		bodyDef.position.set(0,-12);
		
		bodyk = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1, 1);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;
		
		bodyk.createFixture(shape, 0.0f);
		
		shape.dispose();
		
		bodyk.setLinearVelocity(0, 0.75f);
		
	}
	
	
	
	
	
	
}
