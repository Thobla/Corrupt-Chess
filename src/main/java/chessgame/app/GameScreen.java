package chessgame.app;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import chessgame.entities.Player;
import chessgame.utils.CameraStyles;
import chessgame.world.GameMap;
import chessgame.world.TiledGameMap;



public class GameScreen implements Screen {
	final ChessGame game;
	
	
	OrthographicCamera cam;
    Batch batch;
    GameMap gameMap;
    Player player;
    PlayerController playerController;

    public GameScreen(final ChessGame game, String map) {
    	this.game = game;
    	
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
        gameMap = new TiledGameMap(map);
        
        //Displays the player at the maps start position.
        player = new Player(playerSprite , gameMap.getStartPoint());
    }


	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		//Testing playerController.
    	playerController.myController(player);

        gameMap.render(cam);
        
    	batch.begin();
    	player.getSprite().setPosition(player.getPosition().x - cam.position.x+gameMap.getStartPoint().x, player.getPosition().y - cam.position.y+gameMap.getStartPoint().y);
    	player.getSprite().draw(batch);
    	batch.end();
    	
    	CameraStyles.lockOnTarget(cam, player.getPosition());
    	
    	
//        cam.position.x += (player.getPosition().x + 20 - cam.position.x) * 0.5 * 4;
//        cam.position.y = player.getPosition().y;
//        cam.update();
   

        
        
//        cam.position.set(player.getSprite().getX(), player.getSprite().getY(), 0);
//        cam.update();
        
        //Camera within bounds
        cameraBounds();
        
        if(Gdx.input.isKeyPressed(Keys.ENTER)) {
			game.setScreen(new GameScreen(game,"map2"));
			dispose();
		}


	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		batch.dispose();
	    gameMap.dispose();
	}

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
