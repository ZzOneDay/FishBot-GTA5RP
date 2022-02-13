package home.developer.service;

import java.awt.*;
import java.util.List;

public interface TrajectoryService {

    /**
     * Create random point for way between start and finish
     *
     * @return list of points like as way for mouse moving
     */
    List<Point> generatedTrajectory();

}
