package chessgame.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Button;
import chessgame.entities.Door;
import chessgame.entities.Player;
import chessgame.entities.RatingPoint;
import chessgame.utils.EntityManager;
import chessgame.world.PhysicsWorld;

public class ObjectTest {
	PhysicsWorld pworld;
	EntityManager manager;
	
	@BeforeEach
	void setup() {
		pworld = new PhysicsWorld();
		manager = new EntityManager(pworld);
	}
	
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
	
	@Test
	void playerIncreaseRatingTest() {
		Player player = new Player(new Vector2(0,0), pworld.world, manager);
		EntityManager mockManager = mock(EntityManager.class);
		RatingPoint rating = new RatingPoint(new Vector2(0,0), pworld.world, mockManager);
		
		assertEquals(player.getScore(), 0);
		rating.itemFunction(player);
		assertTrue(player.getScore() > 0);
	}
}
