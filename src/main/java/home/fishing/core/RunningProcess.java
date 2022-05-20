package home.fishing.core;

import home.fishing.configuration.entitySettings.coreSettings.CoreSettings;
import home.fishing.configuration.runConfiguration.RunConfiguration;
import home.fishing.core.discord.UpdateNameService;
import home.fishing.core.service.ActionCatcher;
import home.fishing.core.service.ImageCatcher;
import home.fishing.core.service.SoundPlayer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

@Service
@RequiredArgsConstructor
public class RunningProcess {
    private final ActionCatcher actionCatcher;
    private final FishingProcess fishingProcess;
    private final Robot robot;
    private final RandomGenerator randomGenerator;
    private final ImageCatcher imageCatcher;
    private final SoundPlayer soundPlayer;
    private final CoreSettings coreSettings;
    private final RunConfiguration runConfiguration;
    private final UpdateNameService updateNameService;

    //Вне конструктора
    int countFish = 0;

    public void run() throws InterruptedException {
        System.out.println("Процесс: Запуск процесса рыбалки");
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

    @PreDestroy
    private void saveProgressBeforeExit() {
        System.out.println("Процесс: Пытаемся сохранить процесс по рыбаке и Обновить имя в Discord");
        if (runConfiguration.isDiscord()) {
            updateNameService.updateNick(countFish);
        }
    }
}
