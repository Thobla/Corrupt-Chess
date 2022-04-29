package chessgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSound {

	
	static float volume = ((float)SaveFile.readSettings()[4])/100;
	
	static Sound click = Gdx.audio.newSound(Gdx.files.internal("assets/sound/menuClick.mp3"));
	
	static Sound testMusic1 = Gdx.audio.newSound(Gdx.files.internal("assets/sound/Mario1.mp3"));
	static Sound testMusic2 = Gdx.audio.newSound(Gdx.files.internal("assets/sound/Mario2.mp3"));
	
	static Sound[] musicList = new Sound[] {
			testMusic1,
			testMusic2
	};
	
	static Sound bell = Gdx.audio.newSound(Gdx.files.internal("assets/sound/snd_bell.wav"));
	static Sound lowimpact = Gdx.audio.newSound(Gdx.files.internal("assets/sound/snd_impact_low.mp3"));
	static Sound impact = Gdx.audio.newSound(Gdx.files.internal("assets/sound/snd_impact.wav"));
	
	static Sound[] soundEffectList = new Sound[] {
		bell,
		lowimpact,
		impact,
	};
	
	static Sound currentMusic = click;
	static long currentMusicID = -1;
	static int currentMusicIndex = -1;
	
	
	public static void buttonSound() {
		click.play(volume);
	}
	
	public static int[] newAudiolvl(int value, int[] controls) {
		controls[4] = value;
		volume = (float)value/100;
		return controls;
	}
	
	public static void playMusic(int index) {
		if (currentMusicIndex != index) {
			currentMusic.stop();
			currentMusic = musicList[index];
			currentMusicID = currentMusic.play(volume);
			currentMusicIndex = index;
			currentMusic.setLooping(currentMusicID, true);
		}
	}
	
	public static void stopMusic() {
		currentMusic.stop();
	}
	
	public static void playSoundEffect(int index, float volumeMOD) {
		soundEffectList[index].play(volume*volumeMOD);
	}
	
}
