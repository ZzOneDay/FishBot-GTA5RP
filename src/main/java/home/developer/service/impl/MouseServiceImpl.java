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

    @Autowired
    private Robot robot;

    @Autowired
    private RandomGenerator randomGenerator;

    @Value("${mouse.click.delay.min}")
    private int clickDelayMin;

    @Value("${mouse.click.delay.max}")
    private int clickDelayMax;

    @Value("${mouse.click.key}")
    private int keyClick;

    @Override
    public Point getCurrentPosition() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    @Override
    public void click() {
        // Push
        robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK);

        // Wait
        robot.delay(randomGenerator.generateValue(clickDelayMin, clickDelayMax));

        // Not push
        robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
    }

    @Override
    public void move(List<Point> way) {
        for (Point point : way) {
            robot.mouseMove((int) point.getX(), (int) point.getY());
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
