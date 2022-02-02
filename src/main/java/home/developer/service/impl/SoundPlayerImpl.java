package home.developer.service.impl;

import home.developer.service.SoundPlayer;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

@Service
public class SoundPlayerImpl implements SoundPlayer {

    public void applicationMusicStart() {
        try {
            File audio = new File("src/main/resources/start.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audio));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void applicationMusicFinish() {
        try {
            File audio = new File("src/main/resources/finish.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(audio));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
