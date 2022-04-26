package chessgame.entities.playerstates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import chessgame.entities.Player;

public class PlayerKnightState extends PlayerState {

	Player player;
	
	public PlayerKnightState(Player player){
		this.player = player;
	}
	
	@Override
	public void Enter() {
		Texture knightSprite = new Texture (Gdx.files.internal("assets/player/Knight.png").file().getAbsolutePath());
		player.getSprite().setTexture(knightSprite);
		player.controller.holdAbility = true;
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateAbility() {
		player.controller.lock = true;
	}

}
