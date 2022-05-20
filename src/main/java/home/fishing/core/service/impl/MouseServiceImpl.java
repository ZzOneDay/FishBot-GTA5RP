package home.fishing.core.service.impl;

import home.fishing.configuration.entitySettings.mouseSettings.MouseSettings;
import home.fishing.core.RandomGenerator;
import home.fishing.core.service.MouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

@Service
@Scope("prototype")
public class MouseServiceImpl implements MouseService {

    @Autowired
    private MouseSettings mouseSettings;

    @Override
    public Point getCurrentPosition() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    private Robot robot;
    private final RandomGenerator randomGenerator;

    public MouseServiceImpl() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        this.randomGenerator = new RandomGenerator();
    }

    @Override
    public void click() {
        try {
            // Wait before click
            if (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(randomGenerator.generateValue(
                                mouseSettings.getStart().getDelayMin(),
                                mouseSettings.getStart().getDelayMax()
                        )
                );
            }
            // Push
            robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
            // Wait
            if (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(randomGenerator.generateValue(
                                mouseSettings.getPush().getDelayMin(),
                                mouseSettings.getPush().getDelayMax()
                        )
                );
            }
            // Not push
            robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void move(Point point) {
        if (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(randomGenerator.generateValue(
                        mouseSettings.getMoving().getDelayMin(),
                        mouseSettings.getMoving().getDelayMax()
                ) / 100);
                robot.mouseMove((int) point.getX(), (int) point.getY());
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Deprecated
    @Override
    public void clickAndMove(List<Point> way) {
        for (Point point : way) {
            robot.mouseMove((int) point.getX(), (int) point.getY());
            click();
        }
    }
}
