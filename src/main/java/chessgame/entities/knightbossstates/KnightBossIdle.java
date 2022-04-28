package chessgame.entities.knightbossstates;

import chessgame.entities.KnightBoss;

public class KnightBossIdle extends KnightBossState {
	KnightBoss knight;
	int idleTime = 300;
	int counter;
	int riggedRandom;
	int dec;
	boolean lastStand;
	
	public KnightBossIdle(KnightBoss knight) {
		this.knight = knight;
		dec = 10;
	}
	
	@Override
	public void Enter() {
		if (knight.getHealth() == 1) {
			if (!lastStand) {
				dec = 5;
				lastStand = true;
				counter = idleTime *2;
			} else {
				dec -= 1;
				counter = idleTime/2;
			}
			if (dec <= 0) {
				riggedRandom = 0;
			} else {
				riggedRandom = 1;
			}
		} else {
			riggedRandom = (int) (Math.random()*dec);
			dec -= 2;
			counter = idleTime;
		}
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
		} else if (knight.isGrounded())
				counter -= 1;
	}
}
