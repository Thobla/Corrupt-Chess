package chessgame.entities.playerstates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import chessgame.entities.Player;

public class PlayerPawnState extends PlayerState {
	
	Player player;
	
	public PlayerPawnState(Player player){
		this.player = player;
	}
	
	@Override
	public void Enter() {
		Texture pawnSprite = new Texture (Gdx.files.internal("assets/player/player.png").file().getAbsolutePath());
		player.getSprite().setTexture(pawnSprite);
		player.controller.holdAbility = false;
	}

	@Override
	public void Update() {

	}

	@Override
	public void stateAbility() {
		// TODO Auto-generated method stub
		
	}

}
