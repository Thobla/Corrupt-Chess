package chessgame.entities.knightstates;

import chessgame.entities.Knight;
import chessgame.entities.Player;

public class KnightChase extends KnightState{
	Knight knight;
	
	public KnightChase(Knight knight) {
		this.knight = knight;
	}
	
	@Override
	public void Enter() {
		knight.setSprite("assets/enemies/BigKnight.png");
		if (knight.lookingRight)
			knight.getSprite().flip(true, false);
	}
	
	@Override
	public void Update() {
		Player target = knight.getClosestPlayer(knight.aggroRange+1f);
		
		//checks whether to stay in state or change state
		if(target == null) {
			knight.changeState(knight.idleState);
		}
		else {
			knight.moveTo(target.getPosition());	
		}
	}
}
