package home.developer.service.impl;

import home.developer.service.TargetCatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@Scope("prototype")
public class TargetCatcherImpl implements TargetCatcher {

    @Autowired
    private Robot robot;

    public boolean isReadyForFishing(Point point, int fieldRGB) {

        //Color of field
        int red1 = (fieldRGB >> 16) & 0xff;
        int green1 = (fieldRGB >> 8) & 0xff;
        int blue1 = (fieldRGB) & 0xff;

        //Current color of field
        int currentRGB = robot.getPixelColor((int) point.getX(), (int) point.getY()).getRGB();
        int red2 = (currentRGB >> 16) & 0xff;
        int green2 = (currentRGB >> 8) & 0xff;
        int blue2 = (currentRGB) & 0xff;

        boolean hasRed = Math.abs(red1 - red2) < 10;
        boolean hasGreen = Math.abs(green1 - green2) < 10;;
        boolean hasBlue = Math.abs(blue1 - blue2) < 10;;
        return hasRed && hasGreen && hasBlue;
    }
}
