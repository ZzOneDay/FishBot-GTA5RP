package home.developer.service.impl;

import home.developer.core.RandomGenerator;
import home.developer.service.TrajectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class TrajectoryServiceImpl implements TrajectoryService {

    @Autowired
    private RandomGenerator randomGenerator;

    @Value("${trajectory.point.random.count}")
    private int count;

    @Value("${trajectory.point.center.valueX}")
    private int centerValueX;

    @Value("${trajectory.point.center.valueX.min}")
    private int centerValueXMin;

    @Value("${trajectory.point.center.valueX.max}")
    private int centerValueXMax;

    @Value("${trajectory.point.center.valueY}")
    private int centerValueY;

    @Value("${trajectory.point.center.valueY.min}")
    private int centerValueYMin;

    @Value("${trajectory.point.center.valueY.max}")
    private int centerValueYMax;

    @Value("${trajectory.delta.min}")
    private int deltaMin;

    @Value("${trajectory.delta.max}")
    private int deltaMax;

    @Value("${trajectory.delta.valueY.min}")
    private int deltaValueYMin;

    @Value("${trajectory.delta.valueY.max}")
    private int deltaValueYMax;


    //trajectory.delta.valueY.min
    @Override
    public List<Point> generatedTrajectory() {
        int centerX = centerValueX - randomGenerator.generateValue(centerValueXMin, centerValueXMax);
        int centerY = centerValueY - randomGenerator.generateValue(centerValueYMin, centerValueYMax);
//        int centerX = 1000;
//        int centerY = 600;
        Point center = new Point(centerX, centerY);
        List<Point> trajectory = new LinkedList<>();

        List<Point> list1 = trajectory1(center);
        List<Point> list2 = trajectory2(center);
        List<Point> list3 = trajectory3(center);
        List<Point> list4 = trajectory4(center);

//        trajectory.addAll(list1);
//        trajectory.addAll(list2);
//        trajectory.addAll(list3);
//        trajectory.addAll(list4);

        List<Point> leftList = cleanLeftList(list1, list2);
        List<Point> rightList = cleanRightList(list3, list4);

        connectUp(list1, list4);
        connectDown(list2,list3);

        trajectory.addAll(leftList);
        trajectory.addAll(rightList);

        return trajectory;
    }

    /**
     * Траектория движения
     * - - - - - S
     * -
     * F
     */
    private List<Point> trajectory1(Point center) {
        int centerY = (int) center.getY();
        int centerX = (int) center.getX();

        int startY = centerY - randomGenerator.generateValue(deltaValueYMin, deltaValueYMax);
        List<Point> list = new ArrayList<>();
        int currentX = 0;
        double delta = (double) randomGenerator.generateValue(deltaMin, deltaMax) / 10000;
        while (true) {
            currentX = currentX + 1;
            int valueY = (int) Math.floor(delta * (Math.pow(currentX, 2) / 2)); //0.002-0.005
            Point point = new Point(centerX - currentX, startY + valueY);

            if (!list.contains(point)) {
                list.add(point);
            }

            if (point.getY() > centerY + 200) {
                break;
            }
        }
        return list;
    }


    /**
     * Траектория движения
     * S
     * -
     * -
     * - - - - - F
     */
    private List<Point> trajectory2(Point center) {
        int centerY = (int) center.getY();
        int centerX = (int) center.getX();

        int startY = centerY + randomGenerator.generateValue(deltaValueYMin, deltaValueYMax);
        List<Point> list = new ArrayList<>();
        int currentX = 0;
        double delta = (double) randomGenerator.generateValue(deltaMin, deltaMax) / 10000;
        while (true) {
            currentX = currentX + 1;
            int valueY = (int) Math.floor(delta * (Math.pow(currentX, 2) / 2)); //0.002-0.005
            Point point = new Point(centerX - currentX, startY - valueY);

            if (!list.contains(point)) {
                list.add(0, point);
            }

            if (point.getY() < centerY - 200) {
                break;
            }
        }
        return list;
    }

    /**
     * Траектория движения
     * F
     * -
     * -
     * S - - - - -
     */
    private List<Point> trajectory3(Point center) {
        int centerY = (int) center.getY();
        int centerX = (int) center.getX();

        int startY = centerY + randomGenerator.generateValue(deltaValueYMin, deltaValueYMax);
        List<Point> list = new ArrayList<>();
        int currentX = 0;
        double delta = (double) randomGenerator.generateValue(deltaMin, deltaMax) / 10000;
        while (true) {
            currentX = currentX + 1;
            int valueY = (int) Math.floor(delta * (Math.pow(currentX, 2) / 2)); //0.002-0.005
            Point point = new Point(centerX + currentX, startY - valueY);

            if (!list.contains(point)) {
                list.add(point);
            }

            if (point.getY() < centerY - 200) {
                break;
            }

        }
        return list;
    }

    /**
     * Траектория движения
     * F - - - - -
     * -
     * -
     * S
     */
    private List<Point> trajectory4(Point center) {
        int centerY = (int) center.getY();
        int centerX = (int) center.getX();

        int startY = centerY - randomGenerator.generateValue(deltaValueYMin, deltaValueYMax);
        List<Point> list = new ArrayList<>();
        int currentX = 0;
        double delta = (double) randomGenerator.generateValue(deltaMin, deltaMax) / 10000;
        while (true) {
            currentX = currentX + 1;
            int valueY = (int) Math.floor(delta * (Math.pow(currentX, 2) / 2)); //0.002-0.005
            Point point = new Point(centerX + currentX, startY + valueY);

            if (!list.contains(point)) {
                list.add(0, point);
            }

            if (point.getY() > centerY + 200) {
                break;
            }

        }
        return list;
    }

    private List<Point> cleanLeftList(List<Point> list1, List<Point> list2) {
        List<Point> leftList = new LinkedList<>();

        int valueXLeft = 0;
        for (Point point1 : list1) {
            for (Point point2 : list2) {
                if (isClosePoints(point1, point2)) {
                    valueXLeft = (int) point1.getX();
                    break;
                }
            }
        }

        for (Point point : list1) {
            if (point.getX() > valueXLeft) {
                leftList.add(point);
            }
        }

        for (Point point : list2) {
            if (point.getX() > valueXLeft) {
                leftList.add(point);
            }
        }
        return leftList;
    }

    private List<Point> cleanRightList(List<Point> list3, List<Point> list4) {
        List<Point> rightList = new LinkedList<>();

        int valueXRight = 0;
        for (Point point3 : list3) {
            for (Point point4 : list4) {
                if (isClosePoints(point3, point4)) {
                    valueXRight = (int) point3.getX();
                    break;
                }
            }
        }

        for (Point point : list3) {
            if (point.getX() < valueXRight) {
                rightList.add(point);
            }
        }

        for (Point point : list4) {
            if (point.getX() < valueXRight) {
                rightList.add(point);
            }
        }

        return rightList;
    }

    private boolean isClosePoints(Point point1, Point point2) {
        int delta = 5;
        return Math.abs(point1.getX() - point2.getX()) < delta
                && Math.abs(point1.getY() - point2.getY()) < delta;
    }

    private void connectUp(List<Point> list1, List<Point> list4) {
        Point point1 = list1.get(0);
        Point point4 = list4.get(list4.size() - 1);

        if (point1.getY() > point4.getY()) {
            for (Point point : list4) {
                if (point.getY() < point1.getY()) {
                    point.y = (int) point1.getY();
                }
            }
        } else {
            for (Point point : list1) {
                if (point.getY() < point4.getY()) {
                    point.y = (int) point4.getY();
                }
            }
        }
    }

    private void connectDown(List<Point> list2, List<Point> list3) {
        Point point2 = list2.get(list2.size() - 1);
        Point point3 = list3.get(0);

        if (point2.getY() > point3.getY()) {
            for (Point point : list2) {
                if (point.getY() > point3.getY()) {
                    point.y = (int) point3.getY();
                }
            }
        } else {
            for (Point point : list3) {
                if (point.getY() > point2.getY()) {
                    point.y = (int) point2.getY();
                }
            }
        }
    }
}
