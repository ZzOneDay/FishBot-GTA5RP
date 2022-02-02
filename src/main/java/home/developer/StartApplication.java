package home.developer;

import home.developer.configuration.ApplicationConfiguration;
import home.developer.service.MouseService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class StartApplication {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        Runner runner = context.getBean(Runner.class);

        while (true) {
            System.out.println("Start");
            runner.run();
            Thread.sleep(3000);
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
