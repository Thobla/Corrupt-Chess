package chessgame.entities.playerstates;

import chessgame.entities.IState;
import chessgame.entities.pawnstates.PawnState;

public abstract class PlayerState implements IState {

	static PawnState PlayerPawnState, PlayerKnightState, PlayerBishopState, PlayerTowerState, current;
	
	public abstract void Enter();

	public abstract void Update();

	public abstract void stateAbility();
}
