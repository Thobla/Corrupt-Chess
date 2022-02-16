package chessgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player implements Entities{
	Vector2 position;
	Sprite sprite = new Sprite(new Texture (Gdx.files.internal("assets/player.png").file().getAbsolutePath()));;
	
	public Player (Sprite sprite, Vector2 position) {
		this.position = position;
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public void move() {
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}
	
}
