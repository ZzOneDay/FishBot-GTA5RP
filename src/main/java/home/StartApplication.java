package home;

import home.application.MainFrame;
import home.fishing.StartCore;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

public class StartApplication {
    public static void main(String[] args) throws InterruptedException, AWTException, IOException {
        MainFrame mainFrame = new MainFrame();






//        // Запуск процесса рыбалки
//        StartCore startCore = new StartCore();
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            String command = scanner.nextLine();
//
//            if (command.equals("start")) {
//                boolean result = startCore.run();
//                if (result) {
//                    System.out.println("Запуск прошел успешно");
//                } else {
//                    System.out.println("нихера не работает");
//                }
//            }
//
//            if (command.equals("stop")) {
//                startCore.stop();
//            }
//        }
    }
}
