package chessgame.entities.towerstates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Tower;

public class TowerRest extends TowerState{
	Tower tower;
	long enterTime;
	long blinkTime;
	boolean blink = false;
	
	public TowerRest(Tower tower) {
		this.tower = tower;
	}
	
	@Override
	public void Enter() {
		enterTime = System.currentTimeMillis();
		blinkTime = enterTime;
		tower.getBody().setLinearVelocity(Vector2.Zero);
	}

	@Override
	public void Update() {
		if(System.currentTimeMillis() > enterTime + 3000) {
			tower.getSprite().setColor(Color.WHITE);
			blink = false;
			tower.changeState(tower.idleState);	
		}
		if(System.currentTimeMillis() > blinkTime + 300) {
			blinkTime = System.currentTimeMillis();
			if(!blink) {
				tower.getSprite().setColor(Color.YELLOW);
				blink = true;
			} else {
				tower.getSprite().setColor(Color.WHITE);
				blink = false;
			}
				
		}
	}

}
