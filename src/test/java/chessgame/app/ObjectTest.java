package chessgame.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Button;
import chessgame.entities.Door;
import chessgame.utils.EntityManager;
import chessgame.world.PhysicsWorld;

public class ObjectTest {
	PhysicsWorld pworld = new PhysicsWorld();
	EntityManager manager = new EntityManager(pworld);
		
	@Test
	//Checks that a button alters the state of the door.
	void buttonDoorTest() {
		int code = 1;
		Button button = new Button(Vector2.Zero, pworld.world, manager, code);
		Door door = new Door(Vector2.Zero, pworld.world, manager, code);
		manager.doorMap.put(code, door);
		
		assertEquals(false, door.isOpen());
		button.itemFunction(null);
		assertEquals(true, door.isOpen());
		
		Button falseButton = new Button(Vector2.Zero, pworld.world, manager, 2);
		falseButton.itemFunction(null);
		assertEquals(true, door.isOpen());
	}
}
