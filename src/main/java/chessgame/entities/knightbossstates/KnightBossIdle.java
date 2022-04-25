package chessgame.entities.knightbossstates;

import chessgame.entities.KnightBoss;

public class KnightBossIdle extends KnightBossState {
	KnightBoss knight;
	int idleTime = 600;
	int counter;
	
	public KnightBossIdle(KnightBoss knight) {
		this.knight = knight;
	}
	
	@Override
	public void Enter() {
		System.out.println("Entered idle");
		counter = idleTime;
	}
	
	@Override
	public void Update() {
		if (counter <= 0) {
				int r = (int) (Math.random()*2);
			if (r == 1 && !knight.prevState.equals(knight.highJump)) {
				knight.changeState(knight.highJump);
			} 
			if (r == 0) {
				knight.changeState(knight.chaseState);
			}
		} else {
			counter -= 1;
		}
		
	}
}
