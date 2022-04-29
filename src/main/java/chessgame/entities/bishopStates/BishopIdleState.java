package chessgame.entities.bishopStates;

import com.badlogic.gdx.Gdx;

import chessgame.entities.Bishop;

public class BishopIdleState extends BishopState{
	
	Bishop bishop;
	float waitTime;
	
	public BishopIdleState(Bishop bishop){
		this.bishop = bishop;
	}
	
	@Override
	public void Enter() {
		waitTime = 0;
		bishop.getSprite().setTexture(bishop.bishopTex);
	}

	@Override
	public void Update() {
		waitTime += Gdx.graphics.getDeltaTime();
		
		if(bishop.getClosestPlayer(10f) != null)
			if(waitTime > 1f) {
				bishop.changeState(bishop.bishopFireState);
			}
	}

}
