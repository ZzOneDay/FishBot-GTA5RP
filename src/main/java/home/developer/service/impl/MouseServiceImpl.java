package home.developer.service.impl;

import home.developer.core.RandomGenerator;
import home.developer.service.MouseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

@Service
@Scope("prototype")
public class MouseServiceImpl implements MouseService {

    @Value("${mouse.click.delay.push.min}")
    private int clickDelayPushMin;

    @Value("${mouse.click.delay.push.max}")
    private int clickDelayPushMax;

    @Value("${mouse.click.delay.start.min}")
    private int clickDelayStartMin;

    @Value("${mouse.click.delay.start.max}")
    private int clickDelayStartMax;

    @Value("${mouse.moving.delay.step.min}")
    private int moveDelayStepMin;

    @Value("${mouse.moving.delay.step.max}")
    private int moveDelayStepMax;

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
                Thread.sleep(randomGenerator.generateValue(clickDelayStartMin, clickDelayStartMax));
            }
            // Push
            robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
            // Wait
            if (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(randomGenerator.generateValue(clickDelayPushMin, clickDelayPushMax));
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
                Thread.sleep(randomGenerator.generateValue(moveDelayStepMin, moveDelayStepMax) / 100);
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
