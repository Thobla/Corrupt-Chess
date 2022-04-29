package chessgame.entities.thePopeStates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;

import chessgame.entities.ThePope;

public class ThePopeIdleState extends ThePopeStates {
	
	float waitTime;
	Random rand = new Random();
	List<ThePopeStates> attacks = new ArrayList<ThePopeStates>();
	ThePope pope;
	
	public ThePopeIdleState(ThePope pope) {
		this.pope = pope;
	}

	@Override
	public void Enter() {
		waitTime = 0;
		attacks.add(pope.bowserState);
		attacks.add(pope.spiralState);
		attacks.add(pope.shootState);
		attacks.add(pope.lavaState);
	}

	@Override
	public void Update() {
		waitTime += Gdx.graphics.getDeltaTime();
		
		if(waitTime > 3f) {
			pope.changeState(attacks.get(rand.nextInt(attacks.size())));
			waitTime = 0;
		}
	}

}
