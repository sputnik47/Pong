import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

public class Sound {

	public void playSound(String Sound){
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound.class.getResource(Sound)));
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
