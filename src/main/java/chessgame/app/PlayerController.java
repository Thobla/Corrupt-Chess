package chessgame.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.InputMultiplexer;

import chessgame.entities.Player;

public class PlayerController extends InputMultiplexer {
	
	public void myController(Player player) {
    	if(Gdx.input.isKeyPressed(Keys.D))
    		player.move(new Vector2(1,0));
    	if(Gdx.input.isKeyPressed(Keys.A))
    		player.move(new Vector2(-1,0));
    	if(Gdx.input.isKeyPressed(Keys.W))
    		player.move(new Vector2(0,1));
    	if(Gdx.input.isKeyPressed(Keys.S))
    		player.move(new Vector2(0,-1));
	}
}
