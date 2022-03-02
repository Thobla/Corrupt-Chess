package chessgame.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import chessgame.app.PlayerController;
import chessgame.entities.Player;

public class ListenerClass implements ContactListener{
	
	private PhysicsWorld world;
	Player player;
	
	ListenerClass(PhysicsWorld world){
		this.world = world;
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		//checks if the fixture is the "groundCheck-platter" for the player, if it is, we change the players controller isGrounded to true
		if(fixtureA.getUserData() == "foot") {
		 	player = (Player) fixtureA.getBody().getUserData();
		 	player.controller.isGrounded = true;
		}
		else if(fixtureB.getUserData() == "foot") {
			player = (Player) fixtureA.getBody().getUserData();
		 	player.controller.isGrounded = true;
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		//checks if the fixture is the "groundCheck-platter" for the player, if it is, we change the players controller isGrounded to falsew
		if(fixtureA.getUserData() == "foot") {
		 	player = (Player) fixtureA.getBody().getUserData();
		 	player.controller.isGrounded = false;
		}
		if(fixtureB.getUserData() == "foot") {
			player = (Player) fixtureA.getBody().getUserData();
		 	player.controller.isGrounded = false;
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

}
