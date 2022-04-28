package chessgame.server;

import java.util.HashMap;
import java.util.List;

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
	private Vector2 playerPosition;
	private HashMap<Integer, PawnData> pawnMap = new HashMap<>();
	private List<IEntities> removeList;
	
	public void alterP2Position(Game game) {
		if(playerPosition != null)
			game.player2.setPosition(playerPosition);
	}
	public void alterPawnPositions(Game game) {
		if(pawnMap != null) {
			for(IEntities entity: game.entityManager.entityList) {
				if (entity instanceof Pawn) {
					int pawnId = ((Pawn) entity).getId();
					if(pawnMap.containsKey(pawnId)) {
						entity.move(pawnMap.get(pawnId).getPosition());
					}
				}
			}
		}
	}
	
	public void syncDeaths(Game game) {
		if(removeList != null)
			game.entityManager.updateLists(removeList);
	}
	
	//methods to be handled before step
	public void preStep(Game game) {
		alterP2Position(game);
		alterPawnPositions(game);
	}
	//methods to handle before step
	public void postStep(Game game) {
		syncDeaths(game);
	}
	
	
	
	//method to decide what to do 
	public void handlePacket(Object object, Game game) {
		if(object instanceof List) {
			if(!(game.entityManager == null)) {
				if(!game.getIsHost()) {
					this.removeList = (List<IEntities>) object;
				}
			}
		}
		if(object instanceof HashMap) {
			if (!(game.entityManager == null)) {
				if(!game.getIsHost()) {
					for(IEntities entity: game.entityManager.entityList) {
						
						//What to do with the pawn data
						if (entity instanceof Pawn) {
							int pawnId = ((Pawn) entity).getId();
							if(((HashMap) object).containsKey(pawnId)) {
								if(pawnMap.containsKey(pawnId))
									pawnMap.replace(pawnId, ((PawnData) ((HashMap) object).get(pawnId)));
								else
									pawnMap.put(pawnId, ((PawnData) ((HashMap) object).get(pawnId)));
								
								//((Pawn) entity).move(((PawnData) ((HashMap) object).get(pawnId)).getPosition());
								//((Pawn)entity).setHealth(((PawnData) ((HashMap) object).get(pawnId)).getHealth());
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
			
			for(IEntities entity : game.entityManager.playerList) {
				if (entity instanceof Player) {
					String playerId = ((Player) entity).getPlayerId();
					if(((HashMap) object).containsKey(playerId)) {
						if (((Player) entity).getPlayerId().equals("player2") && game.getIsHost())
						{
							playerPosition = ((PlayerData) ((HashMap) object).get(playerId)).getPosition();
						}
						else if (((Player) entity).getPlayerId().equals("player1") && !game.getIsHost()) {
							playerPosition = ((PlayerData) ((HashMap) object).get(playerId)).getPosition();
						}
							
					}
				}
			}
				
			}
			
		}
		if(object instanceof PausePing) {
			if(Game.paused && !((PausePing) object).setToPaused) {
				Game.setPause = true;
			}
			else if(!Game.paused && ((PausePing) object).setToPaused) {
				Game.setPause = true;
			}
			else {
				Game.setPause = false;
			}
		}
		
	}
}
