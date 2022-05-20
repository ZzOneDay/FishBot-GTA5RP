package home.fishing.core;

import home.fishing.core.service.*;
import home.fishing.core.service.impl.LineServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FishingProcess {
    private final MouseService mouseService;
    private final TrajectoryService trajectoryService;
    private final RandomGenerator randomGenerator;
    private final TargetCatcher targetCatcher;

    // Исключены из конструктора
    Thread clicking = null;
    Thread moving = null;


    public void run() {
        int TIME_PROCESS_LIMIT = 12000;
        Long lastStart = null;
        boolean fishingStart = false;

        while (true) {
            // Нужно начинать рыбачить.
            if (!fishingStart && targetCatcher.isReadyForFishing()) {
                System.out.println("CORE: Кликаем и крутим мышкой!");
                // Поток для Клика
                clicking = new Thread(threadClicking());
                clicking.setName("Thread for Clicking");

                // Поток для Движения мышкой
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
            if (lastStart != null && System.currentTimeMillis() - lastStart > TIME_PROCESS_LIMIT) {
                System.out.println("CORE: ОШИБКА: Лимит по времени. Прекращаю процесс ловли.");
                while (!clicking.isInterrupted() || !moving.isInterrupted()) {
                    clicking.interrupt();
                    moving.interrupt();
                }
                break;
            }

            // Нужно заканчивать рыбачить
            if (fishingStart && !targetCatcher.isReadyForFishing()) {
                System.out.println("CORE: Заканчиваем крутить и кликать мышкой v1");
                //Остановка потоков
                clicking.interrupt();
                moving.interrupt();

                //Только когда все потоки остановились, выходим из цикла
                while (!clicking.isInterrupted() && !moving.isInterrupted()) {
                    System.out.println("CORE: Заканчиваем крутить и кликать мышкой v2");
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


    @PreDestroy
    private void stopThreads() {
        System.out.println("CORE: Останавливаю потоки (Двигать мышкой и Кликать мышкой) если нужно");
        if (clicking != null) {
            clicking.interrupt();
        }

        if (moving != null) {
            moving.interrupt();
        }
    }
}
