package home.developer;

import home.developer.configuration.ApplicationConfiguration;
import home.developer.service.MouseService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import java.awt.*;

public class StartApplication {
    public static void main(String[] args) throws InterruptedException, AWTException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        Runner runner = context.getBean(Runner.class);

        while (true) {
            System.out.println("Start");
            runner.run();
            System.out.println("Pause");
        }

//        while (true) {
//            MouseService mouseService = context.getBean(MouseService.class);
//            System.out.println(mouseService.getCurrentPosition());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
