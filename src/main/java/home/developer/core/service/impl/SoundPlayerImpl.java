package home.developer.core.service.impl;

import home.developer.configuration.runConfiguration.RunConfiguration;
import home.developer.core.service.SoundPlayer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

@Service
@AllArgsConstructor
public class SoundPlayerImpl implements SoundPlayer {
    private final RunConfiguration runConfiguration;

    @Override
    public void applicationCaptcha() {
        if (runConfiguration.isAudio()) {
            try {
                File audio = new File("src/main/resources/attentionSound.wav");
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(audio));
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
