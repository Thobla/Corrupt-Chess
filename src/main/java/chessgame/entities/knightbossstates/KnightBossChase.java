package chessgame.entities.knightbossstates;

import com.badlogic.gdx.math.Vector2;

import chessgame.entities.KnightBoss;
import chessgame.entities.Player;

public class KnightBossChase extends KnightBossState{
	KnightBoss knight;
	int totalJumps = 5;
	int jumps;
	
	public KnightBossChase(KnightBoss knight) {
		this.knight = knight;
	}
	
	@Override
	public void Enter() {
		System.out.println("Entered chase");
		jumps = totalJumps + knight.allJumps;
	}
	
	@Override
	public void Update() {
		if (jumps >= knight.allJumps) {
			Player target = knight.getClosestPlayer(10000f);
			if (target != null)
				knight.moveTo(target.getPosition());
		} else {
			knight.changeState(knight.idleState);
		}
		
	}
}
