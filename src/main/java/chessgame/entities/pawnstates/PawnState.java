package chessgame.entities.pawnstates;

/**
 * An abstract class to keep track of the different states, the Enemy "Pawn" can be in
 * 
 * @author Thorgal, Mikal
 *
 */
public abstract class PawnState {
	
	static PawnState PawnIdle, PawnMove, PawnChase, PawnHome, current;
	
	/**
	 * What happens when the pawn enters the state
	 */
	public abstract void Enter();
	/**
	 * What happens in the state, and what triggers it to change state
	 */
	public abstract void Update();
}
	
