package chessgame.entities.playerstates;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Player;

public class PlayerKnightState extends PlayerState {

	Player player;
	boolean hasJumped = false;
	boolean hasCharged = false;
	boolean charging = false;
	float charge = 0;
	float jumpTime = 0;
	
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
		if(!player.controller.lock && hasCharged) {
			jumpTime += Gdx.graphics.getDeltaTime();
			player.jump(500f);
			player.controller.isGrounded = false;
		}
		if(jumpTime > 0.05f * charge) {
			hasCharged = false;
			jumpTime = 0;
			charge = 0;
		}
	}

	@Override
	public void stateAbility() {
		if(charge < 3) {
			charge += Gdx.graphics.getDeltaTime()*2;
			player.controller.lock = true;
			player.myBody.setLinearVelocity(Vector2.Zero);
			System.out.println(charge);
			hasCharged = true;
		} else {
			charge = 3;
			hasCharged = true;
		}
	}
}
