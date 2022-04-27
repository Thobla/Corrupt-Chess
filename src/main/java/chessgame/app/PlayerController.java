package chessgame.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.InputMultiplexer;

import chessgame.entities.Player;
import chessgame.entities.playerstates.PlayerTowerState;

public class PlayerController extends InputMultiplexer {
	
	//Movement
	private int playerspeed = 1;
	private float jumpForce = 500f;
	public boolean isGrounded = false;
	public boolean clearJump = true;
	//Ability
	public boolean lock = false;
	public boolean holdAbility = false;
	public float coolDown = 5;
	public float maxCoolDown;
	//KeyBinds
	private int up;
	private int left;
	private int right;
	private int sprint;
	
	public PlayerController(int[] controls, ChessGame game){
		up = controls[0];
		left = controls[1];
		right = controls[2];
		sprint = controls[3];
	}
	
	public PlayerController(int[] controls){
		up = controls[0];
		left = controls[1];
		right = controls[2];
		sprint = controls[3];
	}
	
	public void myController(Player player) {
		if (!Game.paused) {

			if(Gdx.input.isKeyPressed(sprint))
				player.sprint = true;
			else 
			player.sprint = false;
			
			if(!lock) {
				//Movement inputs
				if(Gdx.input.isKeyPressed(right) && Gdx.input.isKeyPressed(left)) {
					player.move(Vector2.Zero);
				}
		    	if(Gdx.input.isKeyPressed(right)) {
			    	player.move(new Vector2(playerspeed, player.getVelocity().y));
			    	player.facing = true;
		    	}
		    	else if(Gdx.input.isKeyPressed(left)) {
		    		player.move(new Vector2(-playerspeed, player.getVelocity().y));
		    		player.facing = false;
		    	}
		    	else 
		    		player.move(new Vector2(0, player.getVelocity().y));
		
		    	if((Gdx.input.isKeyPressed(up))) {
		    		if(isGrounded && clearJump) {
		    			player.move(Vector2.Zero);
			    		player.jump(jumpForce);
			    		isGrounded = false;
		    		}
		    	}
		    	if(coolDown >= maxCoolDown) {
			    	if(Gdx.input.isKeyJustPressed(Keys.V))
			    		player.nextState();
		    	}
		    	if(!holdAbility) {
			    	if(Gdx.input.isKeyJustPressed(Keys.C))
			    		player.currentState.stateAbility();
		    	}
			}
	    	if(Gdx.input.isKeyPressed(Keys.C) && holdAbility && isGrounded) {
	    		player.currentState.stateAbility();
	    	} else if(holdAbility) {
	    		lock = false;
	    	}
		}
    	if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
    		Game.pauseGame();
    	}
	}
}
