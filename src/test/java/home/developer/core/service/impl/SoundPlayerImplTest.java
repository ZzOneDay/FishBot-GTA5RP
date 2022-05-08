package home.developer.core.service.impl;

import home.developer.configuration.CoreConfiguration;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreConfiguration.class, loader = AnnotationConfigContextLoader.class)

public class SoundPlayerImplTest extends TestCase {
    @Autowired
    SoundPlayerImpl soundPlayer;
}