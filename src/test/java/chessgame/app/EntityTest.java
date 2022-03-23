package chessgame.app;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Pawn;
import chessgame.entities.Player;
import chessgame.utils.EntityManager;
import chessgame.world.PhysicsWorld;

@ExtendWith(MockitoExtension.class)
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

}
