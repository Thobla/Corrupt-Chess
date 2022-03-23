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
	
	@Test	
	void getClosesPlayerTest(){
		Pawn pawn = new Pawn(new Vector2(2,2), pworld.world, manager);
		
		Player target = pawn.getClosestPlayer(1000f);
		assertEquals(null, target);
		
		Player mockPlayer = mock(Player.class);
		when(mockPlayer.getPosition()).thenReturn(new Vector2(2,3));
		Player mockPlayer2 = mock(Player.class);
		when(mockPlayer2.getPosition()).thenReturn(new Vector2(50,3));
		
		manager.playerList.add(mockPlayer);
		manager.playerList.add(mockPlayer2);
		
		target = pawn.getClosestPlayer(1000f);
		assertEquals(mockPlayer, target);
		
	}

}
