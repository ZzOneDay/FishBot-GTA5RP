package home.developer.core.service.impl;

import home.developer.configuration.CoreConfiguration;
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
@ContextConfiguration(classes = CoreConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class TrajectoryServiceImplTest extends TestCase {

    @Autowired
    TrajectoryServiceImpl trajectoryServiceImpl;

//    @Test
//    public void testGeneratePoint1() {
//        Point point1 = new Point(100, 100);
//        Point point2 = new Point(500, 500);
//
//        Point pointTest1 = trajectoryServiceImpl.generatePoint(point1, point2);
//        Point pointTest2 = trajectoryServiceImpl.generatePoint(point1, point2);
//        Point pointTest3 = trajectoryServiceImpl.generatePoint(point1, point2);
//
//        assertNotEquals(pointTest1, pointTest2);
//        assertNotEquals(pointTest2, pointTest3);
//
//        System.out.println(pointTest1);
//        System.out.println(pointTest2);
//        System.out.println(pointTest3);
//    }
//
//
//    @Test
//    public void testCreateRandomPoints1() {
//        Point point1 = new Point(100, 100);
//        Point point2 = new Point(500, 500);
//
//        List<Point> list = trajectoryServiceImpl.createRandomPoints(point1, point2, 4);
//
//        list.forEach(System.out::println);
//    }
//
//    @Test
//    public void testGetWay() {
//        Point point1 = new Point(100, 100);
//        Point point2 = new Point(500, 500);
//
//        List<Point> way = trajectoryServiceImpl.getWay(point1, point2);
//    }

    @Test
    public void testGeneratedTrajectory() {
        Long start = System.currentTimeMillis();
        Point center = new Point(1200,650);
        List<Point> list = trajectoryServiceImpl.generatedTrajectory();

//        List<Point> list1 = trajectoryServiceImpl.trajectory1(center);
//        List<Point> list2 = trajectoryServiceImpl.trajectory2(center);
//
//        int valueXLeft = 0;
//        for (Point point1 : list1) {
//            for (Point point2 : list2) {
//                if (Math.abs(point1.getX() - point2.getX()) < 5 && Math.abs(point1.getY() - point2.getY()) < 5) {
//                    valueXLeft = (int) point1.getX();
//                    break;
//                }
//            }
//        }
//
//        for (Point point : list1) {
//            if (point.getX() < valueXLeft) {
//                continue;
//            } else {
//                list.add(point);
//            }
//        }
//
//        for (Point point : list2) {
//            if (point.getX() < valueXLeft) {
//                continue;
//            } else {
//                list.add(point);
//            }
//        }
//
//        List<Point> list3 = trajectoryServiceImpl.trajectory3(center);
//
//        int minY = (int) list3.get(0).getY();
//        for (Point point : list2) {
//            if (point.getY() >  minY) {
//                point.y = minY;
//            }
//        }
//
//        List<Point> list4 = trajectoryServiceImpl.trajectory4(center);
//
//        int valueXRight = 0;
//        for (Point point3 : list3) {
//            for (Point point4 : list4) {
//                if (Math.abs(point3.getX() - point4.getX()) < 5 && Math.abs(point3.getY() - point4.getY()) < 5) {
//                    valueXRight = (int) point3.getX();
//                    break;
//                }
//            }
//        }
//
//        for (Point point : list3) {
//            if (point.getX() < valueXRight) {
//                list.add(point);
//            }
//        }
//
//        for (Point point : list4) {
//            if (point.getX() < valueXRight) {
//                list.add(point);
//            }
//        }
//
//        int maxY = (int) list1.get(0).getY();
//        for (Point point : list4) {
//            if (point.getY() < maxY) {
//                point.y = maxY;
//            }
//        }

        System.out.println(start - System.currentTimeMillis());
        System.out.println(list.size());
        printTest(list, center);
    }

    private void printTest(List<Point> points, Point center) {
        ClassLoader classLoader = getClass().getClassLoader();
        File img = new File(Objects.requireNonNull(classLoader.getResource("background.jpeg")).getFile());
        BufferedImage image = null;
        try {
            image = ImageIO.read(img);
            Point first = points.get(0);
            printLineLine(center, image, Color.GREEN);


            printLineLine(first, image, Color.RED);

            for (Point point : points) {
                image.setRGB((int) point.getX() + 1, (int) point.getY() + 1, Color.BLACK.getRGB());
                image.setRGB((int) point.getX() + 2, (int) point.getY() + 2, Color.BLACK.getRGB());
                image.setRGB((int) point.getX(), (int) point.getY(), Color.BLACK.getRGB());
                image.setRGB((int) point.getX() - 1, (int) point.getY() - 1, Color.BLACK.getRGB());
                image.setRGB((int) point.getX() - 2, (int) point.getY() - 2, Color.BLACK.getRGB());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        //set break point this and check
        boolean is = image != null;
    }


    private void printLineLine(Point first, BufferedImage image, Color color) {
        for (int i = -100; i < 100; i++) {
            image.setRGB((int) first.getX() + i, (int) first.getY(), color.getRGB());
        }

        for (int i = -100; i < 100; i++) {
            image.setRGB((int) first.getX(), (int) first.getY() + i, color.getRGB());
        }
    }
}