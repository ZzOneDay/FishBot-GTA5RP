package home.developer;

import home.developer.core.RandomGenerator;
import home.developer.service.MouseService;
import home.developer.service.SoundPlayer;
import home.developer.service.TargetCatcher;
import home.developer.service.TrajectoryService;
import home.developer.service.impl.MouseServiceImpl;
import home.developer.service.impl.TargetCatcherImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Robot robot;


    public void run() throws InterruptedException {
        int color = new Color(252, 1, 5, 255).getRGB();
        //java.awt.Point[x=1373,y=1191]
        Point point = new Point(1098, 952);
        Thread clicking = null;
        Thread moving = null;


        TargetCatcher catcher = new TargetCatcherImpl();

        Long lastStart = null;
        boolean fishingStart = false;
        while (true) {
            if (!fishingStart && catcher.isReadyForFishing(point, color)) {
                    System.out.println("start fishing");
                    soundPlayer.applicationMusicStart();
                    Point point1 = mouseService.getCurrentPosition();
                    Point point2 = trajectoryService.generateRandomPoint(point1);
                    clicking = new Thread(threadClicking());
                    clicking.setName("Thread for Clicking");
                    moving = new Thread(threadMoving(point1, point2));
                    moving.setName("Thread for Moving");
                    clicking.start();
                    moving.start();
                    lastStart = System.currentTimeMillis();
                    fishingStart = true;
            }


            if (fishingStart && !catcher.isReadyForFishing(point, color)) {
                System.out.println("StopFishing");
                System.out.println("Пытаюсь остановить потоки!");
                clicking.interrupt();
                moving.interrupt();
                if (clicking.isInterrupted() && moving.isInterrupted()) {
                    System.out.println("STOPPING");
                    soundPlayer.applicationMusicFinish();
                    break;
                }
            }

            if (lastStart != null) {
                System.out.println(lastStart - System.currentTimeMillis());
            }


            if (lastStart != null && System.currentTimeMillis() - lastStart > 6000) {
                System.out.println("LIMIT TIME. FAST OFF APPLICATION");
                while (!clicking.isInterrupted() || !moving.isInterrupted()) {
                    clicking.interrupt();
                    moving.interrupt();
                }

                if (clicking.isInterrupted() && moving.isInterrupted()) {
                    System.out.println("STOPPING");
                    soundPlayer.applicationMusicFinish();
                    break;
                }
            }
        }
    }

    Thread threadMoving(Point point1, Point point2) {
        Iterator<Point> iterator = trajectoryService.generatedTrajectory(point1, point2).iterator();
        return new Thread(() -> {
            MouseService mouseService = new MouseServiceImpl();
            while (!Thread.interrupted()) {
                if (iterator.hasNext()) {
                    //System.out.println("Move mouse");
                    mouseService.move(iterator.next());
                }
                if (Thread.interrupted()) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

    Thread threadClicking() {
        return new Thread(() -> {
            while (!Thread.interrupted()) {
                //System.out.println("Click mouse");
                mouseService.click();
                if (Thread.interrupted()) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }
}
