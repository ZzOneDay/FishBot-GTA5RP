package home.developer.service.impl;

import home.developer.configuration.ApplicationConfiguration;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)

public class SoundPlayerImplTest extends TestCase {
    @Autowired
    SoundPlayerImpl soundPlayer;

    @Test
    public void startApplicationSound() {
        soundPlayer.playMusicStart();
    }
}