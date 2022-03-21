package chessgame.entities.pawnstates;

import chessgame.entities.Pawn;
import chessgame.entities.Player;

public class PawnChase extends PawnState{
	Pawn pawn;
	
	public PawnChase(Pawn pawn) {
		this.pawn = pawn;
	}
		
		
	@Override
	public void Enter() {
			System.out.println("Pawn is now angry! >:(");
	}

	@Override
	public void Update() {
		Player target = pawn.getClosestPlayer(pawn.aggroRange + 2f);
		//checks whether to stay in state or change state
		if(target == null) {
			pawn.changeState(pawn.idleState);
		}
		else {
			pawn.moveTo(target.getPosition());	
		}
	}
}
