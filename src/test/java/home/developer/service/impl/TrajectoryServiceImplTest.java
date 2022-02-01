package home.developer.service.impl;

import home.developer.configuration.ApplicationConfiguration;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TrajectoryServiceImplTest extends TestCase {

    @Autowired
    TrajectoryServiceImpl trajectoryServiceImpl;

    @Test
    public void testGeneratePoint1() {
        Point point1 = new Point(100, 100);
        Point point2 = new Point(500, 500);

        Point pointTest1 = trajectoryServiceImpl.generatePoint(point1, point2);
        Point pointTest2 = trajectoryServiceImpl.generatePoint(point1, point2);
        Point pointTest3 = trajectoryServiceImpl.generatePoint(point1, point2);

        assertNotEquals(pointTest1, pointTest2);
        assertNotEquals(pointTest2, pointTest3);

        System.out.println(pointTest1);
        System.out.println(pointTest2);
        System.out.println(pointTest3);
    }


    @Test
    public void testCreateRandomPoints1() {
        Point point1 = new Point(100, 100);
        Point point2 = new Point(500, 500);

        List<Point> list = trajectoryServiceImpl.createRandomPoints(point1, point2, 4);

        list.forEach(System.out::println);
    }

    @Test
    public void testGetWay() {
        Point point1 = new Point(100, 100);
        Point point2 = new Point(500, 500);

        List<Point> way = trajectoryServiceImpl.getWay(point1, point2);
    }

    @Test
    public void testGeneratedTrajectory() {
        Point point1 = new Point(100, 100);
        Point point2 = new Point(400, 400);

        List<Point> way = trajectoryServiceImpl.generatedTrajectory(point1, point2);

        printTest(way);
    }

    private void printTest(List<Point> points) {
        ClassLoader classLoader = getClass().getClassLoader();
        File img = new File(Objects.requireNonNull(classLoader.getResource("background.jpeg")).getFile());
        BufferedImage image = null;
        try {
            image = ImageIO.read(img);
            for (Point point : points) {
                image.setRGB((int) point.getX(), (int) point.getY(), Color.BLACK.getRGB());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        //set break point this and check
        boolean is = image != null;
    }
}