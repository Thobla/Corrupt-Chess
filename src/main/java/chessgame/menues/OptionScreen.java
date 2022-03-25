package chessgame.menues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import chessgame.app.ChessGame;

public class OptionScreen implements Screen {

	OrthographicCamera cam;
    final ChessGame game;
    private Stage stage;
    private int up;
    private int right;
    private int left;
    private int sprint;
    private float audiolvl;
	
    //Scalable units for size and placements of UI
    int rowHeight = Gdx.graphics.getHeight() / 16;
    int colWidth = Gdx.graphics.getWidth() / 24;
    //Imported skin for UI
    Skin skin = new Skin(Gdx.files.internal("assets/skin/chess/chess.json"));
    
    int[] controls = SaveFile.readSettings();
    
	public OptionScreen(ChessGame game) {
		
		this.game = game;
		stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        
        up = controls[0];
        left = controls[1];
        right = controls[2]; 
        sprint = controls[3];
        audiolvl = controls[4];

        //Background image
        Table backgroundTable = new Table();
        backgroundTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("assets/background.png"))));
        backgroundTable.setFillParent(true);
        stage.addActor(backgroundTable);
        
        //Title for this menu
        Label title = new Label("OPTIONS", skin, "title-light");
        title.setSize(Gdx.graphics.getWidth(),rowHeight*2);
        title.setPosition(0,Gdx.graphics.getHeight()-rowHeight*2);
        title.setAlignment(Align.center);
        stage.addActor(title);
        
        //Button back to Title screen
        TextButton backButton = new TextButton("Back",skin,"default");
        backButton.setSize(colWidth*3,(float) (rowHeight*1.5));
        backButton.setPosition(Gdx.graphics.getWidth()/2 - colWidth*3/2,rowHeight);
        backButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	SaveFile.writeSettings(controls);
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
        Label upText = new Label("Key jump",skin,"default");
        upText.setSize(colWidth*2,rowHeight);
        upText.setPosition(Gdx.graphics.getWidth()/2-colWidth*2,rowHeight*12);
        upText.setAlignment(Align.center);
        stage.addActor(upText);
        //Button for up key
		TextButton upButton = new TextButton(Keys.toString(up),skin,"default");
        upButton.setSize((float) (colWidth*2.2), rowHeight);
        upButton.setPosition(Gdx.graphics.getWidth()/2,rowHeight*12);
        upButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	changeButton(upButton,"jump", 0);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
                return true;
            }
        });
        stage.addActor(upButton);
        
        //Text for left key
        Label leftText = new Label("Key left",skin,"default");
        leftText.setSize(colWidth*2,rowHeight);
        leftText.setPosition(Gdx.graphics.getWidth()/2-colWidth*2,rowHeight*11);
        leftText.setAlignment(Align.center);
        stage.addActor(leftText);
        //Button for left key
        TextButton leftButton = new TextButton(Keys.toString(left),skin,"default");
        leftButton.setSize((float) (colWidth*2.2), rowHeight);
        leftButton.setPosition(Gdx.graphics.getWidth()/2,rowHeight*11);
        leftButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	changeButton(leftButton, "left", 1);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
                return true;
            }
        });
        stage.addActor(leftButton);
        
        //Text for right key
        Label rightText = new Label("Key right",skin,"default");
        rightText.setSize(colWidth*2,rowHeight);
        rightText.setPosition(Gdx.graphics.getWidth()/2-colWidth*2,rowHeight*10);
        rightText.setAlignment(Align.center);
        stage.addActor(rightText);
        //Button for right key
        TextButton rightButton = new TextButton(Keys.toString(right),skin,"default");
        rightButton.setSize((float) (colWidth*2.2),rowHeight);
        rightButton.setPosition(Gdx.graphics.getWidth()/2,rowHeight*10);
        rightButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	changeButton(rightButton, "right", 2);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
                return true;
            }
        });
        stage.addActor(rightButton);
        
      //Text for sprint key
        Label sprintText = new Label("Key sprint",skin,"default");
        sprintText.setSize(colWidth*2,rowHeight);
        sprintText.setPosition(Gdx.graphics.getWidth()/2-colWidth*2,rowHeight*9);
        sprintText.setAlignment(Align.center);
        stage.addActor(sprintText);
        //Button for sprint key
        TextButton sprintButton = new TextButton(Keys.toString(sprint),skin,"default");
        sprintButton.setSize((float) (colWidth*2.2), (rowHeight*1));
        sprintButton.setPosition(Gdx.graphics.getWidth()/2,rowHeight*9);
        sprintButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	changeButton(sprintButton, "sprint", 3);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	
                return true;
            }
        });
        stage.addActor(sprintButton);
        
        //Button for default controls reset
        TextButton defaultControls = new TextButton("Reset to default", skin, "default");
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
        		controls[4] = (int) audioSlider.getValue();
        	}
        	@Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(audioSlider);
	}
	
	public void changeButton(TextButton button, String text, int index) {
		
		//Text prompting you to type new input for key
        Label prompt = new Label("Press the button for the new " + text + " key." , skin, "title-light");
        prompt.setSize(Gdx.graphics.getWidth(),rowHeight*2);
        prompt.setPosition(0,Gdx.graphics.getHeight()-rowHeight*6);
        prompt.setAlignment(Align.center);
        stage.addActor(prompt);
        
        Gdx.input.setInputProcessor(new InputAdapter () {
     	   @Override
     	   public boolean keyDown (int keycode) {
     		   controls[index] = keycode;
     		   button.setText(Keys.toString(keycode));
     		   stage.addAction(Actions.removeActor(prompt));
     		   Gdx.input.setInputProcessor(stage);
     		   return true;
     	   }
     	});
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
