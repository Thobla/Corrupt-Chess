package chessgame.entities.knightbossstates;

import chessgame.entities.IState;

public abstract class KnightBossState implements IState {

	static KnightBossState KnightBossIdle, KnightBossHighJump, KnightBossStunned, KnightBossChase;
	
	public void Enter() {}

	public void Update() {}

}
