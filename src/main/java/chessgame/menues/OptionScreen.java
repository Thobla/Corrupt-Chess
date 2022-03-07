package chessgame.menues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import chessgame.app.ChessGame;

public class OptionScreen implements Screen {

	OrthographicCamera cam;
    final ChessGame game;
    private Stage stage;
    private byte up;
    private byte down;
    private byte right;
    private byte left;
    private float audiolvl;
		
	public OptionScreen(ChessGame game) {
		
		this.game = game;
		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        byte[] controls = SaveFile.readSettings();
        up = controls[0];
        down = controls[2];
        left = controls[1];
        right = controls[3];
        audiolvl = controls[4];
        
        
        //Background image
        Table backgroundTable = new Table();
        backgroundTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("assets/background.png"))));
        backgroundTable.setFillParent(true);
        backgroundTable.setDebug(true);
        stage.addActor(backgroundTable);
        
        //Scalable units for size and placements of UI
        int rowHeight = Gdx.graphics.getHeight() / 16;
        int colWidth = Gdx.graphics.getWidth() / 24;
        //Imported skin for UI
        Skin skin = new Skin(Gdx.files.internal("assets/skin/goldenspiralui/golden-ui-skin.json"));
        
        //Title for this menu
        Label title = new Label("Options", skin, "title");
        title.setSize(Gdx.graphics.getWidth(),rowHeight*2);
        title.setPosition(0,Gdx.graphics.getHeight()-rowHeight*2);
        title.setAlignment(Align.center);
        stage.addActor(title);
        
        //Button back to Title screen
        Button backButton = new TextButton("Back",skin,"default");
        backButton.setSize(colWidth*3,(float) (rowHeight*1.5));
        backButton.setPosition(Gdx.graphics.getWidth()/2 - colWidth*3/2,rowHeight);
        backButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.setScreen(new MenuScreen(game));
            	dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(backButton);
        
        //Text for up key
        Label upText = new Label("Key up",skin,"default");
        upText.setSize(colWidth*2,(float) (rowHeight*1.5));
        upText.setPosition(Gdx.graphics.getWidth()/2-colWidth*2,rowHeight*12);
        upText.setAlignment(Align.center);
        stage.addActor(upText);
        //Button for up key
		Button upButton = new TextButton(Keys.toString(up),skin,"default");
        upButton.setSize(colWidth*2,(float) (rowHeight*1.5));
        upButton.setPosition(Gdx.graphics.getWidth()/2,rowHeight*12);
        upButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.setScreen(new ControllerChangingScreen(game, "up", 0));
            	dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
                return true;
            }
        });
        stage.addActor(upButton);
        
        //Text for down key
        Label downText = new Label("Key down",skin,"default");
        downText.setSize(colWidth*2,(float) (rowHeight*1.5));
        downText.setPosition(Gdx.graphics.getWidth()/2-colWidth*2,rowHeight*11);
        downText.setAlignment(Align.center);
        stage.addActor(downText);
        //Button for down key
        Button downButton = new TextButton(Keys.toString(down),skin,"default");
        downButton.setSize(colWidth*2,(float) (rowHeight*1.5));
        downButton.setPosition(Gdx.graphics.getWidth()/2,rowHeight*11);
        downButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.setScreen(new ControllerChangingScreen(game, "down", 2));
            	dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
                return true;
            }
        });
        stage.addActor(downButton);
        
        //Text for left key
        Label leftText = new Label("Key left",skin,"default");
        leftText.setSize(colWidth*2,(float) (rowHeight*1.5));
        leftText.setPosition(Gdx.graphics.getWidth()/2-colWidth*2,rowHeight*10);
        leftText.setAlignment(Align.center);
        stage.addActor(leftText);
        //Button for left key
        Button leftButton = new TextButton(Keys.toString(left),skin,"default");
        leftButton.setSize(colWidth*2,(float) (rowHeight*1.5));
        leftButton.setPosition(Gdx.graphics.getWidth()/2,rowHeight*10);
        leftButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.setScreen(new ControllerChangingScreen(game, "left", 1));
            	dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
                return true;
            }
        });
        stage.addActor(leftButton);
        
        //Text for right key
        Label rightText = new Label("Key rightp",skin,"default");
        rightText.setSize(colWidth*2,(float) (rowHeight*1.5));
        rightText.setPosition(Gdx.graphics.getWidth()/2-colWidth*2,rowHeight*9);
        rightText.setAlignment(Align.center);
        stage.addActor(rightText);
        //Button for right key
        Button rightButton = new TextButton(Keys.toString(right),skin,"default");
        rightButton.setSize(colWidth*2,(float) (rowHeight*1.5));
        rightButton.setPosition(Gdx.graphics.getWidth()/2,rowHeight*9);
        rightButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	game.setScreen(new ControllerChangingScreen(game, "right", 3));
            	dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
                return true;
            }
        });
        stage.addActor(rightButton);
        
        //Button for default controls reset
        Button defaultControls = new TextButton("Reset to default", skin, "default");
        defaultControls.setSize(colWidth*6, (float) (rowHeight*1.8));
        defaultControls.setPosition(Gdx.graphics.getWidth()/2 - colWidth*6/2,rowHeight*3);
        defaultControls.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	SaveFile.defaultControls();
            	game.setScreen(new OptionScreen(game));
                dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(defaultControls);
        
        //Text for audioSlider
        Label audio = new Label("Sound level", skin, "default");
        audio.setSize(colWidth*3, (rowHeight*2));
        audio.setPosition(Gdx.graphics.getWidth()/2-colWidth*3/2,(float) (rowHeight*6.8));
        audio.setAlignment(Align.center);
        stage.addActor(audio);
        //Slider for controlling audio
        Slider audioSlider = new Slider(0, 100, 1, false, skin, "default-horizontal");
        audioSlider.setSize(colWidth*6, (float) (rowHeight*1.5));
        audioSlider.setPosition(Gdx.graphics.getWidth()/2 - colWidth*6/2,rowHeight*6);
        audioSlider.setValue(audiolvl);
        audioSlider.setSnapToValues(new float[] {0, 50, 100}, 3);
        audioSlider.addListener(new InputListener() {
        	@Override
        	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        		SaveFile.writeSingleSetting((byte) audioSlider.getValue(), 4);
        	}
        	@Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(audioSlider);
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
	}

}
