package chessgame.entities.knightbossstates;

import com.badlogic.gdx.math.Vector2;

import chessgame.entities.KnightBoss;

public class KnightBossRandom extends KnightBossState {
	KnightBoss knight;
	float xVal;
	int jumps;
	
	public KnightBossRandom(KnightBoss knight) {
		this.knight = knight;
	}
	
	@Override
	public void Enter() {
		jumps = (int) Math.random()*2+4 + knight.allJumps;
	}
	
	@Override
	public void Update() {
		if (jumps >= knight.allJumps) {
			if (knight.lookingRight)
				xVal = (float) (Math.random()*1.5f+10f+knight.getPosition().x);
			else
				xVal = (float) (-Math.random()*1.5f-10f+knight.getPosition().x);
			
			Vector2 target = new Vector2(xVal,-100f);
			knight.moveTo(target);
		} else
			knight.changeState(knight.idleState);
	}
}
