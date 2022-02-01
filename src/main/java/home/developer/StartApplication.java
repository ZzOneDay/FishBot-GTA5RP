package home.developer;

import home.developer.configuration.ApplicationConfiguration;
import home.developer.service.SoundPlayer;
import home.developer.service.impl.SoundPlayerImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StartApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        SoundPlayerImpl soundPlayer = context.getBean(SoundPlayerImpl.class);
    }
}
