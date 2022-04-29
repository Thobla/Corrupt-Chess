package chessgame.entities.thePopeStates;

import com.badlogic.gdx.Gdx;

import chessgame.entities.ThePope;

public class ThePopeBowserState extends ThePopeStates {
	ThePope pope;
	float waitTime;
	public ThePopeBowserState(ThePope pope) {
		this.pope = pope;
	}

	@Override
	public void Enter() {
		pope.useMagicCircle = false;
		pope.magicLock = false;
		pope.finishMagicCircle = true;
		pope.magicCircleAttack(pope.magicRoations);
		waitTime = 0;
	}

	@Override
	public void Update() {
		waitTime += Gdx.graphics.getDeltaTime();
		if(waitTime > 3f) {
			if(pope.finishMagicCircle && pope.magicLock)
				pope.changeState(pope.idleState);
		}
	}

}
