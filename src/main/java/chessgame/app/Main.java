package chessgame.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.useVsync(true);
        cfg.setTitle("Chess Game");
       // cfg.setWindowedMode(480, 320);
        Game game = new Game();
        new Lwjgl3Application(game, cfg);
    }
}