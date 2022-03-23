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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import chessgame.entities.Button;
import chessgame.entities.IEntities;
import chessgame.entities.Player;
import chessgame.entities.Portal;
import chessgame.utils.CameraStyles;
import chessgame.utils.Constants;
import chessgame.world.PhysicsWorld;
import chessgame.world.TiledGameMap;
import chessgame.utils.EntityManager;
import chessgame.menues.MenuScreen;
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
    
    static int currentLevelIndex;
    //All levels with their file names
    public static String[] levels = new String[] {
    	"map",
    	"map2",
    };
    
    //Stage for UI elements
    private static Stage stage;
    //Scalable units for size and placements of UI
    static int rowHeight = Gdx.graphics.getHeight() / 16;
    static int colWidth = Gdx.graphics.getWidth() / 24;
    //Imported skin for UI
    static Skin skin = new Skin(Gdx.files.internal("assets/skin/goldenspiralui/golden-ui-skin.json"));
    //UI
    static Label pauseText = new Label("PAUSED", skin, "title");
    static TextButton resumeButton = new TextButton ("Resume", skin, "default"); 
    static Label gameOverText = new Label("GAME OVER", skin, "title");
    static TextButton retryButton = new TextButton ("Retry?", skin, "default");
    static TextButton quitButtonGO = new TextButton ("Quit", skin, "default");
    static TextButton quitButtonP = new TextButton ("Quit", skin, "default");
    static Label healthText = new Label ("Health", skin, "default");
    static Label scoreText = new Label ("Score", skin, "default");
    static Label timerText = new Label ("000", skin, "default");
    static float timer;
    static Label victoryText = new Label("VICTORY", skin, "title");
    static TextButton continueButton = new TextButton ("Next level", skin, "default");
    
    static boolean paused = false;
    
    public Game(ChessGame game, int level) {
    	
    	this.game = game;
    	this.map = levels[level];
    	//World initialisation
    	gameWorld = new PhysicsWorld();
    	debugRenderer = new Box2DDebugRenderer();
    	
    	entityManager = new EntityManager(gameWorld);
    	
    	//The stage for UI elements
    	stage = new Stage(new ScreenViewport());
     
        //The camera viewpoint
        cam = new OrthographicCamera(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
        cam.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
        cam.update();
        
		
        //Batch
        batch = new SpriteBatch();
        int[] controls = SaveFile.readSettings();
        Gdx.input.setInputProcessor(new PlayerController(controls, game));
        //stage inputet må væra etter playercontroller for å fungere?
        Gdx.input.setInputProcessor(stage);
        
        //The Map renderer
        gameMap = new TiledGameMap(map);
        
        //Creates the player
        player = new Player(gameMap.getStartPoint(), gameWorld.world);
        
        //Creates bodies for TileMap
        tiledMap = new TmxMapLoader().load(Gdx.files.internal("assets/"+map+".tmx").file().getAbsolutePath());
    	gameWorld.tileMapToBody(tiledMap);
    	gameWorld.tileMapToEntities(tiledMap, entityManager);
    	
    	//Updates the map
    	entityManager.updateLists();

    	initilizeUI();
    	
    	timer = 300;
    	stage.addActor(timerText);
    	stage.addActor(healthText);
    	stage.addActor(scoreText);

    	entityManager.playerList.add(player);
    }

    @Override
    public void dispose() {
    	batch.dispose();
    	gameMap.dispose();
    }

    @Override
    public void render(float delta) {
        if (!paused) {
	        //Logic step
	    	gameWorld.logicStep(Gdx.graphics.getDeltaTime());
	        gameMap.render(cam);
	    	//Debug-render
	    	debugRenderer.render(gameWorld.world, cam.combined);
	    	batch.setProjectionMatrix(cam.combined);
	    	
	    	//Updates all entities
	    	batch.begin();
	    	for(IEntities entity : entityManager.entityList) {
	    		entity.updateState(batch);
	    	}
	    	entityManager.updateLists();
	    	
	    	player.updateState(batch);
	    	batch.end();

	    	CameraStyles.lockOnTarget(cam, player.getPosition());
	    	if (timer <= 0) {
	    		gameOverScreen();
	    	} else {
	    		timer = timer - delta;
		    	int time = (int) timer;
		    	timerText.setText(time);
	    	}
	    	
	    	healthText.setText("Health: " + player.getHealth());
	    	scoreText.setText("Score: " + player.getScore());
	           
	        //Camera within bounds
	        cameraBounds();
	        cam.update();
	        
	        stage.act();
	        stage.draw();
	        
	        
	        if (player.dead) {
	        	gameOverScreen();
	        }
        }
        else {
        	player.controllerUpdate();
        	
        	//Camera within bounds
	        cameraBounds();
	        cam.update();
	        
	        stage.act();
	        stage.draw();
        }
    }

    public void gameOverScreen() {   
    	//TODO remove player from game upon death
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
    	//TODO remove player from game upon victory
    	stage.addActor(victoryText);
    	stage.addActor(continueButton);
    	stage.addActor(quitButtonP);
    	SaveFile.writeProgress(currentLevelIndex+2);
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
     * Constructs all the buttons making the code less cluttered
     * 
     * @author Åsmund
     */
    private void initilizeUI() {
    	gameOverText.setSize(colWidth*12,rowHeight*2);
        gameOverText.setPosition((float) (Gdx.graphics.getWidth()/2-colWidth*6),rowHeight*12);
        gameOverText.setAlignment(Align.center);
        
        retryButton.setSize(colWidth*3, (float) (rowHeight*1.8));
    	retryButton.setPosition(colWidth*13, rowHeight*8);
    	retryButton.addListener(new InputListener() {
    		@Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    			game.setScreen(new Game(game, currentLevelIndex));
    		}
    		
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
    			return true;
    		}	
    	});
    	
    	quitButtonGO.setSize(colWidth*3, (float) (rowHeight*1.8));
    	quitButtonGO.setPosition(colWidth*8, rowHeight*8);
    	quitButtonGO.addListener(new InputListener() {
    		@Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    			game.setScreen(new MenuScreen(game));
    		}
    		
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
    			return true;
    		}	
    	});
    	
    	pauseText.setSize(colWidth*12,rowHeight*2);
        pauseText.setPosition((float) (Gdx.graphics.getWidth()/2-colWidth*6),rowHeight*12);
        pauseText.setAlignment(Align.center);
        
        resumeButton.setSize(colWidth*3, (float) (rowHeight*1.8));
    	resumeButton.setPosition(colWidth*12 - resumeButton.getWidth()/2, rowHeight*10);
    	resumeButton.addListener(new InputListener() {
    		@Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    			pauseGame();
    		}
    		
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
    			return true;
    		}	
    	});
    	
    	quitButtonP.setSize(colWidth*3, (float) (rowHeight*1.8));
    	quitButtonP.setPosition(colWidth*12 - quitButtonP.getWidth()/2, rowHeight*8);
    	quitButtonP.addListener(new InputListener() {
    		@Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    			pauseGame();
    			game.setScreen(new MenuScreen(game));
    		}
    		
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
    			return true;
    		}	
    	});
    	
    	timerText.setSize(colWidth*2,rowHeight*2);
        timerText.setPosition(0,rowHeight*14);
        timerText.setAlignment(Align.center);
        
        healthText.setSize(colWidth*2, rowHeight*2);
        healthText.setPosition(colWidth*2, rowHeight*14);
        healthText.setAlignment(Align.center);
        
        scoreText.setSize(colWidth*2, rowHeight*2);
        scoreText.setPosition(colWidth*4, rowHeight*14);
        scoreText.setAlignment(Align.center);
        
        victoryText.setSize(colWidth*12,rowHeight*2);
        victoryText.setPosition((float) (Gdx.graphics.getWidth()/2-colWidth*6),rowHeight*12);
        victoryText.setAlignment(Align.center);
        
        continueButton.setSize(colWidth*3, (float) (rowHeight*1.8));
        continueButton.setPosition(colWidth*12 - quitButtonP.getWidth()/2, rowHeight*10);
        continueButton.addListener(new InputListener() {
    		@Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    			game.setScreen(new Game(game, currentLevelIndex+1));
    		}
    		
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
    			return true;
    		}	
    	});
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
