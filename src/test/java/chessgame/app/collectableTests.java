package chessgame.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;


import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Player;
import chessgame.entities.RatingPoint;
import chessgame.utils.EntityManager;
import chessgame.world.PhysicsWorld;

/**
 * tests for all the collectable items in the game
 * @author thorgal
 *
 */
class collectableTests {

	
	PhysicsWorld pWorld;
	Player player;
	RatingPoint rating;
	
	@BeforeEach
	void setup() {
		EntityManager mockManager = mock(EntityManager.class);
		pWorld = new PhysicsWorld();
		player = new Player(new Vector2(0,0), pWorld.world);
		rating = new RatingPoint(new Vector2(0,0), pWorld.world, mockManager);
	}
	
	/**
	 * Tests that the players rating increases when rating's itemFunction(player) is called
	 */
	@Test
	void playerIncreaseRatingTest() {
		assertEquals(player.getScore(), 0);
		rating.itemFunction(player);
		assertTrue(player.getScore() > 0);
	}

}
