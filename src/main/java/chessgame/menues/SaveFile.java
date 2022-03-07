package chessgame.menues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;

public class SaveFile {

	//saves.txt
	//[0]: lvl progress
	//[1]: highscore?
	
	public static void reset() {
		FileHandle file = Gdx.files.local("savefiles/saves.txt");
		file.writeBytes(new byte[] {1,0}, false);
	}
	
	public static void defaultControls() {
		FileHandle file = Gdx.files.local("savefiles/settings.txt");
		file.writeBytes(new byte[] {Keys.W, Keys.A, Keys.S, Keys.D}, false);
		
	}
	
	public static byte[] readControls() {
		FileHandle file = Gdx.files.local("savefiles/settings.txt");
		return file.readBytes();
	}
	
	/**
	 * 
	 * @param data
	 * 
	 * [0]=up
	 * [1]=left
	 * [2]=down
	 * [3]=left
	 * [4]=audiolvl
	 */
	public static void writeSettings(byte[] data) {
		FileHandle file = Gdx.files.local("savefiles/settings.txt");
		file.writeBytes(data, false);
	}
	public static void write(byte[] data) {
		FileHandle file = Gdx.files.local("savefiles/saves.txt");
		file.writeBytes(data, false);
	}
	
	public static byte[] read() {
		FileHandle file = Gdx.files.local("savefiles/saves.txt");
		return file.readBytes();
	}
}
