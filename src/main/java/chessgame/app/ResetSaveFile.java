package chessgame.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class ResetSaveFile {

	public static void reset() {
		FileHandle file = Gdx.files.local("savefiles/saves.txt");
		if (!file.exists()) {
			file.writeBytes(new byte[] {}, false);
		}
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
