package chessgame.entities.playerstates;

import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Bullet;
import chessgame.utils.Direction;
import chessgame.entities.Player;

public class PlayerBishopState extends PlayerState {
	
	Player player;
	
	public PlayerBishopState(Player player) {
		this.player = player;
	}
	
	@Override
	public void Enter() {
		System.out.println("Entered BishopState");
	}

	@Override
	public void Update() {
		
	}

	@Override
	public void stateAbility() {
		Vector2 spawnPosition;
		Direction dir;
		if(player.facing) {
			spawnPosition = new Vector2(player.getPosition().x + 1, player.getPosition().y);
			dir = Direction.RIGHT;
		}else {
			spawnPosition = new Vector2(player.getPosition().x - 1, player.getPosition().y);
			dir = Direction.LEFT;
		}
		
		Bullet b = new Bullet(spawnPosition, player.world, player.manager, dir);
		b.initialize();
		b.setBulletSpeed(20f);
	}

}
