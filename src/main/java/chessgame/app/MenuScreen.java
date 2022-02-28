package chessgame.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MenuScreen implements Screen {

	OrthographicCamera cam;
    final ChessGame game;
    private Stage stage;
    private Label outputLabel;
		
	public MenuScreen(ChessGame game) {
		this.game = game;
		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        int HelpGuides = 12;
        int rowHeight = Gdx.graphics.getWidth() / 12;
        int colWidth = Gdx.graphics.getWidth() / 12;
        
        Skin skin = new Skin(Gdx.files.internal("assets/skin/flat-earth-ui.json"));
	
        Label title = new Label("Chess Game", skin, "title");
        title.setSize(Gdx.graphics.getWidth(),rowHeight*2);
        title.setPosition(0,Gdx.graphics.getHeight()-rowHeight*2);
        title.setAlignment(Align.center);
        stage.addActor(title);
        
        // Button
        Button button1 = new Button(skin,"default");
        button1.setSize(colWidth*4,rowHeight);
        button1.setPosition(colWidth,Gdx.graphics.getHeight()-rowHeight*3);
        button1.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                outputLabel.setText("Press a Button");
                game.setScreen(new GameScreen(game,"map"));
    			dispose();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                outputLabel.setText("Pressed Button");
                return true;
            }
        });
        stage.addActor(button1);
        
        outputLabel = new Label("Press a Button",skin,"default");
        outputLabel.setSize(Gdx.graphics.getWidth(),rowHeight);
        outputLabel.setPosition(0,rowHeight);
        outputLabel.setAlignment(Align.center);
        stage.addActor(outputLabel);
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
		// TODO Auto-generated method stub

	}

}
