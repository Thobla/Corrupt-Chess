package chessgame.entities.playerstates;

import chessgame.entities.Player;

public class PlayerBishopState extends PlayerState {
	
	Player player;
	
	public PlayerBishopState(Player player) {
		this.player = player;
	}
	
	@Override
	public void Enter() {
		System.out.println("Entered BishopState");
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateAbility() {
		// TODO Auto-generated method stub
		
	}

}
