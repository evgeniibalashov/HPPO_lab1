package drinksystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public final class LabMain {

    private LabMain() {
    }

    static void main() {

        Scanner userInputScanner = new Scanner(System.in);

        runApplication(userInputScanner);

        userInputScanner.close();
    }

    private static void runApplication(Scanner scanner) {
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

            DrinkFactory selectedBrandFactory = (brandChoiceId == 1) ? new LiptonFactory() : new NescafeFactory();

            DrinkComponent baseDrinkComponent = selectBaseDrink(scanner, selectedBrandFactory);
            DrinkComponent finalDrinkComponent = applyDecorators(scanner, baseDrinkComponent);

            orderItemList.add(finalDrinkComponent);
            System.out.println("Добавлено в заказ: " + finalDrinkComponent.getDescription() + " | " + finalDrinkComponent.getPrice() + "р");

            System.out.print("Добавить еще напиток? (да/нет): ");
            String userResponse = scanner.nextLine();
            isOrderingActiveFlag = userResponse.equalsIgnoreCase("да");
        }

        if (!orderItemList.isEmpty()) {
            processOrder(orderItemList, scanner);
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

    private static void processOrder(List<DrinkComponent> orderItemList, Scanner scanner) {
        System.out.println(" Оформление заказа ");

        // Composite

        Order finalUserMenu = new Order("Заказ #" + System.currentTimeMillis());
        for (DrinkComponent item : orderItemList) {
            finalUserMenu.addItem(item);
        }

        // Visitor
        ReceiptVisitor receiptPrinter = new ReceiptVisitor();
        finalUserMenu.accept(receiptPrinter);
        System.out.println("Ваш чек:");
        System.out.println(receiptPrinter.printReceipt());

        PriceVisitor priceCalculator = new PriceVisitor();
        finalUserMenu.accept(priceCalculator);
        double rawPrice = priceCalculator.getTotalPrice();

        // Strategy

        System.out.println("Выберите тип клиента:");
        System.out.println("1. Обычный");
        System.out.println("2. Студент (скидка 10%)");
        System.out.print("Ваш выбор: ");

        int clientTypeId = scanner.nextInt();
        scanner.nextLine();


        DiscountStrategy pricingStrategy = (clientTypeId == 2)
                ? new StudentDiscountStrategy()
                : new RegularPriceStrategy();

        double finalPrice = pricingStrategy.apply(rawPrice);

        System.out.println("ИТОГО К ОПЛАТЕ:");
        System.out.println("Базовая стоимость: " + rawPrice + "р");
        System.out.println("Цена со скидкой: " + finalPrice + "р");
    }
}