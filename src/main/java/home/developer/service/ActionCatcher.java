package home.developer.service;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface ActionCatcher {

    boolean needStarting(BufferedImage bufferedImage);

    boolean successFinishing(BufferedImage bufferedImage);

    boolean failFishing(BufferedImage bufferedImage);

    boolean needStopping(BufferedImage bufferedImage);

    boolean needCaptcha(BufferedImage bufferedImage);
}
