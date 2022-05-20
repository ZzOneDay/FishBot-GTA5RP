package home.fishing.core.service.impl;

import home.fishing.configuration.CoreConfiguration;
import home.fishing.core.service.ActionCatcher;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class ActionCatcherImplTest extends TestCase {
    @Autowired
    ActionCatcher actionCatcher;

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

    @Test
    public void testNeedStarting() {
        Point yellowPoint = new Point(startingYellowPointValueX,startingYellowPointValueY);
        Color yellowColor = new Color(243,184,17,255);

        Color whiteColor = new Color(205,204,203,255);
        Point whitePoint = new Point(startingWhitePointValueX,startingWhitePointValueY);
        //printPoint("needToStart.jpg", point);
        String fileName = "TestImage/2560x1440/needToStart.jpg";
        assertTrue(isCloseColor(loadColorFromPoint(fileName, yellowPoint), yellowColor));
        assertTrue(isCloseColor(loadColorFromPoint(fileName, whitePoint), whiteColor));
    }

    @Test
    public void testSuccessFinishing() {
        Color color = new Color(64,175,80,255);
        Point successPoint = new Point(successPointValueX,successPointValueY);
        //printPoint("needToStart.jpg", point);
        String fileName = "TestImage/2560x1440/success.jpg";
        assertTrue(isCloseColor(loadColorFromPoint(fileName, successPoint), color));
    }

    @Test
    public void testFailFishing() {
        Color color = new Color(142,29,49,255);
        Point failPoint = new Point(failPointValueX,failPointValueY);
        printPoint("TestImage/2560x1440/fail.jpg", failPoint);
        String fileName = "TestImage/2560x1440/fail.jpg";
        assertTrue(isCloseColor(loadColorFromPoint(fileName, failPoint), color));
    }

    @Test
    public void testNeedStopping() {
        Color color = new Color(162,233,53,255);
        Point stoppingPoint = new Point(stoppingPointValueX,stoppingPointValueY);
        printPoint("TestImage/2560x1440/needStopping.jpg", stoppingPoint);
        String fileName = "TestImage/2560x1440/needStopping.jpg";
        assertTrue(isCloseColor(loadColorFromPoint(fileName, stoppingPoint), color));
    }

    @Test
    public void testNeedCaptcha() {
        Color color = new Color(249,249,249,255);
        Point stoppingPoint = new Point(captchaPointValueX,captchaPointValueY);
        printPoint("TestImage/2560x1440/needToCaptcha.jpg", stoppingPoint);
        String fileName = "TestImage/2560x1440/needToCaptcha.jpg";
        assertTrue(isCloseColor(loadColorFromPoint(fileName, stoppingPoint), color));
    }


    private void printPoint(String fileName, Point point) {
        ClassLoader classLoader = getClass().getClassLoader();
        File img = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        BufferedImage image = null;
        try {
            image = ImageIO.read(img);

            image.setRGB((int) point.getX() + 4, (int) point.getY(), Color.BLACK.getRGB());
            image.setRGB((int) point.getX() + 3, (int) point.getY(), Color.BLACK.getRGB());
            image.setRGB((int) point.getX() + 2, (int) point.getY(), Color.BLACK.getRGB());
            image.setRGB((int) point.getX() + 1, (int) point.getY(), Color.BLACK.getRGB());
            image.setRGB((int) point.getX(), (int) point.getY(), Color.BLACK.getRGB());
            image.setRGB((int) point.getX() - 4, (int) point.getY(), Color.BLACK.getRGB());
            image.setRGB((int) point.getX() - 3, (int) point.getY(), Color.BLACK.getRGB());
            image.setRGB((int) point.getX() - 2, (int) point.getY(), Color.BLACK.getRGB());
            image.setRGB((int) point.getX() - 1, (int) point.getY(), Color.BLACK.getRGB());

            image.setRGB((int) point.getX(), (int) point.getY() - 4, Color.BLACK.getRGB());
            image.setRGB((int) point.getX(), (int) point.getY() - 3, Color.BLACK.getRGB());
            image.setRGB((int) point.getX(), (int) point.getY() - 2, Color.BLACK.getRGB());
            image.setRGB((int) point.getX(), (int) point.getY() - 1, Color.BLACK.getRGB());
            image.setRGB((int) point.getX(), (int) point.getY() + 1, Color.BLACK.getRGB());
            image.setRGB((int) point.getX(), (int) point.getY() + 2, Color.BLACK.getRGB());
            image.setRGB((int) point.getX(), (int) point.getY() + 3, Color.BLACK.getRGB());
            image.setRGB((int) point.getX(), (int) point.getY() + 4, Color.BLACK.getRGB());

        } catch (IOException e) {
            e.printStackTrace();
        }
        //set break point this and check
        boolean is = image != null;
    }

    private Color loadColorFromPoint(String fileName, Point point) {
        ClassLoader classLoader = getClass().getClassLoader();
        File img = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        BufferedImage image;
            try {
                image = ImageIO.read(img);
                return new Color(image.getRGB(point.x, point.y));
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new IllegalArgumentException();
        }

    private boolean isCloseColor(Color color1, Color color2) {
        int colorValue1 = color1.getRGB();
        int colorValue2 = color2.getRGB();

        int red1 = (colorValue1 >> 16) & 0xff;
        int green1 = (colorValue1 >> 8) & 0xff;
        int blue1 = (colorValue1) & 0xff;

        int red2 = (colorValue2 >> 16) & 0xff;
        int green2 = (colorValue2 >> 8) & 0xff;
        int blue2 = (colorValue2) & 0xff;

        boolean hasRed = Math.abs(red1 - red2) < 10;
        boolean hasGreen = Math.abs(green1 - green2) < 10;;
        boolean hasBlue = Math.abs(blue1 - blue2) < 10;;
        return hasRed && hasGreen && hasBlue;
    }

}