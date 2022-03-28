package chessgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;

import chessgame.app.ChessGame;
import chessgame.app.Game;
import chessgame.menues.LevelSelectScreen;
import chessgame.menues.MenuScreen;
import chessgame.menues.OptionScreen;
import chessgame.menues.SaveFile;

public class UI {
	
	//Scalable units for size and placements of UI
    static int rowHeight = Gdx.graphics.getHeight() / 16;
    static int colWidth = Gdx.graphics.getWidth() / 24;
    //Imported skin for UI
    static Skin skin = new Skin(Gdx.files.internal("assets/skin/chess/chess.json"));
    static //Sound for clicking buttons
    Sound click = Gdx.audio.newSound(Gdx.files.internal("assets/sound/menuClick.mp3"));
    static float volume = ((float)SaveFile.readSettings()[4])/100;

    /**
     * Creates a "blank" button without a InputListener
     * 
     * @param size of the button
     * @param position on the screen by 16 rows and 24 columns
     * @param text of what the button is saying
     * @return a new TextButton
     * 
     * @author Åsmund
     */
    public static TextButton button(Vector2 size, Vector2 position, String text) {
		TextButton button = new TextButton(text, skin, "default");
		button.setSize(colWidth*size.x, rowHeight*size.y);
		button.setPosition(colWidth*position.x, rowHeight*position.y);
		return button;
	}
    
    /**
     * Creates a new TextButton that changes to a new screen on activation
     * 
     * @param size of the button
     * @param position on the screen by 16 rows and 24 columns
     * @param text of what the button is saying
     * @param screen to switch to
     * @param game 
     * @param Variable if set screen needs an extra variable
     * @return a new TextButton
     * 
     * @author Åsmund
     */
	public static TextButton newScreenButton(Vector2 size, Vector2 position, String text, ScreenType screen, ChessGame game,@Null int Variable) {
		TextButton button = button(size, position, text);
		button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	switch (screen) {
            	case MenuScreen:
            		game.setScreen(new MenuScreen(game));
            		break;
            	case OptionScreen:
            		game.setScreen(new OptionScreen(game));
            		break;
				case Game:
					game.setScreen(new Game(game, Variable));
					break;
				default:
					break;
            	}	
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	click.play(volume);
                return true;
            }
        });
		return button;
	}
    
    
	
	
	/**
	 * Creates a new Label with the given size, position, text and style
	 * 
	 * @param size of the text
	 * @param position on the screen by 16 rows and 24 columns
	 * @param text 
	 * @param style of the text defined the skin
	 * @return a new Label
	 * 
	 * @author Åsmund
	 */
	public static Label label(Vector2 size, Vector2 position, String text, String style) {
		Label label = new Label(text, skin, style);
		label.setSize(colWidth*size.x, rowHeight*size.y);
		label.setPosition(colWidth*position.x, rowHeight*position.y);
		label.setAlignment(Align.center);
		return label;
	}
	
	public static Slider audioSlider(float audiolvl, int[] controls) {
		Slider audioSlider = new Slider(0, 100, 1, false, skin, "default-horizontal");
        audioSlider.setSize(colWidth*6, (float) (rowHeight*1.5));
        audioSlider.setPosition(Gdx.graphics.getWidth()/2 - colWidth*6/2,rowHeight*6);
        audioSlider.setValue(audiolvl);
        audioSlider.setSnapToValues(new float[] {0, 50, 100}, 3);
        audioSlider.addListener(new InputListener() {
        	@Override
        	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        		controls[4] = (int) audioSlider.getValue();
        		volume = ((float) controls[4]) / 100;
        	}
        	@Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		click.play(volume);
                return true;
            }
        });
		return audioSlider;
	}
	
	public static int[] newAudiolvl(int value, int[] controls) {
		controls[4] = value;
		volume = value/100;
		return controls;
	}
	
	public static TextButton playButton(Vector2 size, Vector2 position, ChessGame game) {
		TextButton button = button(size, position, "Play");
		button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	//If the player hasnt completed the first level yet, they will skip the levelSelectScreen and begin at lvl 1
            	if (SaveFile.readProgress()[0] == 0) {
            		game.setScreen(new Game(game, 0));
            	}	
            	else {
            		game.setScreen(new LevelSelectScreen(game));
            	}		
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	click.play(volume);
                return true;
            }
        });
		return button;
	}
	
	/**
	 * Creates a TextButton with the given parameters that closes the application on activation.
	 * 
	 * @param size of the button
	 * @param position on the screen by 16 rows and 24 columns
	 * @return a new TextButton
	 * 
	 * @author Åsmund
	 */
	public static TextButton exitButton(Vector2 size, Vector2 position) {
		TextButton button = button(size, position, "Quit");
		button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	Gdx.app.exit();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	click.play(volume);
                return true;
            }
        });
		return button;
	}
	
	public static TextButton changeButton(Vector2 size, Vector2 position, String buttonText, String promptText, int index, Stage stage, int[] controls) {
		TextButton button = button(size, position, buttonText);
		button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int buttona) {
            	changeButtonInput(button, promptText, index, stage, controls);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	click.play(volume);
                return true;
            }
        });
		return button;
	}
	
	private static int[] changeButtonInput(TextButton button, String text, int index, Stage stage, int[] controls) {
		
		//Text prompting you to type new input for key
        Label prompt = UI.label(new Vector2(24,2), new Vector2(0,10),"Press the button for the new " + text + " key.", "title-light");
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
        
        return controls;
	}
	
	
	public static TextButton defaultControlsButton(Vector2 size, Vector2 position, ChessGame game) {
		TextButton button = UI.button(size, position, "Default controls");
        button.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	SaveFile.defaultControls();
            	game.setScreen(new OptionScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	click.play(volume);
                return true;
            }
        });
		return button;
	}

	public static TextButton resumeButton(Vector2 size, Vector2 position) {
		TextButton button = button(size, position, "Resume");
		button.addListener(new InputListener() {
    		@Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    			Game.pauseGame();
    		}
    		
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
    			click.play(volume);
    			return true;
    		}	
    	});
		return button;
	}
}

