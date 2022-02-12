package home.developer.service.impl;

import home.developer.core.RandomGenerator;
import home.developer.service.LineService;
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

    @Override
    public List<Point> generatedTrajectory(Point start, Point finish) {
        List<Point> pointList = createRandomPoints(start, finish, count);
        List<Point> trajectory = new LinkedList<>();

        for (int i = 0; i < pointList.size() - 2; i++) {
            Point point1 = pointList.get(i);
            Point point2 = pointList.get(i + 1);
            List<Point> pointsOfWay = getWay(point1, point2);
            trajectory.addAll(pointsOfWay);
        }
        return trajectory;
    }


    public List<Point> createRandomPoints(Point start, Point finish, int count) {
        List<Point> points = new ArrayList<>(count + 2);
        while (points.size() < count) {
            points.add(generatePoint(start, finish));
        }
        points.add(start);
        points.add(finish);

        sortPoints(start, points);
        return points;
    }

    public Point generatePoint(Point point1, Point point2) {
        int valueX1 = (int) point1.getX();
        int valueX2 = (int) point2.getX();

        int valueY1 = (int) point1.getY();
        int valueY2 = (int) point2.getY();

        int valueX;
        if (valueX1 < valueX2) {
            valueX = randomGenerator.generateValue(valueX1, valueX2);
        } else {
            valueX = randomGenerator.generateValue(valueX2, valueX1);
        }

        int valueY;
        if (valueY1 < valueY2) {
            valueY = randomGenerator.generateValue(valueY1, valueY2);
        } else {
            valueY = randomGenerator.generateValue(valueY2, valueY1);
        }

        return new Point(valueX, valueY);
    }

    public void sortPoints(Point start, List<Point> points) {
        points.sort((o1, o2) -> {
            double delta1 = Math.abs(start.getX() - o1.getX());
            double delta2 = Math.abs(start.getY() - o1.getY());

            double delta3 = Math.abs(start.getX() - o2.getX());
            double delta4 = Math.abs(start.getY() - o2.getY());

            if (o1.equals(o2)) {
                return 0;
            }

            if (delta1 + delta2 > delta3 + delta4) {
                return +1;
            } else {
                return -1;
            }
        });
    }

    public List<Point> getWay(Point point1, Point point2) {
        List<Point> points = new ArrayList<>();
        LineService lineService = LineServiceImpl.createLineOf2Points(point1, point2);

        if (point1.getX() < point2.getX()) {
            for (int currentX = (int) point1.getX(); currentX < (int) point2.getX(); currentX++) {
                Point point = new Point(currentX, lineService.getY(currentX));
                if (!points.contains(point)) {
                    points.add(point);
                }
            }
        } else {
            for (int currentX = (int) point2.getX(); currentX < (int) point1.getX(); currentX++) {
                Point point = new Point(currentX, lineService.getY(currentX));
                if (!points.contains(point)) {
                    points.add(point);
                }
            }
        }

        if (point1.getY() < point2.getY()) {
            for (int currentY = (int) point1.getY(); currentY < (int) point2.getY(); currentY++) {
                Point point = new Point(lineService.getX(currentY), currentY);
                if (!points.contains(point)) {
                    points.add(point);
                }
            }
        } else {
            for (int currentY = (int) point2.getY(); currentY < (int) point1.getY(); currentY++) {
                Point point = new Point(lineService.getX(currentY), currentY);
                if (!points.contains(point)) {
                    points.add(point);
                }
            }
        }

        return points;
    }

public Point generateRandomPoint(Point currentPoint) {
    Point point1 = new Point(635,400);
    Point point2 = new Point(1200,987);
    Point point3 = new Point(614,999);
    Point point4 = new Point(1310,973);
    List<Point> points = new ArrayList<>(4);
    points.add(point1);
    points.add(point2);
    points.add(point3);
    points.add(point4);
    sortPoints(currentPoint, points);
    return points.get(points.size() - 1);
    }
}
