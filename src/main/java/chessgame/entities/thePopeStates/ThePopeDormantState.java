package chessgame.entities.thePopeStates;

import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Door;
import chessgame.entities.ThePope;
import chessgame.utils.EntityManager;

public class ThePopeDormantState extends ThePopeStates{
	
	ThePope pope;
	float yVal = 0;
	
	public ThePopeDormantState(ThePope pope){
		this.pope = pope;
	}
	
	@Override
	public void Enter() {
	}

	@Override
	public void Update() {
		if(!pope.dormant) {
			pope.myBody.setGravityScale(0);
			if(yVal == 0)
				yVal = pope.getPosition().y;
			if(pope.getPosition().y <= yVal + 5f) {
				pope.getBody().applyLinearImpulse(new Vector2(0,1f), pope.getPosition(), true);
			} else {
				pope.getBody().setLinearVelocity(Vector2.Zero);
				pope.changeState(pope.shootState);
			}
			
		}
		if(pope.getClosestPlayer(3.5f) != null) {
			EntityManager entityManager = pope.entityManager;
			if(entityManager.doorMap.containsKey(0)) {
				Door door = entityManager.doorMap.get(0);
				door.doorState();
			}
			pope.trigger = true;
		}
	}
	
}
