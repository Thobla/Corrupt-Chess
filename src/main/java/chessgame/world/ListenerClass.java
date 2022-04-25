package chessgame.world;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.parser.Entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import chessgame.app.PlayerController;
import chessgame.entities.IEnemies;
import chessgame.entities.IEntities;
import chessgame.entities.IObjects;
import chessgame.entities.Knight;
import chessgame.entities.KnightBoss;
import chessgame.entities.Player;
import chessgame.entities.Portal;
import chessgame.utils.EntityManager;

public class ListenerClass implements ContactListener{
	
	private PhysicsWorld world;
	EntityManager manager;
	Player player;
	IEnemies enemy;
	
	private List<String> unjumpable = new ArrayList<String>();
	
	ListenerClass(PhysicsWorld world){
		prepareJumpable();
		this.world = world;
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		//checks if the fixture is the "groundCheck-platter" for the player, if it is, we change the players controller isGrounded to true
		if(fixtureA.getUserData() == "foot" && checkJumpable(fixtureB.getUserData())) {
		 	player = (Player) fixtureA.getBody().getUserData();
		 	player.controller.isGrounded = true;
		}
		else if(fixtureB.getUserData() == "foot" && checkJumpable(fixtureA.getUserData())) {
			player = (Player) fixtureB.getBody().getUserData();
		 	player.controller.isGrounded = true;
		}
		//checks if the fixture is a enemy weakpoint
		if(fixtureA.getUserData() == "weakpoint" && fixtureB.getUserData() == "foot") {
			player = (Player) fixtureB.getBody().getUserData();
		 	enemy = (IEnemies) fixtureA.getBody().getUserData();
		 	enemy.takeDamage(player.getAttack());
			player.myBody.setLinearVelocity(player.myBody.getLinearVelocity().x, 0);
			player.jump(10000f);
			player.controller.isGrounded = false;
		}
		else if(fixtureB.getUserData() == "weakpoint" && fixtureA.getUserData() == "foot") {
			player = (Player) fixtureA.getBody().getUserData();
			enemy = (IEnemies) fixtureB.getBody().getUserData();
			enemy.takeDamage(player.getAttack());
			player.myBody.setLinearVelocity(player.myBody.getLinearVelocity().x, 0);
			player.jump(10000f);
			player.controller.isGrounded = false;
		}
		//Checks if there is an object above the player
		if(fixtureA.getUserData() != "air" && fixtureB.getUserData() == "sky") {
			player = (Player) fixtureB.getBody().getUserData();
			player.controller.clearJump = false;
		}
		else if(fixtureB.getUserData() != "air" && fixtureA.getUserData() == "sky") {
			player = (Player) fixtureA.getBody().getUserData();
			player.controller.clearJump = false;
		}
		//Checks if the player is colliding with an object
		if(fixtureA.getUserData() == "Object" && fixtureB.getUserData() == "Player") {
			IObjects object = (IObjects) fixtureA.getBody().getUserData();
			player = (Player) fixtureB.getBody().getUserData();
			object.itemFunction(player);
		}
		else if(fixtureB.getUserData() == "Object" && fixtureA.getUserData() == "Player") {
			IObjects object = (IObjects) fixtureB.getBody().getUserData();
			player = (Player) fixtureA.getBody().getUserData();
			object.itemFunction(player);
		}
		//TODO make an invulnerability system or something. to prevent player from,
		//dying when jumping on enemy.
		
		//Checks whether the player interacts with an enemy, if so, take damage.
		if(fixtureA.getUserData() == "Enemy" && fixtureB.getUserData() == "Player") {
			IEnemies enemy = (IEnemies) fixtureA.getBody().getUserData();
			player = (Player) fixtureB.getBody().getUserData();
			
			//Calulates angle to launch player after damage
			Vector2 playerPos = player.getPosition();
			Vector2 enemyPos = enemy.getPosition();
			Vector2 test = new Vector2(Math.abs(playerPos.x-enemyPos.x), Math.abs(playerPos.y-enemyPos.y));
			
			player.myBody.applyForceToCenter(new Vector2(5000f * test.x, 5000f * test.y), true);
			player.takeDamage(enemy.getAttack());
		}
		else if(fixtureB.getUserData() == "Enemy" && fixtureA.getUserData() == "Player") {
			IEnemies enemy = (IEnemies) fixtureB.getBody().getUserData();
			player = (Player) fixtureA.getBody().getUserData();
			
			//Calulates angle to launch player after damage
			Vector2 playerPos = player.getPosition();
			Vector2 enemyPos = enemy.getPosition();
			
			Vector2 test = new Vector2(Math.abs(playerPos.x-enemyPos.x), Math.abs(playerPos.y-enemyPos.y));
			
			player.myBody.applyForceToCenter(new Vector2(5000f * test.x, 5000f * test.y), true);
			player.takeDamage(enemy.getAttack());
		}
		//Checks if player touches the Portal
		if(fixtureA.getUserData() == "Portal" && fixtureB.getUserData() == "Player") {
			player = (Player) fixtureB.getBody().getUserData();
			world.entityManager.removePlayer(player);
			Portal.victory();
		}
		else if(fixtureB.getUserData() == "Portal" && fixtureA.getUserData() == "Player") {
			player = (Player) fixtureA.getBody().getUserData();
			world.entityManager.removePlayer(player);
			Portal.victory();
		}
		
		//checks if an entities jump-sensor is hitting any non-entity objects, making sure
		//it differenciates leftJumpSensor from rightJunpSensor depending on linearvelocity.
		//start
		if((fixtureA.getUserData() == "rightJumpSensor" || fixtureA.getUserData() == "leftJumpSensor") && (fixtureB.getBody().getUserData() == "ground")) {
			if (fixtureA.getUserData() == "rightJumpSensor" || fixtureA.getUserData() == "rightJumpSensor2" && fixtureA.getBody().getLinearVelocity().x > 0) {
				IEnemies enemy = (IEnemies) fixtureA.getBody().getUserData();
				System.out.println("sensor");
				enemy.jump();
			}
			else if(fixtureA.getUserData() == "leftJumpSensor" && fixtureA.getBody().getLinearVelocity().x < 0) {
				IEnemies enemy = (IEnemies) fixtureA.getBody().getUserData();
				System.out.println("sensor");
				enemy.jump();
			}
			
		}

		else if((fixtureB.getUserData() == "rightJumpSensor" || fixtureB.getUserData() == "leftJumpSensor") && (fixtureA.getBody().getUserData() == "ground")) {
			if (fixtureB.getUserData() == "rightJumpSensor" && fixtureB.getBody().getLinearVelocity().x > 0) {
				IEnemies enemy = (IEnemies) fixtureB.getBody().getUserData();
				enemy.jump();
			}
			else if(fixtureB.getUserData() == "leftJumpSensor" && fixtureB.getBody().getLinearVelocity().x < 0) {
				IEnemies enemy = (IEnemies) fixtureB.getBody().getUserData();
				enemy.jump();
			}
			
		}
		
		//Checks if the Knight enemy has landed
		if (fixtureA.getUserData() == "hoof" && checkJumpable(fixtureB.getUserData())) {
			if (fixtureA.getBody().getUserData().getClass().equals(Knight.class)) {
				Knight knight = (Knight) fixtureA.getBody().getUserData();
				knight.grounded();
			}
			if (fixtureA.getBody().getUserData().getClass().equals(KnightBoss.class)) {
				KnightBoss knight = (KnightBoss) fixtureA.getBody().getUserData();
				knight.grounded();
			}
			
		} else if (fixtureB.getUserData() == "hoof" && checkJumpable(fixtureA.getUserData())) {
			if (fixtureB.getBody().getUserData().getClass().equals(Knight.class)) {
				Knight knight = (Knight) fixtureB.getBody().getUserData();
				knight.grounded();
			} 
			if (fixtureB.getBody().getUserData().getClass().equals(KnightBoss.class)) {
				KnightBoss knight = (KnightBoss) fixtureB.getBody().getUserData();
				knight.grounded();
			}
		}
		//Checks if the Knight landed on the player or an other enemy
		if (fixtureA.getUserData() == "hoof" && fixtureB.getUserData() == "Player" || fixtureA.getUserData() == "hoof" && fixtureB.getUserData() == "weakpoint") {
			if (fixtureA.getBody().getUserData().getClass().equals(Knight.class)) {
				Knight knight = (Knight) fixtureA.getBody().getUserData();
				knight.jump();
				IEntities entity = (IEntities) fixtureB.getBody().getUserData();
				entity.takeDamage(knight.getAttack());
			} else {
				KnightBoss knight = (KnightBoss) fixtureA.getBody().getUserData();
				knight.jump();
				IEntities entity = (IEntities) fixtureB.getBody().getUserData();
				entity.takeDamage(knight.getAttack());
			}
		} else if (fixtureB.getUserData() == "hoof" && fixtureA.getUserData() == "Player" || fixtureB.getUserData() == "hoof" && fixtureA.getUserData() == "weakpoint" && fixtureB.getUserData() == "hoof" && fixtureA.getUserData() == "sky") {
			if (fixtureB.getBody().getUserData().getClass().equals(Knight.class)) {
				Knight knight = (Knight) fixtureB.getBody().getUserData();
				knight.jump();
				IEntities entity = (IEntities) fixtureA.getBody().getUserData();
				entity.takeDamage(knight.getAttack());
			} else {
				KnightBoss knight = (KnightBoss) fixtureB.getBody().getUserData();
				knight.jump();
				IEntities entity = (IEntities) fixtureA.getBody().getUserData();
				entity.takeDamage(knight.getAttack());
			}
			
		}
		
		//end
		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		//checks if the fixture is the "groundCheck-platter" for the player, if it is, we change the players controller isGrounded to falsew
		if(fixtureA.getUserData() == "foot") {
		 	//player = (Player) fixtureA.getBody().getUserData();
		 	//player.controller.isGrounded = false;
		}
		if(fixtureB.getUserData() == "foot") {
			//player = (Player) fixtureA.getBody().getUserData();
		 	//player.controller.isGrounded = false;
		}
		if(fixtureA.getUserData() == "sky") {
			player = (Player) fixtureA.getBody().getUserData();
			player.controller.clearJump = true;
		}
		if(fixtureB.getUserData() == "sky") {
			player = (Player) fixtureB.getBody().getUserData();
			player.controller.clearJump = true;
		}
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	private void prepareJumpable() {
		unjumpable.add("Object");
		unjumpable.add("Enemy");
		unjumpable.add("Portal");
		unjumpable.add("rightJumpSensor");
		unjumpable.add("leftJumpSensor");
	}
	
	private <T> boolean checkJumpable(T name) {
		return !unjumpable.contains(name);
	}

}
