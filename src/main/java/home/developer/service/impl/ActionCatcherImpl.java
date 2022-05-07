package home.developer.service.impl;

import home.developer.service.ActionCatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
public class ActionCatcherImpl implements ActionCatcher {

    @Autowired
    private Robot robot;

    @Autowired
    private Point startingWhitePoint;
    @Autowired
    private Color startingWhiteColor;

    @Autowired
    private Point startingYellowPoint;
    @Autowired
    private Color startingYellowColor;

    @Autowired
    private Point successPoint;
    @Autowired
    private Color successColor;

    @Autowired
    private Point failPoint;
    @Autowired
    private Color failColor;

    @Autowired
    private Point stoppingPoint;
    @Autowired
    private Color stoppingColor;

    @Autowired
    private Point captchaPoint;
    @Autowired
    private Color captchaColor;


    @Override
    public boolean needStarting(BufferedImage bufferedImage) {
        Color whiteCurrentRGB = new Color(bufferedImage.getRGB(startingWhitePoint.x, startingWhitePoint.y));
        Color yellowCurrentRGB = new Color(bufferedImage.getRGB(startingYellowPoint.x, startingYellowPoint.y));
        return isCloseColor(startingWhiteColor, whiteCurrentRGB) &&
                isCloseColor(startingYellowColor, yellowCurrentRGB);
    }

    @Override
    public boolean successFinishing(BufferedImage bufferedImage) {
        Color currentRGB = new Color(bufferedImage.getRGB(successPoint.x, successPoint.y));
        return isCloseColor(successColor, currentRGB);
    }

    @Override
    public boolean failFishing(BufferedImage bufferedImage) {
        Color currentRGB = new Color(bufferedImage.getRGB(failPoint.x, failPoint.y));
        return isCloseColor(failColor, currentRGB);
    }

    @Override
    public boolean needStopping(BufferedImage bufferedImage) {
        Color currentRGB = new Color(bufferedImage.getRGB(stoppingPoint.x, stoppingPoint.y));
        return isCloseColor(stoppingColor, currentRGB);
    }

    @Override
    public boolean needCaptcha(BufferedImage bufferedImage) {
        Color currentRGB1 = new Color(bufferedImage.getRGB(captchaPoint.x, captchaPoint.y));
        Color currentRGB2 = new Color(bufferedImage.getRGB(captchaPoint.x + 5, captchaPoint.y + 5));
        Color currentRGB3 = new Color(bufferedImage.getRGB(captchaPoint.x - 5, captchaPoint.y - 5));

        Color currentRGBRED = new Color(bufferedImage.getRGB(600, 475));
        Color captchaRGBRED = new Color(240,35,59);
//        Stopping by captcha
//        Color is java.awt.Color[r=240,g=35,b=59]
        return isCloseColor(captchaColor, currentRGB1) &&
                isCloseColor(captchaColor, currentRGB2) &&
                isCloseColor(captchaColor, currentRGB3) &&
                isCloseColor(captchaRGBRED, currentRGBRED);
    }

    private boolean isCloseColor(Color color1, Color color2) {
        int colorValue1 = color1.getRGB();
        int colorValue2 = color2.getRGB();

        int red1 = (colorValue1 >> 16) & 0xff;
        int green1 = (colorValue1 >> 8) & 0xff;
        int blue1 = (colorValue1) & 0xff;

        int red2 = (colorValue2 >> 16) & 0xff;
        int green2 = (colorValue2 >> 8) & 0xff;
        int blue2 = (colorValue2) & 0xff;

        boolean hasRed = Math.abs(red1 - red2) < 30;
        boolean hasGreen = Math.abs(green1 - green2) < 30;
        ;
        boolean hasBlue = Math.abs(blue1 - blue2) < 30;
        ;
        return hasRed && hasGreen && hasBlue;
    }
}
