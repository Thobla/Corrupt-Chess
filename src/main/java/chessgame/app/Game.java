package chessgame.app;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import chessgame.entities.Pawn;
import chessgame.entities.Enemies;
import chessgame.entities.Player;
import chessgame.utils.CameraStyles;
import chessgame.utils.Constants;
import chessgame.world.PhysicsWorld;
import chessgame.world.TiledGameMap;
import chessgame.utils.EntityManager;
import chessgame.menues.SaveFile;


public class Game implements Screen {
	static int PPM = Constants.PixelPerMeter;
	//Rendering
    OrthographicCamera cam;
    Batch batch;
    
    //TiledMap
    TiledGameMap gameMap;
    TiledMap tiledMap;
    
    //Player
    Player player;
    PlayerController playerController;
    
    //World generation
    PhysicsWorld gameWorld;
    Box2DDebugRenderer debugRenderer;
    
    //Entities
    EntityManager entityManager;
    
    final ChessGame game;
    String map;
    
    //Stage for UI elements
    private Stage stage;
    //Scalable units for size and placements of UI
    int rowHeight = Gdx.graphics.getHeight() / 16;
    int colWidth = Gdx.graphics.getWidth() / 24;
    
    public Game(ChessGame game, String map) {
    	
    	this.game = game;
    	this.map = map;
    	//World initialisation
    	gameWorld = new PhysicsWorld();
    	debugRenderer = new Box2DDebugRenderer();
    	
    	entityManager = new EntityManager(gameWorld);
    	
    	stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        //The camera viewpoint
        cam = new OrthographicCamera(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
        cam.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
        cam.update();
        
		
        //Batch
        batch = new SpriteBatch();
        byte[] controls = SaveFile.readSettings();
        Gdx.input.setInputProcessor(new PlayerController(controls));
        
        //The Map renderer
        gameMap = new TiledGameMap(map);
        
        //Creates the player
        player = new Player(gameMap.getStartPoint(), gameWorld.world);
        
        //Creates bodies for TileMap
        tiledMap = new TmxMapLoader().load(Gdx.files.internal("assets/"+"map"+".tmx").file().getAbsolutePath());
    	gameWorld.tileMapToBody(tiledMap);
    	
    	//Adds a pawn for testing purposes.
    	gameWorld.tileMapToEnemies(tiledMap, entityManager);
    	Pawn pawn = new Pawn(gameMap.getStartPoint().mulAdd(new Vector2(100, 100),1), gameWorld.world, entityManager);
    	
    	
    	//Updates the map
    	entityManager.updateLists();
    	
    	Skin skin = new Skin(Gdx.files.internal("assets/skin/goldenspiralui/golden-ui-skin.json"));
    	//Button for exiting the game
        Button quitButton = new TextButton("Quit",skin,"default");
        quitButton.setSize(colWidth*3,(float) (rowHeight*1.5));
        quitButton.setPosition(colWidth*4,(float) (Gdx.graphics.getHeight()-rowHeight*8.5));
        quitButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(quitButton);
    	
        System.out.print(quitButton.isVisible());
    }

    @Override
    public void dispose() {
    	batch.dispose();
    	gameMap.dispose();
    }

    @Override
    public void render(float delta) {
        
    	//Logic step
    	gameWorld.logicStep(Gdx.graphics.getDeltaTime());
        gameMap.render(cam);
    	//Debug-render
    	debugRenderer.render(gameWorld.world, cam.combined);
    	batch.setProjectionMatrix(cam.combined);
    	
    	batch.begin();
    	player.updatePlayer(batch);
    	for(Enemies enemy : entityManager.enemyList) {
    		enemy.updateState(batch);
    	}
    	entityManager.updateLists();
    	batch.end();
        
    	CameraStyles.lockOnTarget(cam, player.getPosition());
           
        //Camera within bounds
        cameraBounds();
        cam.update();
        
        stage.act();
        stage.draw();
        
        
        if (player.dead) {
        	gameOverScreen();
        }
    }

    public void gameOverScreen() {
    	
        //Imported skin for UI
        Skin skin = new Skin(Gdx.files.internal("assets/skin/goldenspiralui/golden-ui-skin.json"));
    	
        Label gameOverText = new Label("GAME OVER", skin, "title");
        gameOverText.setSize(colWidth*12,rowHeight*2);
        gameOverText.setPosition((float) (Gdx.graphics.getWidth()/2-colWidth*6),rowHeight*12);
        gameOverText.setAlignment(Align.center);
    	stage.addActor(gameOverText);
    	
    	TextButton retryButton = new TextButton ("Retry?", skin, "default");
    	retryButton.setSize(colWidth*3, (float) (rowHeight*1.8));
    	retryButton.setPosition(colWidth*8, rowHeight*8);
    	retryButton.addListener(new InputListener() {
    		@Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    			game.setScreen(new Game(game, map));
    		}
    		
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
    			return true;
    		}	
    	});
    	stage.addActor(retryButton);
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
     * Keeps camera within bounds of the gameMap
     * 
     * @author Mikal
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
    	if(cam.position.x > gameMap.getWidth()-halfCamWidth) {
    		cam.position.x = gameMap.getWidth()-halfCamWidth;
    	}
    	//Checks if the camera is trying to above the map
    	if(cam.position.y <= 0+halfCamHeight) {
    		cam.position.y = 0+halfCamHeight;
    	}
    	//Checks if the camera is trying to go under the map
    	if(cam.position.y > gameMap.getHeight()-halfCamHeight) {
    		cam.position.y = gameMap.getHeight()-halfCamHeight;
    	}
    	//updates camera based on calculation.
    	cam.update();
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
