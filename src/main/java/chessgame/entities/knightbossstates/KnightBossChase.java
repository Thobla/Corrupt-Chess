package chessgame.entities.knightbossstates;

import chessgame.entities.KnightBoss;
import chessgame.entities.Player;

public class KnightBossChase extends KnightBossState{
	KnightBoss knight;
	int jumps;
	
	public KnightBossChase(KnightBoss knight) {
		this.knight = knight;
	}
	
	@Override
	public void Enter() {
		jumps = (int) Math.random()*2+3 + knight.allJumps;;
	}
	
	@Override
	public void Update() {
		if (jumps >= knight.allJumps) {
			Player target = knight.getClosestPlayer(10000f);
			if (target != null) {
				knight.moveTo(target.getPosition());
				if(target.getPosition().x-0.1f < knight.getPosition().x && knight.getPosition().x < target.getPosition().x+0.1f) {
					knight.getBody().setLinearVelocity(0, knight.getBody().getLinearVelocity().y);
				}
			}
		} else {
			knight.changeState(knight.idleState);
		}
		
	}
}
