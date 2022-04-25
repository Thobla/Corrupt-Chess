package chessgame.entities.knightbossstates;

import com.badlogic.gdx.math.Vector2;

import chessgame.entities.KnightBoss;

public class KnightBossDormant extends KnightBossState {
	KnightBoss knight;
	
	public KnightBossDormant(KnightBoss knight) {
		this.knight = knight;
	}
	
	@Override
	public void Enter() {
		knight.getBody().setLinearVelocity(Vector2.Zero);
	}
	
	@Override
	public void Update() {
		if (knight.getClosestPlayer(knight.aggroRange) != null)
			knight.activated = true;
		if (knight.activated) {
			if (knight.isGrounded())
				knight.changeState(knight.idleState);
		} else
			knight.getBody().setLinearVelocity(Vector2.Zero);
	}
}
