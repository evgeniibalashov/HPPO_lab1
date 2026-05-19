package drinksystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import drinksystem.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class LabMain {

    private LabMain() {
    }

    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК SPRING CONTEXT ===");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("=== SPRING ГОТОВ ===\n");

        LiptonFactory liptonFactory = context.getBean(LiptonFactory.class);
        NescafeFactory nescafeFactory = context.getBean(NescafeFactory.class);
        RegularPriceStrategy regularStrategy = context.getBean(RegularPriceStrategy.class);
        StudentDiscountStrategy studentStrategy = context.getBean(StudentDiscountStrategy.class);
        PriceVisitor priceVisitor = context.getBean(PriceVisitor.class);
        ReceiptVisitor receiptVisitor = context.getBean(ReceiptVisitor.class);

        Scanner userInputScanner = new Scanner(System.in);
        runApplication(userInputScanner, liptonFactory, nescafeFactory,
                regularStrategy, studentStrategy, priceVisitor, receiptVisitor);
        userInputScanner.close();

        ((AnnotationConfigApplicationContext) context).close();
    }

    private static void runApplication(Scanner scanner,
                                       LiptonFactory liptonFactory,
                                       NescafeFactory nescafeFactory,
                                       RegularPriceStrategy regularStrategy,
                                       StudentDiscountStrategy studentStrategy,
                                       PriceVisitor priceVisitor,
                                       ReceiptVisitor receiptVisitor) {
        System.out.println(" Система заказов напитков ");

        List<DrinkComponent> orderItemList = new ArrayList<>();

        boolean isOrderingActiveFlag = true;

        while (isOrderingActiveFlag) {
            System.out.println("Выберите производителя:");
            System.out.println("1. Lipton");
            System.out.println("2. Nescafe");
            System.out.print("Ваш выбор (1-2): ");

            int brandChoiceId = scanner.nextInt();
            scanner.nextLine();

            // Abstract Factory

            DrinkFactory selectedBrandFactory = (brandChoiceId == 1) ? liptonFactory : nescafeFactory;

            DrinkComponent baseDrinkComponent = selectBaseDrink(scanner, selectedBrandFactory);
            DrinkComponent finalDrinkComponent = applyDecorators(scanner, baseDrinkComponent);

            orderItemList.add(finalDrinkComponent);
            System.out.println("Добавлено в заказ: " + finalDrinkComponent.getDescription() + " | " + finalDrinkComponent.getPrice() + "р");

            System.out.print("Добавить еще напиток? (да/нет): ");
            String userResponse = scanner.nextLine();
            isOrderingActiveFlag = userResponse.equalsIgnoreCase("да");
        }

        if (!orderItemList.isEmpty()) {
            processOrder(orderItemList, scanner, priceVisitor, receiptVisitor, regularStrategy, studentStrategy);
        } else {
            System.out.println("Заказ пуст. До свидания!");
        }
    }


    private static DrinkComponent selectBaseDrink(Scanner scanner, DrinkFactory factory) {
        System.out.println( "Выберите напиток:");
        System.out.println("1. Чай");
        System.out.println("2. Кофе");
        System.out.print("Ваш выбор: ");

        int drinkChoiceId = scanner.nextInt();
        scanner.nextLine();

        return (drinkChoiceId == 1) ? factory.createTea() : factory.createCoffee();
    }



      // Decorator.

    private static DrinkComponent applyDecorators(Scanner scanner, DrinkComponent currentDrink) {
        DrinkComponent decoratedDrink = currentDrink;
        boolean isAddingIngredientsFlag = true;

        while (isAddingIngredientsFlag) {
            System.out.println("Добавить ингредиент?");
            System.out.println("1. Сахар (+15р)");
            System.out.println("2. Молоко (+30р)");
            System.out.println("0. Нет, оставить как есть");
            System.out.print("Ваш выбор: ");

            int ingredientChoiceId = scanner.nextInt();
            scanner.nextLine();

            if (ingredientChoiceId == 0) {
                isAddingIngredientsFlag = false;
            } else if (ingredientChoiceId == 1) {
                // Decorator оборачивает текущий напиток, добавляя функциональность
                decoratedDrink = new SugarDecorator(decoratedDrink);
                System.out.println("   + Сахар. Новая цена: " + decoratedDrink.getPrice() + "р");
            } else if (ingredientChoiceId == 2) {
                decoratedDrink = new MilkDecorator(decoratedDrink);
                System.out.println("   + Молоко. Новая цена: " + decoratedDrink.getPrice() + "р");
            }
        }
        return decoratedDrink;
    }


      // Composite, Visitor и Strategy.

    private static void processOrder(List<DrinkComponent> orderItemList, Scanner scanner,
                                     PriceVisitor priceVisitor, ReceiptVisitor receiptVisitor,
                                     RegularPriceStrategy regularStrategy, StudentDiscountStrategy studentStrategy) {
        System.out.println(" Оформление заказа ");

        // Composite

        Order finalUserMenu = new Order("Заказ #" + System.currentTimeMillis());
        for (DrinkComponent item : orderItemList) {
            finalUserMenu.addItem(item);
        }

        // Visitor
        finalUserMenu.accept(receiptVisitor);
        System.out.println("Ваш чек:");
        System.out.println(receiptVisitor.printReceipt());

        finalUserMenu.accept(priceVisitor);
        double rawPrice = priceVisitor.getTotalPrice();

        // Strategy

        System.out.println("Выберите тип клиента:");
        System.out.println("1. Обычный");
        System.out.println("2. Студент (скидка 10%)");
        System.out.print("Ваш выбор: ");

        int clientTypeId = scanner.nextInt();
        scanner.nextLine();


        DiscountStrategy pricingStrategy = (clientTypeId == 2)
                ? studentStrategy
                : regularStrategy;

        double finalPrice = pricingStrategy.apply(rawPrice);

        System.out.println("ИТОГО К ОПЛАТЕ:");
        System.out.println("Базовая стоимость: " + rawPrice + "р");
        System.out.println("Цена со скидкой: " + finalPrice + "р");
    }
}