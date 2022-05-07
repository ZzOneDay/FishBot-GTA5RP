package home.developer;

import home.developer.core.RandomGenerator;
import home.developer.discord.UpdateNameService;
import home.developer.service.ActionCatcher;
import home.developer.service.ImageCatcher;
import home.developer.service.SoundPlayer;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSOutput;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
        UpdateNameService updateNameService = new UpdateNameService();
        int countFish = 0;
        int updateAfterFish = randomGenerator.generateValue(5,10);



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
//                Color currentRGBRED = new Color(bufferedImage.getRGB(1240, 800));
//                System.out.println("\tColor is " + currentRGBRED);

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
                    System.out.println("countFish " + countFish + " updateAfterFish " + updateAfterFish);
                    countFish++;
                    if (countFish > updateAfterFish) {
                        updateNameService.updateNick(countFish);
                        System.out.println("Обновление ника");
                        countFish = 0;
                        updateAfterFish = randomGenerator.generateValue(5,10);
                    }
                    Thread.sleep(randomGenerator.generateValue(5000, 8000));
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
