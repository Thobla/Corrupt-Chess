package chessgame.server;

import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import chessgame.app.Game;
import chessgame.entities.*;
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

class DoorData {
	Boolean open;
	
	DoorData(Boolean open){
		this.open = open;
	}
	
	Boolean getOpen() {
		return this.open;
	}
}

class ButtonData {
	Boolean active;
	
	ButtonData(Boolean active) {
		this.active = active;
	}
	
	Boolean getActive() {
		return this.active;
	}
}

/**
 * this is the packet class, which keeps controll of the data that should be sent
 * @author thorg
 *
 */
public class Packet {
	private EntityManager entityManager;
	
	HashMap<Integer, PawnData> pawnList;
	HashMap<Integer, DoorData> doorList;
	HashMap<Integer, ButtonData> buttonList;
	HashMap<String, PlayerData> playerList;
	
	List<IEntities> removeList = entityManager.entityRemoveList;
	
	Packet(EntityManager entityManager){
		this.entityManager = entityManager;
		addAllEntities();
	}
	
	void addAllEntities() {
		for(IEntities entity : entityManager.entityList) {
			if (entity instanceof Pawn) {
				pawnList.put(((Pawn) entity).getId(), new PawnData(((Pawn) entity).getHealth(), entity.getPosition()));
			}
			if (entity instanceof Player) {
				playerList.put(((Player) entity).getId(), new PlayerData(((Player) entity).getHealth(), entity.getPosition()));
			}
			if (entity instanceof Door) {
				doorList.put(((Door) entity).getId(), new DoorData(((Door) entity).isOpen()));
			}
			if (entity instanceof Button) {
				buttonList.put(((Button) entity).getId(), new ButtonData(((Button) entity).isActive()));
			}	
		}
	}
	
}
