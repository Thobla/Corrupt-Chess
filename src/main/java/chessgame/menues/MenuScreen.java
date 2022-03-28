package chessgame.menues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import chessgame.app.ChessGame;
import chessgame.app.Game;
import chessgame.utils.ScreenType;
import chessgame.utils.UI;


public class MenuScreen implements Screen {

	OrthographicCamera cam;
    final ChessGame game;
    private Stage stage;
		
	public MenuScreen(ChessGame game) {
		
		this.game = game;
		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        int[] settings = SaveFile.readSettings();
        float volume = ((float)settings[4])/100;
        
        Sound click = Gdx.audio.newSound(Gdx.files.internal("assets/sound/menuClick.mp3"));
        
        //Background image
        Table backgroundTable = new Table();
        backgroundTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("assets/background.png"))));
        backgroundTable.setFillParent(true);
        stage.addActor(backgroundTable);
        //Scalable units for size and placements of UI
        int rowHeight = Gdx.graphics.getHeight() / 16;
        int colWidth = Gdx.graphics.getWidth() / 24;
        //Imported skin for UI
        Skin skin = new Skin(Gdx.files.internal("assets/skin/chess/chess.json"));
        
        Image logo = new Image(new Texture("assets/corruptChess.png"));
        logo.setSize(colWidth*5, rowHeight*4);
        logo.setPosition(colWidth*3, Gdx.graphics.getHeight()-rowHeight*4);
        logo.setAlign(Align.center);
        stage.addActor(logo);      
        
        //Play button for starting the game.
        Button playButton = UI.button(new Vector2(3, 1.5f), new Vector2(4, 10), "Play");
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	//If the player hasnt completed the first level yet, they will skip the levelSelectScreen and begin at lvl 1
            	if (SaveFile.readProgress()[0] == 0) {
            		game.setScreen(new Game(game, 0));
            		dispose();
            	}	
            	else {
            		game.setScreen(new LevelSelectScreen(game));
            		dispose();
            	}		
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	click.play(volume);
                return true;
            }
        });
        stage.addActor(playButton);
        
        //Button for going to the option screen.
        Button optionButton = UI.newScreenButton(new Vector2(3, 1.5f), new Vector2(4, 8.5f), "Options", ScreenType.OptionScreen, game, 0);
        stage.addActor(optionButton);
        
        //Currently unused button
        Button creditsButton = UI.button(new Vector2(3, 1.5f), new Vector2(4, 7), "Credits");
        stage.addActor(creditsButton);
        
        //Button for exiting the game
        Button quitButton = UI.quitButton(new Vector2(3, 1.5f), new Vector2(4, 5.5f));
        stage.addActor(quitButton);
        
        
        //Button for resetting the game
        Button resetButton = UI.button(new Vector2(4,2), new Vector2(20,0), "RESET");
        resetButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                SaveFile.totalReset();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	click.play(volume);
                return true;
            }
        });
        stage.addActor(resetButton);
        
	}
	
	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
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

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
