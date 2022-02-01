package home.developer.service.impl;

import home.developer.service.LineService;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class LineServiceImpl implements LineService {
    private final double indexA;
    private final double indexB;
    private final double indexC;

    private LineServiceImpl(double indexA, double indexB, double indexC) {
        this.indexA = indexA;
        this.indexB = indexB;
        this.indexC = indexC;
    }

    public int getY(int X) {
        return (int) ((-(indexA / indexB) * X) - (indexC / indexB));
    }

    public int getX(int Y) {
        return (int) ((-(indexB / indexA) * Y) - (indexC / indexA));
    }

    public static LineService createLineOf2Points(Point point1, Point point2) {
        double x1 = point1.getX();
        double y1 = point1.getY();

        double x2 = point2.getX();
        double y2 = point2.getY();

        double indexA = y2 - y1;
        double indexB = x1 - x2;

        double indexC = ((x2 * y1) - (x1 * y2));
        return new LineServiceImpl(indexA, indexB, indexC);
    }
}
