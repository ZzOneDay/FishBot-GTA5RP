package home.developer.core;

import home.developer.configuration.entitySettings.coreSettings.CoreSettings;
import home.developer.configuration.runConfiguration.RunConfiguration;
import home.developer.core.discord.UpdateNameService;
import home.developer.core.service.ActionCatcher;
import home.developer.core.service.ImageCatcher;
import home.developer.core.service.SoundPlayer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@AllArgsConstructor
public class RunningProcess {
    private final ActionCatcher actionCatcher;
    private final FishingProcess fishingProcess;
    private final Robot robot;
    private final RandomGenerator randomGenerator;
    private final ImageCatcher imageCatcher;
    private final SoundPlayer soundPlayer;
    private final CoreSettings coreSettings;
    private final RunConfiguration runConfiguration;

    public void run() throws InterruptedException {
        System.out.println("Процесс: Запуск процесса рыбалки");
        UpdateNameService updateNameService = new UpdateNameService();
        int countFish = 0;
        int updateAfterFish = randomGenerator.generateValue(5, 10);

        BufferedImage bufferedImage;
        boolean needToWork = false;
        boolean activateNotification = true;
        while (true) {
            Thread.sleep(randomGenerator.generateValue(
                            coreSettings.getUpdateScreenSettings().getMin(),
                            coreSettings.getUpdateScreenSettings().getMax()
                    )
            );
            bufferedImage = imageCatcher.updateImage();
            if (actionCatcher.needStarting(bufferedImage)) {
                System.out.println("Процесс: Начинаем автоматическую ловлю рыбу");
                needToWork = true;
                activateNotification = true;
            }

            if (actionCatcher.failFishing(bufferedImage)) {
                needToWork = false;
                //  Один раз показали, и хватит
                if (activateNotification) {
                    System.out.println("Процесс: Неприятность, например кончилась приманка");
                    soundPlayer.applicationCaptcha();
                    activateNotification = false;
                }
            }

            if (actionCatcher.needStopping(bufferedImage)) {
                needToWork = false;
                //  Один раз показали, и хватит
                if (activateNotification) {
                    System.out.println("Процесс: Приостановили процесс, открыли инвентарь");
                    activateNotification = false;
                }
            }

            if (actionCatcher.needCaptcha(bufferedImage)) {
                needToWork = false;
                //  Один раз показали, и хватит
                if (activateNotification) {
                    System.out.println("Процесс: Появилась captcha, нужно пройти captcha");
                    soundPlayer.applicationCaptcha();
                    activateNotification = false;
                }
            }


            if (needToWork) {
                if (actionCatcher.successFinishing(bufferedImage)) {
                    System.out.println("Процесс: Успешно поймали рыбу");
                    countFish++;
                    if (countFish > updateAfterFish && runConfiguration.isDiscord()) {
                        updateNameService.updateNick(countFish);
                        System.out.println("Discord: Обновление ника");
                        countFish = 0;
                        updateAfterFish = randomGenerator.generateValue(5, 10);
                    }
                    Thread.sleep(randomGenerator.generateValue(
                                    coreSettings.getStartProcessSettings().getMin(),
                                    coreSettings.getStartProcessSettings().getMax()
                            )
                    );
                    System.out.println("Процесс: Закидываем удочку");
                    startFishing();
                    needToWork = false;
                } else {
                    fishingProcess.run();
                    Thread.sleep(800);
                }
            }
        }
    }

    private void startFishing() throws InterruptedException {
        robot.keyPress(KeyEvent.VK_1);
        Thread.sleep(randomGenerator.generateValue(100, 250));
        robot.keyRelease(KeyEvent.VK_1);
    }
}
