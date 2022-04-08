package chessgame.entities.towerstates;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Tower;

public class TowerIdle extends TowerState{
	Tower tower;
	int dir;
	
	public TowerIdle(Tower tower) {
		this.tower = tower;
	}
	
	@Override
	public void Enter() {
		//Makes it so that upon enter, it walks randomly left or right
		Random rand = new Random();
		dir = rand.nextInt(2);
		tower.setSpeed(3);
		tower.changeDirection();
	}

	@Override
	public void Update() {
		tower.move(new Vector2(dir, 0));
		
		if(tower.getClosestPlayer(1f) != null)
			tower.changeState(tower.chaseState);
	}

}
