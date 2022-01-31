package home.developer.configuration;

import home.developer.core.MyLOG;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.awt.*;

@Configuration
@ComponentScan("home")
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    @Bean
    Robot robot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            MyLOG.error("Configuration is not correct. Bean Robot did not start. " + e.getMessage());
        }
        throw new IllegalArgumentException("Robot service was not initialized");
    }
}
