package chessgame.server;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

import chessgame.app.Game;
import chessgame.entities.IEntities;
import chessgame.entities.Pawn;
import chessgame.entities.Player;
import chessgame.utils.EntityManager;

/**
 * This class decides what data ,considering each pawn entity, should be sent.
 * @author thorg
 *
 */
class PawnData {
	int health;
	Vector2 position;
	
	PawnData(int health, Vector2 position){
		this.health = health;
		this.position = position;
	}
	
	int getHealth() {
		return this.health;
	}
	
	Vector2 getPosition() {
		return this.position;
	}
}

/**
 * This class decides what data ,considering each player, should be sent.
 * @author thorg
 */
class PlayerData {
	int health;
	Vector2 position;
	
	PlayerData(int health, Vector2 position){
		this.health = health;
		this.position = position;
	}
	
	int getHealth() {
		return this.health;
	}
	
	Vector2 getPosition() {
		return this.position;
	}
}

/**
 * this is the packet class, which keeps controll of the data that should be sent
 * @author thorg
 *
 */
public class Packet {
	private Game game;
	private EntityManager entityManager = game.entityManager;
	
	HashMap<Integer, PawnData> pawnList;
	HashMap<String, PlayerData> playerList;
	
	Packet(Game game){
		this.game = game;
	}
	
	void addAllEntities() {
		for(IEntities entity : entityManager.entityList) {
			if (entity instanceof Pawn) {
				pawnList.put(((Pawn) entity).getId(), new PawnData(((Pawn) entity).getHealth(), entity.getPosition()));
			}
			if (entity instanceof Player) {
				playerList.put(((Player) entity).getId(), new PlayerData(((Player) entity).getHealth(), entity.getPosition()));
			}
			
			//må vite hvilke entities som skal fjernes, må snedes før removelist blir tømt
			for(IEntities removeEntity : entityManager.entityRemoveList) {
				
			}
			
		}
	}
	
}
