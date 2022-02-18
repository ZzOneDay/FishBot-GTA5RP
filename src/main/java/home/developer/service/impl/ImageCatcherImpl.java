package home.developer.service.impl;

import home.developer.service.ImageCatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class ImageCatcherImpl implements ImageCatcher {
    @Autowired
    Robot robot;

    @Override
    public BufferedImage updateImage() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        return robot.createScreenCapture(screenRectangle);
    }
}
