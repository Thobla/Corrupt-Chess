package chessgame.entities.thePopeStates;

import java.util.Random;

import com.badlogic.gdx.Gdx;

import chessgame.entities.ThePope;
import chessgame.utils.Direction;

public class ThePopeSpiralState extends ThePopeStates {
	ThePope pope;
	
	float waitTime;
	float degree = 0;
	boolean left = true;
	Random rand = new Random();
	
	public ThePopeSpiralState(ThePope pope) {
		this.pope = pope;
	}

	@Override
	public void Enter() {
		waitTime = 0;
		degree = 0;
		left = true;
	}

	@Override
	public void Update() {
		pope.useMagicCircle = false;
		waitTime += Gdx.graphics.getDeltaTime();
		if(degree == 30) {
			left = false;
			if(rand.nextInt(6) == 3) {
				pope.changeState(pope.shootState);
			}
		}
		if(waitTime > 0.1f && degree >= 0) {
			if(left)
				degree += 0.5f;
			else
				degree -= 0.5f;
			pope.magicShot3(degree);
			waitTime = 0;
		} else if(!left && degree == 0) {
			pope.changeState(pope.idleState);
		}
	}

}
