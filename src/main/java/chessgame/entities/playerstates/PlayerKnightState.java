package chessgame.entities.playerstates;

import chessgame.entities.Player;

public class PlayerKnightState extends PlayerState {

	Player player;
	
	public PlayerKnightState(Player player){
		this.player = player;
	}
	
	@Override
	public void Enter() {
		System.out.println("Entered KnightState");
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateAbility() {
		
	}

}
