package chessgame.menues;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;

public class SaveFile {
	
	/**
	 * Changes the controls in the settings file back to default (W,A,D,shift).
	 * !!DOES NOT CHANGE AUDIO!!
	 */
	public static void defaultControls() {
		byte audio = readSettings()[4];
		FileHandle file = Gdx.files.local("savefiles/settings.txt");
		file.writeBytes(new byte[] {Keys.W, Keys.A, Keys.D, Keys.SHIFT_LEFT, audio}, false);
		
	}
	
	/**
	 * Reads the settings file and returns its data.
	 * 
	 * @return
	 * 
	 * [0]=up input,
	 * [1]=left input,
	 * [2]=right input,
	 * [3]=sprint,
	 * [4]=audiolvl
	 * [5]=jumphelp
	 * 
	 * @author Åsmund
	 */
	public static byte[] readSettings() {
		FileHandle file = Gdx.files.local("savefiles/settings.txt");
		return file.readBytes();
	}
	
	/**
	 * Writes the input data to the settings file.
	 * 
	 * @param data Data to be saved.
	 * @see
	 * [0]=up input,
	 * [1]=left input,
	 * [2]=right input,
	 * [3]=sprint,
	 * [4]=audiolvl
	 * 
	 * @author Åsmund
	 */
	public static void writeSettings(byte[] data) {
		FileHandle file = Gdx.files.local("savefiles/settings.txt");
		file.writeBytes(data, false);
	}
	
	/**
	 * Writes the input data to the specified location in the settings file.
	 * 
	 * @param data New data to be saved
	 * @param index Index of data in settings file.
	 * @see
	 * [0]=up input,
	 * [1]=left input,
	 * [2]=right input,
	 * [3]=sprint,
	 * [4]=audiolvl
	 * 
	 * @author Åsmund
	 */
	public static void writeSingleSetting(byte data, int index) {
		FileHandle file = Gdx.files.local("savefiles/settings.txt");
		byte[] controls = file.readBytes();
		controls[index] = data;
		file.writeBytes(controls, false);
	}
	
	/**
	 * Resets the settingsfile to its default state
	 */
	public static void totalReset() {
		FileHandle file = Gdx.files.local("savefiles/settings.txt");
		file.writeBytes(new byte[] {Keys.W, Keys.A, Keys.D, Keys.SHIFT_LEFT, 50}, false);
	}
}
