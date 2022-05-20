package home.fishing.configuration;

import home.fishing.core.MyLOG;
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
