package chessgame.entities.thePopeStates;

import java.util.Random;

import com.badlogic.gdx.Gdx;

import chessgame.entities.ThePope;
import chessgame.utils.Direction;

public class ThePopeLavaState extends ThePopeStates {
	ThePope pope;
	float shots;
	float waitTime;
	Random rand = new Random();
	
	public ThePopeLavaState(ThePope pope) {
		this.pope = pope;
	}

	@Override
	public void Enter() {
		waitTime = 0;
		shots = 0;
	}

	@Override
	public void Update() {
		pope.useMagicCircle = false;
		waitTime += Gdx.graphics.getDeltaTime();
		
		if(waitTime > .2f) {
			if(shots < 20) {
				for(Direction dir : Direction.values()) {
					shots++;
					pope.magicShot2(dir);
				}
			} else {
				if(rand.nextInt(5) == 4)
					pope.changeState(pope.shootState);
				else
					pope.changeState(pope.idleState);
			}
			waitTime = 0;
			
		}
	}

}
