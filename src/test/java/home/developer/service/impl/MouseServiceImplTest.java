package home.developer.service.impl;

import home.developer.configuration.ApplicationConfiguration;
import home.developer.service.MouseService;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class MouseServiceImplTest extends TestCase {

    @Autowired
    MouseService mouseService;

    @Autowired
    Robot robot;

    @Value("${mouse.click.delay.min}")
    private int clickDelayMin;

    @Value("${mouse.click.delay.max}")
    private int clickDelayMax;

    @Test
    public void testGetCurrentPosition1() {
        int x = 10;
        int y = 10;

        robot.mouseMove(x, y);
        robot.delay(1000);

        Point point = mouseService.getCurrentPosition();
        Assert.assertEquals(new Point(x, y), point);
    }

    @Test
    public void testClick1() {
        long timeMillisStart = System.currentTimeMillis();
        mouseService.click();
        long timeMillisFinish = System.currentTimeMillis();

        assertTrue(timeMillisFinish - timeMillisStart < clickDelayMax);
        assertTrue(timeMillisFinish - timeMillisStart > clickDelayMin);
    }

    @Test
    public void testMove1() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(10, 10));
        points.add(new Point(50, 50));
        points.add(new Point(100, 100));
        Point point = new Point(200, 200);

        mouseService.move(points);
        assertEquals(point, mouseService.getCurrentPosition());
    }
}