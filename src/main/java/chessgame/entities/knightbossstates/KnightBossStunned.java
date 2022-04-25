package chessgame.entities.knightbossstates;

import chessgame.entities.KnightBoss;

public class KnightBossStunned extends KnightBossState {
	KnightBoss knight;
	int counter;
	int initialTime = 2000;
	
	public KnightBossStunned(KnightBoss knight) {
		this.knight = knight;
	}
	
	@Override
	public void Enter() {
		System.out.println("Enetered stunned");
		counter = initialTime;
	}
	
	@Override
	public void Update() {
		if (counter <= 0 || knight.hit) {
			knight.changeState(knight.idleState);
		}
		counter -= 1;
	}
	
}
