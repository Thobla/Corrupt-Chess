package chessgame.server;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;

import chessgame.app.Game;
import chessgame.entities.Button;
import chessgame.entities.IEntities;
import chessgame.entities.Pawn;
import chessgame.entities.Player;
import chessgame.server.DataTypes.ButtonData;
import chessgame.server.DataTypes.PawnData;
import chessgame.server.DataTypes.PlayerData;
import chessgame.utils.EntityManager;


/**
 * Class to handle what game is going to do when it resives packets
 * @author thorg
 *
 */
public class NetworkHandler {
	Game game;
	Boolean isHost;
	EntityManager entityManager;
	Vector2 playerPosition;

	public NetworkHandler(Game game) {
		this.game = game;
		this.isHost = game.getIsHost();
		this.entityManager = game.entityManager;
		this.playerPosition = game.getP2Position();
	}
	
	public void handlePacket(Object object) {
		if(object instanceof HashMap) {
			if (!(game.entityManager == null)) {
				if(!isHost) {
					for(IEntities entity: entityManager.entityList) {
						if (entity instanceof Pawn) {
							int pawnId = ((Pawn) entity).getId();
							if(((HashMap) object).containsKey(pawnId)) {
								((Pawn) entity).move(((PawnData) ((HashMap) object).get(pawnId)).getPosition());
								((Pawn)entity).setHealth(((PawnData) ((HashMap) object).get(pawnId)).getHealth());
							}
						}
						if (entity instanceof Button) {
							int buttonId = ((Button) entity).getId();
							if(((HashMap) object).containsKey(buttonId)) {
								
								if(((Button) entity).isActive() != ((ButtonData) ((HashMap) object).get(buttonId)).getActive()) {
									((Button) entity).itemFunction();
								}
							}
						}	
					}
				}
			
			for(IEntities entity : entityManager.playerList) {
				if (entity instanceof Player) {
					System.out.println("isPlayer");
					String playerId = ((Player) entity).getPlayerId();
					if(((HashMap) object).containsKey(playerId)) {
						System.out.println("containsKey");
						if (((Player) entity).getPlayerId().equals("player2") && isHost)
						{
							playerPosition = ((PlayerData) ((HashMap) object).get(playerId)).getPosition();
						}
						else if (((Player) entity).getPlayerId().equals("player1") && !isHost) {
							playerPosition = ((PlayerData) ((HashMap) object).get(playerId)).getPosition();
						}
							
					}
				}
			}
				
			}
			
		}
		
	}
}
