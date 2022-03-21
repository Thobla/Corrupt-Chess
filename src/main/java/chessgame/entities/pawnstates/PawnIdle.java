package chessgame.entities.pawnstates;

import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Pawn;

public class PawnIdle extends PawnState {
	Pawn pawn;
		
	public PawnIdle(Pawn pawn) {
		this.pawn = pawn;
	}
		
		
	@Override
	public void Enter() {
		pawn.getBody().setLinearVelocity(Vector2.Zero);
		System.out.println("Pawn in idle.");
	}

	@Override
	public void Update() {
			
	}
}
