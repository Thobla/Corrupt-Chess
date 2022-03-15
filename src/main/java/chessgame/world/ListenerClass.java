package chessgame.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import chessgame.app.PlayerController;
import chessgame.entities.IEnemies;
import chessgame.entities.IObjects;
import chessgame.entities.Player;

public class ListenerClass implements ContactListener{
	
	private PhysicsWorld world;
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
			System.out.println("Enemy hit");
			player.playerBody.setLinearVelocity(player.playerBody.getLinearVelocity().x, 0);
			player.jump(10000f);
			player.controller.isGrounded = false;
		}
		else if(fixtureB.getUserData() == "weakpoint" && fixtureA.getUserData() == "foot") {
			player = (Player) fixtureA.getBody().getUserData();
			enemy = (IEnemies) fixtureB.getBody().getUserData();
			enemy.takeDamage(player.getAttack());
			System.out.println("Enemy hit");
			player.playerBody.setLinearVelocity(player.playerBody.getLinearVelocity().x, 0);
			player.jump(10000f);
			player.controller.isGrounded = false;
		}
		//Checks if there is an object above the player
		if(fixtureA.getUserData() != null && fixtureB.getUserData() == "sky") {
			System.out.println("Can not Jump!");
			player = (Player) fixtureB.getBody().getUserData();
			player.controller.clearJump = false;
		}
		else if(fixtureB.getUserData() != null && fixtureA.getUserData() == "sky") {
			System.out.println("Can not Jump!");
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
			System.out.println("Can Jump!");
			player = (Player) fixtureA.getBody().getUserData();
			player.controller.clearJump = true;
		}
		if(fixtureB.getUserData() == "sky") {
			System.out.println("Can Jump!");
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
	}
	
	private <T> boolean checkJumpable(T name) {
		return !unjumpable.contains(name);
	}

}
