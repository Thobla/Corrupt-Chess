package chessgame.utils;

import java.util.ArrayList;
import java.util.List;

import chessgame.entities.Enemies;
import chessgame.world.PhysicsWorld;


/**
 * A class that keep track of the entities (enemies) that exists in the world.
 * The EntityManager also helps remove enemies from the world.
 */
public class EntityManager {
	//Lists to keep track of the entity / enemy creation and removal.
    public List<Enemies> enemyList = new ArrayList<Enemies>();
    public List<Enemies> enemyRemoveList = new ArrayList<Enemies>();
    
    private PhysicsWorld pworld;
    
    public EntityManager(PhysicsWorld gameWorld) {
    	this.pworld = gameWorld;
    }
    
    /**
     * Adds the enemy to the removeList.
     * @param enemy
     */
    public void removeEnemy(Enemies enemy) {
    	if(!enemyRemoveList.contains(enemy))
    		enemyRemoveList.add(enemy);
    }
    /**
     * Adds the enemy to the enemyList.
     * @param enemy
     * @author mikal, thorgal
     */
    public void addEnemy(Enemies enemy) {
    	if(!enemyList.contains(enemy))
    		enemyList.add(enemy);
    }
    
    /**
     * updates the enemyAddList and enemyRemoveList to add and remove the enemies from the world
     * 
     * @author mikal, thorgal
     */
    public void updateLists() {
    	for(Enemies enemy : enemyRemoveList) {
    		if(enemyList.contains(enemy)) {
    			enemyList.remove(enemy);
	    		pworld.world.destroyBody(enemy.getBody());
    		}
    	}
    	enemyRemoveList.clear();
    }
}
