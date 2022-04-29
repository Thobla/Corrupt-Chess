package chessgame.entities.thePopeStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import chessgame.entities.ThePope;
import chessgame.utils.Direction;

public class ThePopeShootState extends ThePopeStates{
	ThePope pope;
	float waitTime;
	float restTime;
	int bulletAmount;
	boolean movementLock;
	boolean returnAir;
	float yVal = 0;
	
	public ThePopeShootState(ThePope pope) {
		this.pope = pope;
	}

	@Override
	public void Enter() {
		movementLock = false;
		returnAir = false;
		waitTime = 0;
		restTime = 0;
		bulletAmount = pope.bullets;
		
		pope.magicShot();
		bulletAmount--;
	}

	@Override
	public void Update() {
		pope.useMagicCircle = false;
		waitTime += Gdx.graphics.getDeltaTime();
		restTime += Gdx.graphics.getDeltaTime();
		
		if(waitTime > 1f && bulletAmount > 0) {
			pope.magicShot();
			pope.magicShot2(Direction.DOWN);
			waitTime = 0;
			bulletAmount--;
		}
		else if (returnAir) {
			if(yVal == 0)
				yVal = pope.getPosition().y;
			if(pope.getPosition().y <= yVal) {
				pope.getBody().applyLinearImpulse(new Vector2(0,1f), pope.getPosition(), true);
			} else {
				pope.getBody().setLinearVelocity(Vector2.Zero);
				pope.changeState(pope.idleState);
			}
		}
		else if(bulletAmount <= 0) {
			int hp = 3;
			//play tired animation
			if(yVal == 0) {
				yVal = pope.myBody.getPosition().y;
				hp = pope.getHealth();
			}
			if(!movementLock) {
				pope.myBody.setLinearVelocity(0, -5f);
				restTime = 0;
			}
			if(pope.myBody.getPosition().y <= yVal -5f && !returnAir) {
				movementLock = true;
				pope.myBody.setLinearVelocity(Vector2.Zero);
				if(restTime >= 3 || pope.getHealth() < hp) {
					returnAir = true;
				}
			}
		}
		
	}

}
