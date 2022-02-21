package home.developer;

import home.developer.core.RandomGenerator;
import home.developer.service.ActionCatcher;
import home.developer.service.ImageCatcher;
import home.developer.service.SoundPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class RunningProcess {

    @Autowired
    ActionCatcher actionCatcher;

    @Autowired
    FishingProcess fishingProcess;

    @Autowired
    Robot robot;

    @Autowired
    RandomGenerator randomGenerator;

    @Autowired
    ImageCatcher imageCatcher;

    @Autowired
    Point failPoint;

    @Autowired
    Color failColor;

    @Autowired
    SoundPlayer soundPlayer;

    public void run() throws InterruptedException, IOException {
        System.out.println("Run FishBot Application");

        BufferedImage bufferedImage;
        boolean needToWork = false;
        boolean activateNotification = true;
        while (true) {
            Thread.sleep(randomGenerator.generateValue(500, 700));
            bufferedImage = imageCatcher.updateImage();
            if (actionCatcher.needStarting(bufferedImage)) {
                System.out.println("starting process");
                needToWork = true;
                activateNotification = true;
            }

            if (actionCatcher.failFishing(bufferedImage)) {
                System.out.println("Stopping fail by fishing");
                needToWork = false;

                //  Один раз показали, и хватит
                if (activateNotification) {
                    soundPlayer.applicationCaptcha();
                    activateNotification = false;
                }
            }

            if (actionCatcher.needStopping(bufferedImage)) {
                System.out.println("Stopping by inventory");
                needToWork = false;
            }

            if (actionCatcher.needCaptcha(bufferedImage)) {
                System.out.println("Stopping by captcha");
                needToWork = false;

                //  Один раз показали, и хватит
                if (activateNotification) {
                    soundPlayer.applicationCaptcha();
                    activateNotification = false;
                }
            }


            if (needToWork) {
                System.out.println("need to work");
                if (actionCatcher.successFinishing(bufferedImage)) {
                    System.out.println("Success by fishing");
                    Thread.sleep(randomGenerator.generateValue(2000, 4000));
                    startFishing();
                    needToWork = false;
                } else {
                    System.out.println("start fishing process");
                    fishingProcess.run();
                    Thread.sleep(800);
                }
            }
        }
    }

    private void startFishing() throws InterruptedException {
        System.out.println("Throw for fishing");
        robot.keyPress(KeyEvent.VK_1);
        Thread.sleep(randomGenerator.generateValue(50,200));
        robot.keyRelease(KeyEvent.VK_1);
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

        boolean hasRed = Math.abs(red1 - red2) < 40;
        boolean hasGreen = Math.abs(green1 - green2) < 40;
        ;
        boolean hasBlue = Math.abs(blue1 - blue2) < 40;
        ;
        return hasRed && hasGreen && hasBlue;
    }

    private boolean test(BufferedImage image) {
        Color currentColor = new Color(image.getRGB(failPoint.x, failPoint.y));
        Color color = Color.GREEN;
        for (int i = -20; i < 20; i++) {
            image.setRGB((int) failPoint.getX() + i, (int) failPoint.getY(), color.getRGB());
        }

        for (int i = -20; i < 20; i++) {
            image.setRGB((int) failPoint.getX(), (int) failPoint.getY() + i, color.getRGB());
        }
        System.out.println(currentColor);
        System.out.println(failColor);
        return isCloseColor(failColor, currentColor);
    }
}
