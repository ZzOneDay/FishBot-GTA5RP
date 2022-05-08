package home.developer.core.service.impl;

import home.developer.configuration.entitySettings.colorSettings.ColorSettings;
import home.developer.configuration.entitySettings.targetSettings.TargetSettings;
import home.developer.core.service.TargetCatcher;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@Scope("prototype")
@AllArgsConstructor
public class TargetCatcherImpl implements TargetCatcher {
    private final Robot robot;
    private final TargetSettings targetSettings;
    private final ColorSettings colorSettings;

    public boolean isReadyForFishing() {
        int fieldRGB = new Color(
                colorSettings.getMouseColors().getMouseRed().getValueR(),
                colorSettings.getMouseColors().getMouseRed().getValueG(),
                colorSettings.getMouseColors().getMouseRed().getValueB()
        ).getRGB();

        //Color of field
        int red1 = (fieldRGB >> 16) & 0xff;
        int green1 = (fieldRGB >> 8) & 0xff;
        int blue1 = (fieldRGB) & 0xff;

        //Current color of field
        int currentRGB = robot.getPixelColor(
                targetSettings.getMouseTargets().getMouseRed().getValueX(),
                targetSettings.getMouseTargets().getMouseRed().getValueY()
        ).getRGB();
        int red2 = (currentRGB >> 16) & 0xff;
        int green2 = (currentRGB >> 8) & 0xff;
        int blue2 = (currentRGB) & 0xff;

        boolean hasRed = Math.abs(red1 - red2) < 10;
        boolean hasGreen = Math.abs(green1 - green2) < 10;
        boolean hasBlue = Math.abs(blue1 - blue2) < 10;

        return hasRed && hasGreen && hasBlue;
    }
}
