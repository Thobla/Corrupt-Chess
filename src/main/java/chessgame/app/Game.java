package chessgame.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import chessgame.world.GameMap;
import chessgame.world.TiledGameMap;

public class Game implements ApplicationListener {
    OrthographicCamera cam;
    GameMap gameMap;

    @Override
    public void create() {

        //The camera viewpoint
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.update();
        
        //The map
        gameMap = new TiledGameMap();
    }

    @Override
    public void dispose() {
       gameMap.dispose();
    }

    @Override
    public void render() {
        gameMap.render(cam);
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
