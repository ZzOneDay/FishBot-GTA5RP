package home.developer.service;

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
     * Mouse move by points (way)
     *
     * @param way points
     */
    void move(List<Point> way);

    /**
     * Mouse move and click after every moving
     *
     * @param way points
     */
    void clickAndMove(List<Point> way);
}
