package chessgame.entities.towerstates;

import chessgame.entities.Tower;

public class TowerChase extends TowerState{
	
	Tower tower;
	long chaseStart;
	
	public TowerChase(Tower tower) {
		this.tower = tower;
	}
	
	@Override
	public void Enter() {
		tower.setSpeed(10);
		chaseStart = System.currentTimeMillis();
	}

	@Override
	public void Update() {
		if(tower.getClosestPlayer(1f) != null) {
			float playerX = tower.getClosestPlayer(1f).getPosition().x;
			if(playerX - tower.getPosition().x > 0) {
				tower.setDirection(1);
			} else {
				tower.setDirection(0);
			}
			//moves the tower towards player
			tower.move(tower.getClosestPlayer(1f).getPosition());
			
		}
		if(System.currentTimeMillis() > chaseStart + 2000) {
			tower.changeState(tower.restState);	
		}
	}

}
