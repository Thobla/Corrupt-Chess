package chessgame.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import chessgame.entities.Player;
import chessgame.world.GameMap;
import chessgame.world.TiledGameMap;

public class Game implements ApplicationListener {
    OrthographicCamera cam;
    Batch batch;
    GameMap gameMap;
    Player player;
    PlayerController playerController;
    @Override
    public void create() {
    	
    	//PlayerController
    	playerController = new PlayerController();

        //The camera viewpoint
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.update();
        
        //Batch
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(new PlayerController());
        
        //Get sprite
        Sprite playerSprite = new Sprite(new Texture (Gdx.files.internal("assets/player.png").file().getAbsolutePath()));
        
        //The Map renderer
        gameMap = new TiledGameMap("map");
        
        //Displays the player at the maps start position.
        player = new Player(playerSprite , gameMap.getStartPoint());
    }

    @Override
    public void dispose() {
    	batch.dispose();
       gameMap.dispose();
    }

    @Override
    public void render() {
    	//Testing playerController.
    	playerController.myController(player);

        gameMap.render(cam);
        
    	batch.begin();
    	player.getSprite().draw(batch);
    	player.getSprite().setPosition(player.getPosition().x, player.getPosition().y);
    	batch.end();
    	
        cam.position.set(player.getSprite().getX(), player.getSprite().getY(), 0);
        cam.update();
        
        //Camera within bounds
        cameraBounds();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    
    /**
     * Keeps camera within bounds
     */
    public void cameraBounds() {
    	//Half of the camera x view
    	float halfCamWidth = cam.viewportWidth/2;
    	//Half of the camera y view
    	float halfCamHeight = cam.viewportHeight/2;

    	//Checks if the camera is trying to go left of the map
    	if(cam.position.x <= 0+halfCamWidth) {
    		cam.position.x = 0+halfCamWidth;
    	}
    	//Checks if the camera is trying to go right of the map
    	if(cam.position.x > gameMap.getWidthPixels()-halfCamWidth) {
    		cam.position.x = gameMap.getWidthPixels()-halfCamWidth;
    	}
    	//Checks if the camera is trying to above the map
    	if(cam.position.y <= 0+halfCamHeight) {
    		cam.position.y = 0+halfCamHeight;
    	}
    	//Checks if the camera is trying to go under the map
    	if(cam.position.y > gameMap.getHeightPixels()-halfCamHeight) {
    		cam.position.y = gameMap.getHeightPixels()-halfCamHeight;
    	}
    	//updates camera based on calculation.
    	cam.update();
    }
}
