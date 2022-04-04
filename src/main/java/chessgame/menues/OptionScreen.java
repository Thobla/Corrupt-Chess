package chessgame.menues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import chessgame.app.ChessGame;
import chessgame.app.Main;
import chessgame.utils.Constants;
import chessgame.utils.SaveFile;
import chessgame.utils.ScreenType;
import chessgame.utils.UI;

public class OptionScreen implements Screen {

	OrthographicCamera cam;
    final ChessGame game;
    private Stage stage;
    private int up;
    private int right;
    private int left;
    private int sprint;
    private float audiolvl;
    private int resolutionIndex;
	
    //Scalable units for size and placements of UI
    int rowHeight = Gdx.graphics.getHeight() / 16;
    int colWidth = Gdx.graphics.getWidth() / 24;
    //Imported skin for UI
    Skin skin = new Skin(Gdx.files.internal("assets/skin/chess/chess.json"));
    
    static int[] controls;
    
    static float changingTimer = 0;
	static boolean timer = false;
    
	public OptionScreen(ChessGame game) {
		
		this.game = game;
		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        controls = SaveFile.readSettings();
        up = controls[0];
        left = controls[1];
        right = controls[2]; 
        sprint = controls[3];
        audiolvl = controls[4];  
        resolutionIndex = controls[5];
        
        //Background image
        Table backgroundTable = new Table();
        backgroundTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("assets/background.png"))));
        backgroundTable.setFillParent(true);
        stage.addActor(backgroundTable);
        
        //Title for this menu
        Label title = UI.label(new Vector2(24,2), new Vector2(0,14), "OPTIONS", "title-light");
        stage.addActor(title);
        
        //Button back to Title screen
        TextButton backButton = UI.newScreenButton(new Vector2(3,1.5f), new Vector2(10.5f,1), "Back", ScreenType.MenuScreen, game, 0);
        stage.addActor(backButton);
        
        //Text for up key
        Label upText = UI.label(new Vector2(2,1), new Vector2(10,12), "Key up", "default");
        stage.addActor(upText);
        //Button for up key
		TextButton upButton = UI.changeButton(new Vector2(2.2f,1), new Vector2(12,12), Keys.toString(up), "jump", 0, stage, controls);
        stage.addActor(upButton);
        
        //Text for left key
        Label leftText = UI.label(new Vector2(2,1), new Vector2(10,11), "Key left", "default");
        stage.addActor(leftText);
        //Button for left key
        TextButton leftButton = UI.changeButton(new Vector2(2.2f,1), new Vector2(12,11), Keys.toString(left), "left", 1, stage, controls);
        stage.addActor(leftButton);
        
        //Text for right key
        Label rightText = UI.label(new Vector2(2,1), new Vector2(10,10), "Key right", "default");
        stage.addActor(rightText);
        //Button for right key
        TextButton rightButton = UI.changeButton(new Vector2(2.2f,1), new Vector2(12,10), Keys.toString(right), "right", 2, stage, controls);
        stage.addActor(rightButton);
        
        //Text for sprint key
        Label sprintText = UI.label(new Vector2(2,1), new Vector2(10,9), "Key sprint", "default");
        stage.addActor(sprintText);
        //Button for sprint key
        TextButton sprintButton = UI.changeButton(new Vector2(2.2f,1), new Vector2(12,9), Keys.toString(sprint), "sprint", 3, stage, controls);
        stage.addActor(sprintButton);
        
        //Button for default controls reset
        TextButton defaultControls = UI.defaultControlsButton(new Vector2(6,1.8f), new Vector2(9,3), game);
        stage.addActor(defaultControls);
        
        //Text for audioSlider
        Label audio = UI.label(new Vector2(3,2), new Vector2(10.5f,6.8f), "Sound level", "default");
        stage.addActor(audio);
        //Slider for controlling audio
        Slider audioSlider = UI.audioSlider(new Vector2(6,1.5f) ,new Vector2(9,6), audiolvl, controls);
        stage.addActor(audioSlider);  
        
        String[] resItems = Constants.resolutionsString;
        SelectBox<String> resolution = UI.resolutionBox(new Vector2(2.5f,1.5f), new Vector2(16,6), resItems, resolutionIndex, controls, game);
        stage.addActor(resolution);
        
        CheckBox fullScreen = UI.fullScreenBox(new Vector2(2.2f,1), new Vector2(20,6), controls, game);
        stage.addActor(fullScreen);
	}
	
	public static void defaultControls() {
		SaveFile.defaultControls();
		controls = SaveFile.readSettings();
	}
	
	@Override
	public void show() {	
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        
        if (changingTimer > 0) {
        	changingTimer = changingTimer - delta;
        } else if (timer) {
        	UI.timesUp(stage);
        	timer = false;
        }
	}
	
	public static void startTimer(float time) {
		changingTimer = time;
		timer = true;
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
		SaveFile.writeSettings(controls);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
