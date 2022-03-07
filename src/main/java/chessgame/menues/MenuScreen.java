package chessgame.menues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import chessgame.app.ChessGame;
import chessgame.app.Game;


public class MenuScreen implements Screen {

	OrthographicCamera cam;
    final ChessGame game;
    private Stage stage;
		
	public MenuScreen(ChessGame game) {
		
		this.game = game;
		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        //Background image
        Table backgroundTable = new Table();
        backgroundTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("assets/background.png"))));
        backgroundTable.setFillParent(true);
        backgroundTable.setDebug(true);
        stage.addActor(backgroundTable);
        
        int rowHeight = Gdx.graphics.getHeight() / 16;
        int colWidth = Gdx.graphics.getWidth() / 24;
        
        Skin skin = new Skin(Gdx.files.internal("assets/skin/goldenspiralui/golden-ui-skin.json"));
	
        Label title = new Label("Chess Game", skin, "title");
        title.setSize((float) (Gdx.graphics.getWidth()/2.2),rowHeight*2);
        title.setPosition(0,Gdx.graphics.getHeight()-rowHeight*2);
        title.setAlignment(Align.center);
        stage.addActor(title);
        
        
        Button button1 = new TextButton("Play",skin,"default");
        button1.setSize(colWidth*3,(float) (rowHeight*1.5));
        button1.setPosition(colWidth*4,Gdx.graphics.getHeight()-rowHeight*4);
        button1.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Game(game,"map"));
    			dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(button1);
        
     
        Button button2 = new TextButton("Options",skin,"default");
        button2.setSize(colWidth*3,(float) (rowHeight*1.5));
        button2.setPosition(colWidth*4,(float) (Gdx.graphics.getHeight()-rowHeight*5.5));
        button2.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionScreen(game));
                dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(button2);
        
        Button button3 = new TextButton("Credits",skin, "default");
        button3.setSize(colWidth*3, (float) (rowHeight*1.5));
        button3.setPosition(colWidth*4, Gdx.graphics.getHeight()-rowHeight*7);
        button3.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(button3);
        
        Button button4 = new TextButton("Quit",skin,"default");
        button4.setSize(colWidth*3,(float) (rowHeight*1.5));
        button4.setPosition(colWidth*4,(float) (Gdx.graphics.getHeight()-rowHeight*8.5));
        button4.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(button4);
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
		// TODO Auto-generated method stub

	}

}
