package home.developer.configuration;

import home.developer.core.MyLOG;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.awt.*;

@Configuration
@ComponentScan("home")
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    @Value("${target.point.color.red}")
    private int colorTargetRed;

    @Value("${target.point.color.green}")
    private int colorTargetGreen;

    @Value("${target.point.color.blue}")
    private int colorTargetBlue;

    @Value("${target.point.color.alpha}")
    private int colorTargetAlpha;

    @Value("${target.point.value.x}")
    private int targetPointX;

    @Value("${target.point.value.y}")
    private int targetPointY;

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


    @Bean
    Point targetPoint() {
        return new Point(targetPointX,targetPointY);
    }

    @Bean
    int targetColor() {
        return new Color(colorTargetRed, colorTargetGreen, colorTargetBlue, colorTargetAlpha).getRGB();
    }
}
