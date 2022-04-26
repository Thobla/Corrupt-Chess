package chessgame.entities.knightbossstates;

import chessgame.entities.KnightBoss;

public class KnightBossIdle extends KnightBossState {
	KnightBoss knight;
	int idleTime = 800;
	int counter;
	int riggedRandom;
	int dec;
	
	public KnightBossIdle(KnightBoss knight) {
		this.knight = knight;
		dec = 1;
	}
	
	@Override
	public void Enter() {
		counter = idleTime;
		riggedRandom = (int) (Math.random()*dec);
		if (knight.getHealth() == 1) {
			counter = counter/4;
			dec = 4;
		} else
			dec -= 2;
	}
	
	@Override
	public void Update() {
		if (counter <= 0) {	
			if (riggedRandom == 0 && !knight.prevState.equals(knight.stunnedState)) {
				if (knight.getHealth() > 1) {
				knight.changeState(knight.superJumpState);
				dec = 10;
				} else
					knight.changeState(knight.omegaState);
			} else {
				int r = (int) (Math.random()*3);
				if (r == 0) {
					knight.changeState(knight.chaseState);
				}
				if (r > 1) {
					knight.changeState(knight.randState);
				}
			}	
		}
		counter -= 1;
	}
}
