package chessgame.entities.playerstates;

import chessgame.entities.Player;

public class PlayerPawnState extends PlayerState {
	
	Player player;
	
	public PlayerPawnState(Player player){
		this.player = player;
	}
	
	@Override
	public void Enter() {
		//System.out.println("Entered PawnState");
	}

	@Override
	public void Update() {

	}

	@Override
	public void stateAbility() {
		// TODO Auto-generated method stub
		
	}

}
