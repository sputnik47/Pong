import java.io.File;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

public class Sound {

	public void playSound(File Sound){
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
