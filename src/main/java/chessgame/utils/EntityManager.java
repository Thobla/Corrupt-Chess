package chessgame.utils;

import java.util.ArrayList;
import java.util.List;

import chessgame.entities.IEntities;
import chessgame.entities.Player;
import chessgame.world.PhysicsWorld;


/**
 * A class that keep track of the entities (enemies) that exists in the world.
 * The EntityManager also helps remove enemies from the world.
 */
public class EntityManager {
	//Lists to keep track of the entity / enemy creation and removal.
	//TODO is linkedlist here more effective??
    public List<IEntities> entityList = new ArrayList<IEntities>();
    public List<IEntities> entityRemoveList = new ArrayList<IEntities>();
    public List<Player> playerList = new ArrayList<Player>();
    
    private PhysicsWorld pworld;
    
    public EntityManager(PhysicsWorld gameWorld) {
    	this.pworld = gameWorld;
    }
    
    /**
     * Adds the entity to the removeList.
     * @param entity
     */
    public void removeEntity(IEntities entity) {
    	if(!entityRemoveList.contains(entity))
    		entityRemoveList.add(entity);
    }
    /**
     * Adds the enemy to the enemyList.
     * @param enemy
     * @author mikal, thorgal
     */
    public void addEntity(IEntities entity) {
    	if(!entityList.contains(entity))
    		entityList.add(entity);
    }
    
    /**
     * updates the enemyAddList and enemyRemoveList to add and remove the enemies from the world
     * 
     * @author mikal, thorgal
     */
    public void updateLists() {
    	for(IEntities entity : entityRemoveList) {
    		if(entityList.contains(entity)) {
    			entityList.remove(entity);
	    		pworld.world.destroyBody(entity.getBody());
    		}
    	}
    	entityRemoveList.clear();
    }
}
