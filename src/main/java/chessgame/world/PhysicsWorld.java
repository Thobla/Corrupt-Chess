package chessgame.world;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import chessgame.utils.Constants;

public class PhysicsWorld {
	static int PPM = Constants.PixelPerMeter;
	public World world;
	//The worlds gravity
	Vector2 gravity = new Vector2(0, -9.81f);
	
	public PhysicsWorld() {
		world = new World(gravity, true);
	}
	
	/**
	 * Creates bodies for each "Object" in the TileMaps object-layer.
	 * @param tiledMap
	 * @author Mikal, Thorgal
	 */
	public void tileMapToBody(TiledMap tiledMap) {
		MapObjects objects = tiledMap.getLayers().get("Ground").getObjects();
		
		//Creates a body for each tile in the selected layer
		for(MapObject object : objects) {
			Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
			Vector2 pos = rectangle.getPosition(new Vector2());
			pos.x = (pos.x + rectangle.width/2)/PPM;
			pos.y = (pos.y + rectangle.height/2)/PPM;
			
			//Creates a physics body for object
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyDef.BodyType.StaticBody;

			Body body = world.createBody(bodyDef);
			
			//Shapes the body, and adds friciton
			PolygonShape polygonShape = new PolygonShape();
			polygonShape.setAsBox(1, 1);
			Fixture fixture = body.createFixture(getShapeFromRectangle(rectangle), 10f);
			fixture.setFriction(0);
			
			body.setTransform(pos, 0);
		}
	}
	/**
	 * Creates a shape based on the rectangle given as input
	 * @param rectangle
	 * @return shape
	 * @author Mikal, Thorgal
	 */
	public static Shape getShapeFromRectangle(Rectangle rectangle){
	    PolygonShape polygonShape = new PolygonShape();
	    polygonShape.setAsBox(rectangle.width*0.5F/ PPM,rectangle.height*0.5F/ PPM);
	    return polygonShape;
	}
	/**
	 * Makes the Box2d world move forwards in time
	 * @param delta should always be deltatime
	 * @author Thorgal, Mikal
	 */
	public void logicStep(float delta) {
		world.step(delta, 3, 3);
	}
}
