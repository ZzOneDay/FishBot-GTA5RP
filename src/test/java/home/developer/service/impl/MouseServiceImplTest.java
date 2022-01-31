package home.developer.service.impl;

import home.developer.configuration.ApplicationConfiguration;
import home.developer.service.MouseService;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.awt.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class MouseServiceImplTest extends TestCase {

    @Autowired
    MouseService mouseService;

    @Autowired
    Robot robot;

    @Test
    public void testGetCurrentPosition1() {
        int x = 10;
        int y = 10;

        robot.mouseMove(x,y);
        robot.delay(1000);

        Point point = mouseService.getCurrentPosition();
        Assert.assertEquals(new Point(x,y), point);
    }
}