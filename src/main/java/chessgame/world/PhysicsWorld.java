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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import chessgame.entities.Button;
import chessgame.entities.Door;
import chessgame.entities.Pawn;
import chessgame.entities.Portal;
import chessgame.entities.RatingPoint;
import chessgame.utils.Constants;
import chessgame.utils.EntityManager;

public class PhysicsWorld {
	static int PPM = Constants.PixelPerMeter;
	static float gravity = Constants.Gravity;
	public World world;
	public EntityManager entityManager;
	
	/**
	 * A class to keep track of the Entity world, and ContactListener.
	 * As well as add objects into the world.
	 */
	public PhysicsWorld() {
		world = new World(new Vector2(0, -gravity), true);
		world.setContactListener(new ListenerClass(this));
		entityManager = new EntityManager(this);
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
			body.setUserData("ground");
			
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
	/**
	 * Creates enemies based on the tileMap objects on the enemy layer.
	 * @param tiledMap
	 * @param manager
	 */
	public void tileMapToEntities(TiledMap tiledMap, EntityManager manager) {
		MapObjects entities = tiledMap.getLayers().get("Entities").getObjects();
		
		for(MapObject entity : entities) {
			
			Rectangle rectangle = ((RectangleMapObject)entity).getRectangle();
			Vector2 pos = rectangle.getPosition(new Vector2());
			
			if(entity.getName() == null) {
				break;
			}
			//Spawns a pawn
			if(entity.getName().toLowerCase().equals("pawn")) {;
				Pawn pawn = new Pawn(pos, world, manager);
				pawn.initialize();
			}
			//Spawns a coin	
			if(entity.getName().toLowerCase().equals("ratingpoint")) {
				RatingPoint point = new RatingPoint(pos, world, manager);
				point.initialize();
			}
			//Spawns a door
			if(entity.getName().toLowerCase().equals("door")) {
				int code =(int) entity.getProperties().get("code");
				Door door = new Door(pos, world, manager, code);
				door.initialize();
			}
			if(entity.getName().toLowerCase().equals("button")) {
				int code =(int) entity.getProperties().get("code");
				Button button = new Button(pos, world, manager, code);
				button.initialize();
			}
			if(entity.getName().toLowerCase().equals("portal")) {
				Portal portal = new Portal(pos, world, manager);
				portal.initialize();
			}
			if(entity.getName().toLowerCase().equals("player")) {
				manager.addPlayerSpawn(pos);
			}
			
			
		}
	}
	
}
