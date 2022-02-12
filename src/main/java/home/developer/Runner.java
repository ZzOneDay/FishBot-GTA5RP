package home.developer;

import home.developer.core.RandomGenerator;
import home.developer.service.MouseService;
import home.developer.service.SoundPlayer;
import home.developer.service.TargetCatcher;
import home.developer.service.TrajectoryService;
import home.developer.service.impl.MouseServiceImpl;
import home.developer.service.impl.TargetCatcherImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Iterator;

@Service
public class Runner {

    @Autowired
    private SoundPlayer soundPlayer;

    @Autowired
    private MouseService mouseService;

    @Autowired
    private TrajectoryService trajectoryService;

    @Autowired
    private RandomGenerator randomGenerator;

    @Autowired
    private TargetCatcher targetCatcher;

    @Autowired
    private Robot robot;

    @Autowired
    private Point targetPoint;

    @Autowired
    private int targetColor;

    @Value("${audio.log.processing}")
    private boolean processWithAudio;

    @Value("${time.waiting.start.process.min}")
    private int timeWaitingStartProcessMin;

    @Value("${time.waiting.start.process.max}")
    private int timeWaitingStartProcessMax;

    @Value("${time.process.limit}")
    private int timeProcessLimit;


    public void run() throws InterruptedException {
        Thread clicking = null;
        Thread moving = null;
        Long lastStart = null;
        boolean fishingStart = false;

        Thread.sleep(randomGenerator.generateValue(timeWaitingStartProcessMin, timeWaitingStartProcessMax));
        while (true) {
            // Нужно начинать рыбачить.
            if (!fishingStart && targetCatcher.isReadyForFishing(targetPoint, targetColor)) {
                System.out.println("ИНФО: Начат процесс ловли");
                startAudio();
                // Поток для кликанья
                clicking = new Thread(threadClicking());
                clicking.setName("Thread for Clicking");

                // Поток для водить мышкой
                Point point1 = mouseService.getCurrentPosition();
                Point point2 = trajectoryService.generateRandomPoint(point1);
                moving = new Thread(threadMoving(point1, point2));
                moving.setName("Thread for Moving");

                // Запуск обоих потоков
                clicking.start();
                moving.start();

                // Задание переменных для отслеживания
                lastStart = System.currentTimeMillis();
                fishingStart = true;
            }

            // Превышен лимит ожидания, сколько можно там кликать мышкой?
            if (lastStart != null && System.currentTimeMillis() - lastStart > timeProcessLimit) {
                System.out.println("ОШИБКА: Лимит по времени. Прекращаю процесс ловли.");
                clicking.interrupt();
                moving.interrupt();
            }

            // Нужно заканчивать рыбачить
            if (fishingStart && !targetCatcher.isReadyForFishing(targetPoint, targetColor)) {
                System.out.println("ИНФО: Остановка процесса ловли");
                //Остановка потоков
                clicking.interrupt();
                moving.interrupt();

                //Только когда все потоки остановились, выходим из цикла
                if (clicking.isInterrupted() && moving.isInterrupted()) {
                    System.out.println("ИНФО: Процесс ловли остановлен");
                    finishAudio();
                    break;
                }
            }
        }
    }

    private Thread threadMoving(Point point1, Point point2) {
        Iterator<Point> iterator = trajectoryService.generatedTrajectory(point1, point2).iterator();
        return new Thread(() -> {
            while (!Thread.interrupted()) {
                if (iterator.hasNext()) {
                    mouseService.move(iterator.next());
                }

                // На всякий случай. Дополнительная остановка потока
                if (Thread.interrupted()) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

    private Thread threadClicking() {
        return new Thread(() -> {
            while (!Thread.interrupted()) {
                mouseService.click();

                // На всякий случай. Дополнительная остановка потока
                if (Thread.interrupted()) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }


    private void startAudio() {
        if (processWithAudio) {
            soundPlayer.applicationMusicStart();
        }
    }

    private void finishAudio() {
        if (processWithAudio) {
            soundPlayer.applicationMusicFinish();
        }
    }
}
