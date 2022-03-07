package chessgame.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Pawn;
import chessgame.utils.EntityManager;
import chessgame.world.PhysicsWorld;

public class EntityTest {
	
	PhysicsWorld pworld = new PhysicsWorld();
	EntityManager manager = new EntityManager(pworld);
	
	@BeforeAll
	static void setUpBeforeAll() {
	}

	/**
	 * Setup method called before each of the test methods
	 */
	@BeforeEach
	void setUpBeforeEach() {
	}
	
	@Test
	void enemySpawnTest() {
		Pawn pawn = new Pawn(Vector2.Zero, pworld.world, manager);
		
		assertTrue(manager.enemyList.contains(pawn));
		assertEquals(pworld.world.getBodyCount(), 1);
	}
	@Test
	void enemyDeathTest() {
		Pawn pawn = new Pawn(Vector2.Zero, pworld.world, manager);
		
		assertTrue(manager.enemyList.contains(pawn));
		assertEquals(pworld.world.getBodyCount(), 1);
		
		pawn.takeDamage(pawn.getHealth());
		assertEquals(pawn.getHealth(), 0);
		pawn.updateState(null);
		manager.updateLists();
		
		assertFalse(manager.enemyList.contains(pawn));
		assertEquals(pworld.world.getBodyCount(), 0);
	}
}
