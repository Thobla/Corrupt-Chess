package chessgame.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.InputMultiplexer;

import chessgame.entities.Player;

public class PlayerController extends InputMultiplexer {
	
	private int playerspeed;
	private float jumpForce = 3000f;
	public boolean isGrounded;
	
	
	public void myController(Player player) {
		//checks if the player is on the ground
		isGrounded = true;
		
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			playerspeed = 16;
		else 
			playerspeed = 8;
		
		//Movement inputs
    	if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
    		player.move(new Vector2(playerspeed, player.getVelocity().y));
    	else if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
    		player.move(new Vector2(-playerspeed, player.getVelocity().y));
    	else 
    		player.move(new Vector2(0, player.getVelocity().y));
    	if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.SPACE))
    		player.jump(jumpForce);
	}
}
