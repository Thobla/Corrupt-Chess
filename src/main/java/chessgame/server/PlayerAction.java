package chessgame.server;

import java.util.HashMap;

import chessgame.entities.IEntities;
import chessgame.entities.Player;
import chessgame.server.DataTypes.PlayerData;
import chessgame.utils.EntityManager;

public class PlayerAction {
	public HashMap<String, PlayerData> playerList= new HashMap<>();
	
	public PlayerAction(EntityManager entityManager) {
		for(IEntities entity : entityManager.playerList) {
			if (entity instanceof Player) {
				if(((Player) entity).getPlayerId().equals("player2"))
					playerList.put(((Player) entity).getPlayerId(), new PlayerData(((Player) entity).getHealth(), entity.getPosition()));
			}
		}
	}
}
