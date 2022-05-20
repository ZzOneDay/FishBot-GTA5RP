package home.fishing.core.service.impl;

import home.fishing.configuration.entitySettings.colorSettings.ColorEntity;
import home.fishing.configuration.entitySettings.colorSettings.ColorSettings;
import home.fishing.configuration.entitySettings.targetSettings.TargetEntity;
import home.fishing.configuration.entitySettings.targetSettings.TargetSettings;
import home.fishing.core.service.ActionCatcher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;

@Service
@AllArgsConstructor
public class ActionCatcherImpl implements ActionCatcher {
    private final ColorSettings colorSettings;
    private final TargetSettings targetSettings;

    @Override
    public boolean needStarting(BufferedImage bufferedImage) {
        final Color colorMouseWhite = entityToColor(colorSettings.getMouseColors().getMouseWhite());
        final Color colorMouseYellow = entityToColor(colorSettings.getMouseColors().getMouseYellow());

        final Color colorMouseWhiteFromTarget =
                getColorFromTarget(bufferedImage, targetSettings.getMouseTargets().getMouseWhite());
        final Color colorMouseYellowFromTarget =
                getColorFromTarget(bufferedImage, targetSettings.getMouseTargets().getMouseYellow());

        return isCloseColor(colorMouseWhite, colorMouseWhiteFromTarget) &&
                isCloseColor(colorMouseYellow, colorMouseYellowFromTarget);
    }

    @Override
    public boolean successFinishing(BufferedImage bufferedImage) {
        final Color successColor =  entityToColor(colorSettings.getProcessColors().getSuccess());
        final Color currentRGB = getColorFromTarget(bufferedImage, targetSettings.getProcessTargets().getSuccess());
        return isCloseColor(successColor, currentRGB);
    }

    @Override
    public boolean failFishing(BufferedImage bufferedImage) {
        final Color failColor = entityToColor(colorSettings.getProcessColors().getFail());
        final Color currentRGB = getColorFromTarget(bufferedImage, targetSettings.getProcessTargets().getFail());
        return isCloseColor(failColor, currentRGB);
    }

    @Override
    public boolean needStopping(BufferedImage bufferedImage) {
        final Color inventoryColor = entityToColor(colorSettings.getInventoryColors().getPointPurple());
        final Color currentRGB = getColorFromTarget(bufferedImage, targetSettings.getInventoryTargets().getPointPurpleLeft());
        return isCloseColor(inventoryColor, currentRGB);
    }

    @Override
    public boolean needCaptcha(BufferedImage bufferedImage) {
        final Color captchaWhiteColor = entityToColor(colorSettings.getCaptchaColors().getPointWhite());
        final Color captchaRedColor = entityToColor(colorSettings.getCaptchaColors().getPointRed());

        final Color currentWhite1 = getColorFromTarget(bufferedImage, targetSettings.getCaptchaTargets().getPointWhite1());
        final Color currentWhite2 = getColorFromTarget(bufferedImage, targetSettings.getCaptchaTargets().getPointWhite2());
        final Color currentWhite3 = getColorFromTarget(bufferedImage, targetSettings.getCaptchaTargets().getPointWhite3());

        final Color currentRed1 = getColorFromTarget(bufferedImage, targetSettings.getCaptchaTargets().getPointRed1());
        final Color currentRed2 = getColorFromTarget(bufferedImage, targetSettings.getCaptchaTargets().getPointRed2());

        return isCloseColor(captchaWhiteColor, currentWhite1) &&
                isCloseColor(captchaWhiteColor, currentWhite2) &&
                isCloseColor(captchaWhiteColor, currentWhite3) &&
                isCloseColor(captchaRedColor, currentRed1) && isCloseColor(captchaRedColor, currentRed2);
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
        boolean hasBlue = Math.abs(blue1 - blue2) < 30;

        return hasRed && hasGreen && hasBlue;
    }

    private Color entityToColor (ColorEntity colorEntity) {
        return new Color(colorEntity.getValueR(), colorEntity.getValueG(), colorEntity.getValueB());
    }

    private Color getColorFromTarget(BufferedImage bufferedImage, TargetEntity target) {
        return new Color(bufferedImage.getRGB(target.getValueX(), target.getValueY()));
    }
}
