package chessgame.app;

import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import chessgame.entities.IEntities;
import chessgame.entities.Player;
import chessgame.utils.CameraStyles;
import chessgame.utils.Constants;
import chessgame.world.PhysicsWorld;
import chessgame.world.TiledGameMap;
import chessgame.utils.EntityManager;
import chessgame.utils.SaveFile;
import chessgame.utils.ScreenType;
import chessgame.utils.UI;
import chessgame.menues.MenuScreen;


public class Game implements Screen {
	static int PPM = Constants.PixelPerMeter;
	//Rendering
    OrthographicCamera cam;
    Batch batch;
    
    //TiledMap
    TiledGameMap gameMap;
    TiledMap tiledMap;
    
    //Player
    static Player player;
    PlayerController playerController;
    
    //World generation
    PhysicsWorld gameWorld;
    Box2DDebugRenderer debugRenderer;
    
    //Entities
    EntityManager entityManager;
    
    final ChessGame game;
    String map;
    
    static int currentLevelIndex;
    //All levels with their file names
    public static String[] levels = new String[] {
    	"1-1",
    	"1-2",
    	"1-3"
    };
    
    //Stage for UI elements
    private static Stage stage;
    //Scalable units for size and placements of UI
    static int rowHeight = Gdx.graphics.getHeight() / 16;
    static int colWidth = Gdx.graphics.getWidth() / 24;
    //Imported skin for UI
    static Skin skin = new Skin(Gdx.files.internal("assets/skin/chess/chess.json"));
    //UI
    static Label pauseText;
    static TextButton resumeButton; 
    static Label gameOverText;
    static TextButton retryButton;
    static TextButton quitButtonGO;
    static TextButton quitButtonP;
    static Label healthText;
    static Label scoreText;
    static Label timerText;
    static float timer;
    static Label victoryText;
    static TextButton continueButton;
    
    static boolean paused;
    static boolean dead;
    
    
    public Game(ChessGame game, int level) {
    	this.game = game;
    	currentLevelIndex = level;
    	if(currentLevelIndex >= levels.length) 
    		return;
    	this.map = levels[level];
    	
    	//World initialisation
    	gameWorld = new PhysicsWorld();
    	//debugRenderer = new Box2DDebugRenderer();
    	
    	entityManager = gameWorld.entityManager;
    	
    	//The stage for UI elements
    	stage = new Stage(new ScreenViewport());
     
        //The camera viewpoint
        cam = new OrthographicCamera(Gdx.graphics.getWidth() / (PPM*1.5f), Gdx.graphics.getHeight() / (PPM*1.5f));
        cam.setToOrtho(false, Gdx.graphics.getWidth() / (PPM*1.5f), Gdx.graphics.getHeight() / (PPM*1.5f));
        cam.update();
		
        //Batch
        batch = new SpriteBatch();
        int[] controls = SaveFile.readSettings();
        Gdx.input.setInputProcessor(new PlayerController(controls, game));
        //stage inputet må væra etter playercontroller for å fungere?
        Gdx.input.setInputProcessor(stage);
        
        //The Map renderer
        gameMap = new TiledGameMap(map);
        
        //Creates bodies for TileMap
        tiledMap = new TmxMapLoader().load(Gdx.files.internal("assets/levels/"+map+".tmx").file().getAbsolutePath());
    	gameWorld.tileMapToBody(tiledMap);
    	gameWorld.tileMapToEntities(tiledMap, entityManager);
    	
    	//Creates the player
     	player = entityManager.addPlayer();
     	
    	//Updates the map
    	entityManager.updateLists();
    	

    	initilizeUI();
    	
    	timer = 300;
    	stage.addActor(timerText);
    	stage.addActor(healthText);
    	stage.addActor(scoreText);
    	paused = false;
    	

    }

    @Override
    public void dispose() {
    	batch.dispose();
    	gameMap.dispose();
    	stage.dispose();
    }

