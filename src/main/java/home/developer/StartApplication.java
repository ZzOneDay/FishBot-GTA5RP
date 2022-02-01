package home.developer;

import home.developer.configuration.ApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class StartApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        Runner runner = context.getBean(Runner.class);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            runner.run();
        }
    }
}
