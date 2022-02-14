package chessgame.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HelloWorld implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
        
        
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

//        batch.begin();
//        font.draw(batch, "Would you rather have:\n Unlimited bacon but no game. \n Or games unlimited games, but no games"
//        		+ "", 100, 200);
//        batch.end();
        
        
        //PUSH DA
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(90, 140, 300, 70);
        shapeRenderer.end();
        
        batch.begin();
        font.draw(batch, "Would you rather have:\n Unlimited bacon but no game. \n Or games unlimited games, but no games"
        		+ "", 100, 200);
        batch.end();
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
}
