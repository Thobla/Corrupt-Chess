package chessgame.menues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import chessgame.app.ChessGame;

public class ControllerChangingScreen implements Screen {

	OrthographicCamera cam;
    final ChessGame game;
    private Stage stage;
	
	
	public ControllerChangingScreen(ChessGame game, String button, int buttonMemory) {
		
		this.game = game;
		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        int rowHeight = Gdx.graphics.getHeight() / 16;
        int colWidth = Gdx.graphics.getWidth() / 24;
        
        Skin skin = new Skin(Gdx.files.internal("assets/skin/goldenspiralui/golden-ui-skin.json"));
        
        byte[] controls = SaveFile.readControls();	
        
      //Background image
        Table backgroundTable = new Table();
        backgroundTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("assets/background.png"))));
        backgroundTable.setFillParent(true);
        backgroundTable.setDebug(true);
        stage.addActor(backgroundTable);
        
        Label prompt = new Label("Press the button for the new " + button + " key." , skin, "title");
        prompt.setSize(Gdx.graphics.getWidth(),rowHeight*2);
        prompt.setPosition(0,Gdx.graphics.getHeight()-rowHeight*6);
        prompt.setAlignment(Align.center);
        stage.addActor(prompt);
        
        Gdx.input.setInputProcessor(new InputAdapter () {
        	   @Override
        	   public boolean keyDown (int keycode) {
        		   SaveNewControls(keycode,controls,buttonMemory);
        		   game.setScreen(new OptionScreen(game));
                   dispose();
        		   return true;
        	   }
        	});
	}
	
	private void SaveNewControls(int keycode, byte[] controls, int mem) {
		controls[mem] = (byte) keycode;
		SaveFile.writeControls(controls);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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