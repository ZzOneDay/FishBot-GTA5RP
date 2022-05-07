package home.developer;

import home.developer.configuration.ApplicationConfiguration;
import home.developer.discord.UpdateNameService;
import home.developer.service.MouseService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StartApplication {
    public static void main(String[] args) throws InterruptedException, AWTException, IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        RunningProcess runningProcess = context.getBean(RunningProcess.class);
        runningProcess.run();





//        while (true) {
//            MouseService mouseService = context.getBean(MouseService.class);
//            Robot robot = context.getBean(Robot.class);
//            System.out.println(robot.getPixelColor(mouseService.getCurrentPosition().x, mouseService.getCurrentPosition().y));
//            System.out.println(mouseService.getCurrentPosition());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }


//        process.needStopping.point.valueX=1843
//        process.needStopping.point.valueY=1069
//        MouseService mouseService = context.getBean(MouseService.class);
//        Robot robot = context.getBean(Robot.class);
//        Thread.sleep(10000);
//
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        Rectangle screenRectangle = new Rectangle(screenSize);
//        BufferedImage image = robot.createScreenCapture(screenRectangle);
//        Point point = context.getBean("startingYellowPoint", Point.class);
//        Color checkColor = context.getBean("startingYellowColor", Color.class);
//        Color currentColor = new Color(image.getRGB(point.x, point.y));
//        Color color = Color.GREEN;
//        for (int i = -20; i < 20; i++) {
//            image.setRGB((int) point.getX() + i, (int) point.getY(), color.getRGB());
//        }
//
//        for (int i = -20; i < 20; i++) {
//            image.setRGB((int) point.getX(), (int) point.getY() + i, color.getRGB());
//        }
//        boolean isCloseColor = isCloseColor(checkColor, currentColor);
//        System.out.println("123");
    }

    private static boolean isCloseColor(Color color1, Color color2) {
        int colorValue1 = color1.getRGB();
        int colorValue2 = color2.getRGB();

        int red1 = (colorValue1 >> 16) & 0xff;
        int green1 = (colorValue1 >> 8) & 0xff;
        int blue1 = (colorValue1) & 0xff;

        int red2 = (colorValue2 >> 16) & 0xff;
        int green2 = (colorValue2 >> 8) & 0xff;
        int blue2 = (colorValue2) & 0xff;

        boolean hasRed = Math.abs(red1 - red2) < 10;
        boolean hasGreen = Math.abs(green1 - green2) < 10;;
        boolean hasBlue = Math.abs(blue1 - blue2) < 10;;
        return hasRed && hasGreen && hasBlue;
    }
}