    @Override
    public void render(float delta) {
    	if(currentLevelIndex >= levels.length) {
    		game.setScreen(new MenuScreen(game));
    		return;
    	}
        if (!paused) {
	        //Logic step
	    	gameWorld.logicStep(Gdx.graphics.getDeltaTime());
	        gameMap.render(cam);
	    	
	        /**Debug-render to be off when not debugging.
	    	debugRenderer.render(gameWorld.world, cam.combined);
	    	*/
	        
	    	batch.setProjectionMatrix(cam.combined);
	    	
	    	
	    	//Updates all entities
	    	batch.begin();
	    	entityManager.updateEntities(batch);
	    	entityManager.updatePlayers(batch);
	    	batch.end();
	    	
	    	entityManager.updateLists();
	    	
	    	//UI updates
	    	healthText.setText("Health: " + player.getHealth());
	    	scoreText.setText("Score: " + player.getScore());
	           
	        //Camera Updates
	    	CameraStyles.lockOnTarget(cam, player.getPosition());
	    	if (timer <= 0) {
	    		paused = true;
	    		gameOverScreen();
	    	} else {
	    		timer = timer - delta;
		    	int time = (int) timer;
		    	timerText.setText(time);
	    	}
	        cameraBounds();
	        cam.update();
	        
	        stage.act();
	        stage.draw();
	        
	        
	        //Player Value updates
	        if (player.dead) {
	        	gameOverScreen();
	        	entityManager.removePlayer(player);
	        }
        }
        else {
        	gameMap.render(cam);
        	
        	/**Debug-render to be off when not debugging.
	    	
	    	*/
        	//debugRenderer.render(gameWorld.world, cam.combined);
        	
	    	batch.setProjectionMatrix(cam.combined);
        	
	    	//Updates all entities
	    	batch.begin();
	    	entityManager.updateEntities(batch);
	    	entityManager.updatePlayers(batch);
	    	batch.end();
	    	
	    	entityManager.updateLists();
	    	
        	//Camera within bounds
	        cameraBounds();
	        cam.update();
	        
	        stage.act();
	        stage.draw();
        }
    }
    public void gameOverScreen() {  
    	paused = true;
    	dead = true;
    	stage.addActor(gameOverText);
    	stage.addActor(retryButton);
    	stage.addActor(quitButtonGO);
    }
    
    public static void pauseGame() {
    	if (paused) {
    		paused = false;
    		stage.addAction(Actions.removeActor(pauseText));
    		stage.addAction(Actions.removeActor(resumeButton));
    		stage.addAction(Actions.removeActor(quitButtonP));
    	}
    	else {
    		paused = true;	
        	stage.addActor(pauseText);
        	stage.addActor(resumeButton);
        	stage.addActor(quitButtonP);
    	}
    }
    
    
    public static void victoryScreen() {
    	paused = true;
    	dead = true;
    	stage.addActor(victoryText);
    	stage.addActor(continueButton);
    	stage.addActor(quitButtonP);
    	SaveFile.writeProgress(currentLevelIndex+1);
    	SaveFile.writeScore(player.getScore());
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
    /**
     * Constructs all the UI making the code less cluttered
     * 
     * @author Åsmund
     */
    private void initilizeUI() {
    	
    	Vector2 buttonSize = new Vector2(3,1.8f);
    	Vector2 smallUISize = new Vector2(2,2);
    	Vector2 titleSize = new Vector2(12,2);
    	
    	gameOverText = UI.label(titleSize, new Vector2(6,12), "GAME OVER", "title-dark");
    	
        pauseText = UI.label(titleSize, new Vector2(6,12), "PAUSED", "title-light");
        
        timerText = UI.label(smallUISize, new Vector2(0,14), "000", "default");
        
    	healthText = UI.label(smallUISize, new Vector2(3,14), "Health: ", "default");

    	scoreText = UI.label(smallUISize, new Vector2(6,14), "Score: ", "default");

    	victoryText = UI.label(titleSize, new Vector2(6,12), "VICTORY", "title-light");
    	
        retryButton = UI.newScreenButton(buttonSize, new Vector2(13,8), "Retry?", ScreenType.Game, game, currentLevelIndex);
    	
    	quitButtonGO = UI.newScreenButton(buttonSize, new Vector2(8,8), "Quit", ScreenType.MenuScreen, game, 0);
    	
    	quitButtonP = UI.newScreenButton(buttonSize, new Vector2(10.5f,8), "Quit", ScreenType.MenuScreen, game, 0);
    	
    	continueButton = UI.newScreenButton(buttonSize, new Vector2(10.5f,10), "Continue", ScreenType.Game, game, currentLevelIndex+1);
    	
    	resumeButton = UI.resumeButton(buttonSize, new Vector2(10.5f,10));
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
