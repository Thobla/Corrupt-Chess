package chessgame.entities.knightstates;

import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Knight;

public class KnightIdle extends KnightState {
	Knight knight;
	int counter;
	int initialTime = 600;	
	
	public KnightIdle(Knight knight) {
		this.knight = knight;
	}
	
	@Override
	public void Enter() {
		knight.getBody().setLinearVelocity(Vector2.Zero);
		counter = initialTime;
	}
	
	@Override
	public void Update() {
		if(knight.getClosestPlayer(knight.aggroRange) != null) {
			knight.changeState(knight.chaseState);
		}
		if(counter <= 0) {
			counter = initialTime;
			//knight.changeState(knight.homeState);

				
		}
		
		counter -= 1;
	}
}
