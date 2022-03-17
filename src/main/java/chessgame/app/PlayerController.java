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
	 * [2]=right
	 * [3]=sprint
	 */
	private int playerspeed;
	private float jumpForce = 10000f;
	public boolean isGrounded = false;
	public boolean clearJump = true;
	
	private byte up;
	private byte left;
	private byte right;
	private byte sprint;
	
	public PlayerController(byte[] controls, ChessGame game){
		up = controls[0];
		left = controls[1];
		right = controls[2];
		sprint = controls[3];
	}
	
	public PlayerController(byte[] controls){
		up = controls[0];
		left = controls[1];
		right = controls[2];
		sprint = controls[3];
	}
	
	
	public void myController(Player player) {
		if (!Game.paused) {
			//checks if the player is on the ground
			
			if(Gdx.input.isKeyPressed(sprint))
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
	
	    	if((Gdx.input.isKeyPressed(up) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.SPACE))) {
	    		if(isGrounded && clearJump) {
		    		player.jump(jumpForce);
		    		isGrounded = false;
	    		}
	    	}
		}
    	if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
    		Game.pauseGame();
    	}
	}
}
