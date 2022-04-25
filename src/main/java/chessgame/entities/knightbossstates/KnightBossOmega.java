package chessgame.entities.knightbossstates;

import chessgame.entities.KnightBoss;

public class KnightBossOmega extends KnightBossState{
	KnightBoss knight;
	int jumps;
	float power;
	
	public KnightBossOmega(KnightBoss knight) {
		this.knight = knight;
	}
	
	@Override
	public void Enter() {
		jumps = 6 + knight.allJumps;
	}
	
	@Override
	public void Update() {
		if (jumps >= knight.allJumps) {
			power = 20f+(jumps-knight.allJumps)*5f;
			knight.superJump(power, true);
		} else
			knight.changeState(knight.superJumpState);
	}
}
