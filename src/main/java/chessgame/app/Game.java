package chessgame.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import chessgame.entities.Entities;
import chessgame.entities.Player;
import chessgame.utils.CameraStyles;
import chessgame.utils.Constants;
import chessgame.world.B2dModel;
import chessgame.world.GameMap;
import chessgame.world.TiledGameMap;

public class Game implements ApplicationListener {
    OrthographicCamera cam;
    Batch batch;
    GameMap gameMap;
    Player player;
    PlayerController playerController;
    B2dModel model; 
    Body playerBody;
    Box2DDebugRenderer debugRenderer;
    Sprite playerSprite;
    
    
    
    @Override
    public void create() {
    	
    	model = new B2dModel();
    	playerBody = model.playerBody;
    	
    	debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
    	
    	playerSprite = new Sprite(new Texture (Gdx.files.internal("assets/player.png").file().getAbsolutePath()));
    	playerSprite.setSize(2, 2);
    	playerSprite.setOrigin(playerSprite.getWidth()/2, playerSprite.getHeight()/2);
    	playerBody.setUserData(playerSprite);
    	player = new Player(playerSprite , playerBody.getPosition());
    	
    	System.out.println(player.getPosition());
    	System.out.println(playerBody.getPosition());
    	
    	
        //The camera viewpoint
        cam = new OrthographicCamera(32, 24);
        
        cam.position.set(player.getPosition().x / Constants.PPM, player.getPosition().y / Constants.PPM, 0);
        
        //cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.update();
        
      //Get sprite
        
        
        
        
        //gameMap = new TiledGameMap("map");
        
        
        
        batch = new SpriteBatch();
        /**
        
      //PlayerController
    	playerController = new PlayerController();

        
        //Batch
        
        Gdx.input.setInputProcessor(new PlayerController());
        
        
        
        //The Map renderer
       
        
        //Displays the player at the maps start position.
        
        **/
    }

    @Override
    public void dispose() {
    	batch.dispose();
    	gameMap.dispose();
    }

    @Override
    public void render() {
    	//Testing playerController.
    	
    	
    	
    	model.logicStep(Gdx.graphics.getDeltaTime());
    	Gdx.gl.glClearColor(0f, 0f, 0f, 01);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	debugRenderer.render(model.world, cam.combined);
    	
    	batch.setProjectionMatrix(cam.combined);
    	batch.begin();
    	playerSprite = (Sprite) playerBody.getUserData();
    	playerSprite.setPosition(playerBody.getPosition().x - playerSprite.getWidth()/2, playerBody.getPosition().y - playerSprite.getHeight()/2);
    	playerSprite.draw(batch);
    	batch.end();
    	
    	System.out.println(player.getPosition());
    	System.out.println(playerBody.getPosition());

    	
    	//gameMap.render(cam);
    	
    	//playerController.myController(player);

        
        
    	
    	
    	//CameraStyles.lockOnTarget(cam, player.getPosition());
    	
    	
        //cam.position.x += (player.getPosition().x + 20 - cam.position.x) * 0.5 * 4;
        //cam.position.y = player.getPosition().y;
        //cam.update();
   

        
        
        //cam.position.set(player.getSprite().getX(), player.getSprite().getY(), 0);
        //cam.update();
        
        //Camera within bounds
        //cameraBounds();
    	
    	/**
    	
        **/
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
