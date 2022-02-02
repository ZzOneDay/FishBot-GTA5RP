package home.developer.service.impl;

import home.developer.core.RandomGenerator;
import home.developer.service.MouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

@Service
public class MouseServiceImpl implements MouseService {
    private final int clickDelayMin = 100;
    private final int clickDelayMax = 200;

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
            // Push
            robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
            // Wait
            Thread.sleep(randomGenerator.generateValue(clickDelayMin, clickDelayMax));
            // Not push
            robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void move(Point point) {
        robot.mouseMove((int) point.getX(), (int) point.getY());
        try {
            Thread.sleep(randomGenerator.generateValue(10, 100));
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void clickAndMove(List<Point> way) {
        for (Point point : way) {
            robot.mouseMove((int) point.getX(), (int) point.getY());
            click();
        }
    }
}
