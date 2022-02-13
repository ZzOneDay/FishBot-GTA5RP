package home.developer;

import home.developer.core.RandomGenerator;
import home.developer.service.*;
import home.developer.service.impl.LineServiceImpl;
import home.developer.service.impl.MouseServiceImpl;
import home.developer.service.impl.TargetCatcherImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.Iterator;
import java.util.List;

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
                moving = new Thread(threadMoving(point1));
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
                while (!clicking.isInterrupted() || !moving.isInterrupted()) {
                    clicking.interrupt();
                    moving.interrupt();
                }
                break;
            }

            // Нужно заканчивать рыбачить
            if (fishingStart && !targetCatcher.isReadyForFishing(targetPoint, targetColor)) {
                System.out.println("ИНФО: Остановка процесса ловли");
                //Остановка потоков
                clicking.interrupt();
                moving.interrupt();

                //Только когда все потоки остановились, выходим из цикла
                while (!clicking.isInterrupted() && !moving.isInterrupted()) {
                    System.out.println("ИНФО: Процесс ловли остановлен");
                    finishAudio();
                    break;
                }
                break;
            }
        }
    }

    private Thread threadMoving(Point point1) {
        return new Thread(() -> {
            List<Point> list = trajectoryService.generatedTrajectory();
            int startIndex = randomGenerator.generateValue(1, list.size() - 1);
            Point startPoint = list.get(startIndex);
            LineService lineService = LineServiceImpl.createLineOf2Points(point1, startPoint);
            if (point1.getX() > startPoint.getX()) {
                for (int x = (int) point1.getX(); x > startPoint.getX(); x--) {
                    mouseService.move(new Point(x, lineService.getY(x)));
                }
            } else {
                for (int x = (int) point1.getX(); x < startPoint.getX(); x++) {
                    mouseService.move(new Point(x, lineService.getY(x)));
                }
            }

            int start = startIndex;
            boolean needToStop = false;
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = start; i < list.size(); i++) {
                    if (!Thread.currentThread().isInterrupted()) {
                        mouseService.move(list.get(i));
                    } else {
                        needToStop = true;
                        break;
                    }
                }

                if (needToStop) {
                    break;
                } else {
                    start = 0;
                    list = trajectoryService.generatedTrajectory();
                }
            }
        });
    }

    private Thread threadClicking() {
        return new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                mouseService.click();

                // На всякий случай. Дополнительная остановка потока
                if (Thread.currentThread().isInterrupted()) {
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
