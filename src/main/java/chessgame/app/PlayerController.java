package chessgame.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.InputMultiplexer;

import chessgame.entities.Player;

public class PlayerController extends InputMultiplexer {
	
	/**
	 * [0]=up
	 * [1]=left
	 * [2]=down
	 * [3]=right
	 */
	private int playerspeed;
	private float jumpForce = 10000f;
	public boolean isGrounded = false;
	
	private byte up;
	private byte left;
	private byte down;
	private byte right;
	
	public PlayerController(byte[] controls){
		up = controls[0];
		left = controls[1];
		down = controls[2];
		right = controls[3];	
	}
	
	
	public void myController(Player player) {
		//checks if the player is on the ground
		
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			playerspeed = 16;
		else 
			playerspeed = 8;
		
		//Movement inputs
    	if(Gdx.input.isKeyPressed(right) || Gdx.input.isKeyPressed(Keys.RIGHT))
    		player.move(new Vector2(playerspeed, player.getVelocity().y));
    	else if(Gdx.input.isKeyPressed(left) || Gdx.input.isKeyPressed(Keys.LEFT))
    		player.move(new Vector2(-playerspeed, player.getVelocity().y));
    	else 
    		player.move(new Vector2(0, player.getVelocity().y));
    	if((Gdx.input.isKeyPressed(up) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.SPACE)) && isGrounded) {
    		player.jump(jumpForce);
    		isGrounded = false;
    	}
	}
}
