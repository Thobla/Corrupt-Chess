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
	 * [3]=left
	 */
	public void myController(Player player, byte[] controls) {
    	if(Gdx.input.isKeyPressed(controls[3]))
    		player.move(new Vector2(3,0));
    	if(Gdx.input.isKeyPressed(controls[1]))
    		player.move(new Vector2(-3,0));
    	if(Gdx.input.isKeyPressed(controls[0]))
    		player.move(new Vector2(0,3));
    	if(Gdx.input.isKeyPressed(controls[2]))
    		player.move(new Vector2(0,-3));
	}
}
