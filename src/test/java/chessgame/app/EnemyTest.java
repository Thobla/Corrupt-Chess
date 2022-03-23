package chessgame.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Pawn;
import chessgame.entities.Player;
import chessgame.utils.EntityManager;
import chessgame.world.PhysicsWorld;

public class EnemyTest {
	PhysicsWorld pworld = new PhysicsWorld();
	EntityManager manager = new EntityManager(pworld);
	
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
	
	@Test
	void changeStateTest() {
		Pawn pawn = new Pawn(new Vector2(2,2), pworld.world, manager);
		
	}
}
