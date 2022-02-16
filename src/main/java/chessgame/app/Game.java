package chessgame.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import chessgame.entities.Player;
import chessgame.world.GameMap;
import chessgame.world.TiledGameMap;

public class Game implements ApplicationListener {
    OrthographicCamera cam;
    Batch batch;
    GameMap gameMap;
    Player player;

    @Override
    public void create() {

        //The camera viewpoint
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.update();
        
        //Batch
        batch = new SpriteBatch();
        
        //Get sprite
        Sprite playerSprite = new Sprite(new Texture (Gdx.files.internal("assets/player.png").file().getAbsolutePath()));
        
        //The map
        gameMap = new TiledGameMap("map");
        //Displays the player at the maps start position.
        player = new Player(playerSprite , gameMap.getStartPoint());
    }

    @Override
    public void dispose() {
    	batch.dispose();
       gameMap.dispose();
    }

    @Override
    public void render() {
		/**
        if(Gdx.input.isKeyPressed(Keys.R)) {
        	System.out.println("Pressed R!");
        	gameMap.changeMap("map2");
        } */
        gameMap.render(cam);
        
    	batch.begin();
    	player.getSprite().draw(batch);
    	player.getSprite().setPosition(player.getPosition().x, player.getPosition().y);
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
