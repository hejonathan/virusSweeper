import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Runner {
    public static void main(String[] args) {
        Frame f = new Frame("HackTAMS");
    }

    public static synchronized void playSound(String url) {
        try (InputStream in = Runner.class.getResourceAsStream(url)) {
            InputStream bufferedIn = new BufferedInputStream(in);
            try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn)) {
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
