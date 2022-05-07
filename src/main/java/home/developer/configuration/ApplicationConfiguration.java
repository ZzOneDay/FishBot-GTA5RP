package home.developer.configuration;

import home.developer.core.MyLOG;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.awt.*;

@Configuration
@ComponentScan("home")
@PropertySource("classpath:application1280x800.properties")
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

    //Colors
    @Value("${process.needStarting.point.white.valueX}")
    private int startingWhitePointValueX;

    @Value("${process.needStarting.point.white.valueY}")
    private int startingWhitePointValueY;

    @Value("${process.needStarting.point.yellow.valueX}")
    private int startingYellowPointValueX;

    @Value("${process.needStarting.point.yellow.valueY}")
    private int startingYellowPointValueY;

    @Value("${process.success.point.valueX}")
    private int successPointValueX;

    @Value("${process.success.point.valueY}")
    private int successPointValueY;

    @Value("${process.fail.point.valueX}")
    private int failPointValueX;

    @Value("${process.fail.point.valueY}")
    private int failPointValueY;

    @Value("${process.needStopping.point.valueX}")
    private int stoppingPointValueX;

    @Value("${process.needStopping.point.valueY}")
    private int stoppingPointValueY;

    @Value("${process.needCaptcha.point.valueX}")
    private int captchaPointValueX;

    @Value("${process.needCaptcha.point.valueY}")
    private int captchaPointValueY;

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

    @Bean
    Point startingWhitePoint() {
        return new Point(startingWhitePointValueX, startingWhitePointValueY);
    }

    @Bean
    Color startingWhiteColor() {
        return new Color(200,203,208,255);
    }

    @Bean
    Point startingYellowPoint() {
        return new Point(startingYellowPointValueX, startingYellowPointValueY);
    }

    @Bean
    Color startingYellowColor() {
        return new Color(241,184,24,255);
    }

    @Bean
    Point successPoint() {
        return new Point(successPointValueX, successPointValueY);
    }

    @Bean
    Color successColor() {
        return new Color(56,185,66,255);
    }

    @Bean
    Point failPoint() {
        return new Point(failPointValueX, failPointValueY);
    }

    @Bean
    Color failColor() {
        return new Color(172,16,53,255);
    }

    @Bean
    Point stoppingPoint() {
        return new Point(stoppingPointValueX, stoppingPointValueY);
    }

    @Bean
    Color stoppingColor() {
        return new Color(189,0,255,255);
    }

    @Bean
    Point captchaPoint() {
        return new Point(captchaPointValueX, captchaPointValueY);
    }

    @Bean
    Color captchaColor() {
        return new Color(249,249,249,255);
    }
}
