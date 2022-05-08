package home.developer.configuration;

import home.developer.core.MyLOG;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.awt.*;

@Configuration
@ComponentScan("home")
public class CoreConfiguration {
    @Bean
    @Scope("prototype")
    Robot robot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            MyLOG.error("Configuration is not correct. Bean Robot did not start. " + e.getMessage());
        }
        throw new IllegalArgumentException("Robot service was not initialized");
    }
}
