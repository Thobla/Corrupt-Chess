package chessgame.server;

import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import chessgame.server.DataTypes.*;
import chessgame.app.Game;
import chessgame.entities.*;
import chessgame.utils.EntityManager;









/**
 * this is the packet class, which keeps controll of the data that should be sent
 * @author thorg
 *
 */
public class Packet {
	private EntityManager entityManager;
	
	public HashMap<Integer, PawnData> pawnList;
	public HashMap<Integer, DoorData> doorList;
	public HashMap<Integer, ButtonData> buttonList;
	public HashMap<String, PlayerData> playerList;
	
	public List<IEntities> removeList;
	
	public Packet(EntityManager entityManager){
		this.entityManager = entityManager;
		removeList = entityManager.entityRemoveList;
		addAllEntities();
	}
	
	void addAllEntities() {
		for(IEntities entity : entityManager.entityList) {
			if (entity instanceof Pawn) {
				System.out.println("stage 1");
				pawnList.put(((Pawn) entity).getId(), new PawnData(((Pawn) entity).getHealth(), entity.getPosition()));
			}
			if (entity instanceof Player) {
				System.out.println("stage 2");
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
