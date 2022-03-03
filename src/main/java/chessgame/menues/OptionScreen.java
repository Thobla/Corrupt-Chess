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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import chessgame.app.ChessGame;
import chessgame.app.GameScreen;


public class OptionScreen implements Screen {

	OrthographicCamera cam;
    final ChessGame game;
    private Stage stage;
    private byte upButton;
    private byte downButton;
    private byte rightButton;
    private byte leftButton;
		
	public OptionScreen(ChessGame game) {
		
		this.game = game;
		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        byte[] controls = SaveFile.readControls();
        upButton = controls[0];
        downButton = controls[2];
        leftButton = controls[1];
        rightButton = controls[3];
        
      //Background image
        Table backgroundTable = new Table();
        backgroundTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("assets/background.png"))));
        backgroundTable.setFillParent(true);
        backgroundTable.setDebug(true);
        stage.addActor(backgroundTable);
        
        int rowHeight = Gdx.graphics.getHeight() / 16;
        int colWidth = Gdx.graphics.getWidth() / 24;
        
        Skin skin = new Skin(Gdx.files.internal("assets/skin/goldenspiralui/golden-ui-skin.json"));
	
        Label title = new Label("Options", skin, "title");
        title.setSize(Gdx.graphics.getWidth(),rowHeight*2);
        title.setPosition(0,Gdx.graphics.getHeight()-rowHeight*2);
        title.setAlignment(Align.center);
        stage.addActor(title);
        
        
        Button button1 = new TextButton("Back",skin,"default");
        button1.setSize(colWidth*3,(float) (rowHeight*1.5));
        button1.setPosition(Gdx.graphics.getWidth()/2 - colWidth*3/2,rowHeight);
        button1.addListener(new InputListener(){
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
        stage.addActor(button1);
        
        
        
        
        Label up = new Label("Key up",skin,"default");
        up.setSize(colWidth*2,(float) (rowHeight*1.5));
        up.setPosition(Gdx.graphics.getWidth()/2-colWidth*2,rowHeight*12);
        up.setAlignment(Align.center);
        stage.addActor(up);

		Button buttonUp = new TextButton(Keys.toString(upButton),skin,"default");
        buttonUp.setSize(colWidth*2,(float) (rowHeight*1.5));
        buttonUp.setPosition(Gdx.graphics.getWidth()/2,rowHeight*12);
        buttonUp.addListener(new InputListener(){
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
        stage.addActor(buttonUp);
        
        Label down = new Label("Key down",skin,"default");
        down.setSize(colWidth*2,(float) (rowHeight*1.5));
        down.setPosition(Gdx.graphics.getWidth()/2-colWidth*2,rowHeight*11);
        down.setAlignment(Align.center);
        stage.addActor(down);
        
        Button buttonDown = new TextButton(Keys.toString(downButton),skin,"default");
        buttonDown.setSize(colWidth*2,(float) (rowHeight*1.5));
        buttonDown.setPosition(Gdx.graphics.getWidth()/2,rowHeight*11);
        buttonDown.addListener(new InputListener(){
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
        stage.addActor(buttonDown);
        
        Label left = new Label("Key left",skin,"default");
        left.setSize(colWidth*2,(float) (rowHeight*1.5));
        left.setPosition(Gdx.graphics.getWidth()/2-colWidth*2,rowHeight*10);
        left.setAlignment(Align.center);
        stage.addActor(left);
        
        Button buttonLeft = new TextButton(Keys.toString(leftButton),skin,"default");
        buttonLeft.setSize(colWidth*2,(float) (rowHeight*1.5));
        buttonLeft.setPosition(Gdx.graphics.getWidth()/2,rowHeight*10);
        buttonLeft.addListener(new InputListener(){
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
        stage.addActor(buttonLeft);
        
        Label right = new Label("Key rightp",skin,"default");
        right.setSize(colWidth*2,(float) (rowHeight*1.5));
        right.setPosition(Gdx.graphics.getWidth()/2-colWidth*2,rowHeight*9);
        right.setAlignment(Align.center);
        stage.addActor(right);
        
        Button buttonRight = new TextButton(Keys.toString(rightButton),skin,"default");
        buttonRight.setSize(colWidth*2,(float) (rowHeight*1.5));
        buttonRight.setPosition(Gdx.graphics.getWidth()/2,rowHeight*9);
        buttonRight.addListener(new InputListener(){
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
        stage.addActor(buttonRight);
        
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
