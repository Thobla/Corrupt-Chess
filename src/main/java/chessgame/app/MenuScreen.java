package chessgame.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class MenuScreen implements Screen {

	OrthographicCamera cam;
    final ChessGame game;
		
	public MenuScreen(ChessGame game) {
		this.game = game;
			
		//The camera viewpoint
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.update();
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		cam.update();
		game.batch.setProjectionMatrix(cam.combined);

		game.batch.begin();
		game.font.draw(game.batch, "CHESSGAME!!!", 100, 150);
		game.font.draw(game.batch, "Press ENTER to start game.", 100, 100);
		game.batch.end();
		
		if(Gdx.input.isKeyPressed(Keys.ENTER)) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
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
