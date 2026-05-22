package drinksystem;

import java.util.Scanner;
import drinksystem.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class LabMain {

    private LabMain() {
    }

    public static void main(String[] args) {

        System.out.println("=== ЗАПУСК SPRING CONTEXT ===");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("=== SPRING ГОТОВ ===\n");

        DrinkOrderManager manager = context.getBean(DrinkOrderManager.class);

        Scanner scanner = new Scanner(System.in);
        manager.run(scanner);
        scanner.close();

        context.close();
    }

}