package home.fishing.core.service;

import java.awt.*;
import java.util.List;

public interface MouseService {

    /**
     * Return current position of mouse.
     *
     * @return point with x and y
     */
    Point getCurrentPosition();

    /**
     * Click to point
     *
     */
    void click();

    /**
     * move to target
     *
     * @param point target
     */
    void move(Point point);

    /**
     * Mouse move and click after every moving
     *
     * @param way points
     */
    void clickAndMove(List<Point> way);
}
