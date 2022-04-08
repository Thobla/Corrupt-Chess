package chessgame.entities.towerstates;

import chessgame.entities.IState;

public abstract class TowerState implements IState{
	
	static TowerState TowerIdle, TowerChase, TowerCharge, current;
	
	public abstract void Enter();

	public abstract void Update();
}
