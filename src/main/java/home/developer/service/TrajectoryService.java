package home.developer.service;

import java.awt.*;
import java.util.List;

public interface TrajectoryService {

    /**
     * Create random point for way between start and finish
     *
     * @param start  start point
     * @param finish finish point
     * @return list of points like as way for mouse moving
     */
    List<Point> generatedTrajectory(Point start, Point finish);


    Point generateRandomPoint(Point currentPoint);
}
