package drinksystem;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Component
public class DrinkOrderManager {

    private final LiptonFactory liptonFactory;
    private final NescafeFactory nescafeFactory;
    private final PriceVisitor priceVisitor;
    private final ReceiptVisitor receiptVisitor;
    private final RegularPriceStrategy regularStrategy;
    private final StudentDiscountStrategy studentStrategy;

    public DrinkOrderManager(LiptonFactory liptonFactory,
                             NescafeFactory nescafeFactory,
                             PriceVisitor priceVisitor,
                             ReceiptVisitor receiptVisitor,
                             RegularPriceStrategy regularStrategy,
                             StudentDiscountStrategy studentStrategy) {
        this.liptonFactory = liptonFactory;
        this.nescafeFactory = nescafeFactory;
        this.priceVisitor = priceVisitor;
        this.receiptVisitor = receiptVisitor;
        this.regularStrategy = regularStrategy;
        this.studentStrategy = studentStrategy;
    }

    public void run(Scanner scanner) {
        System.out.println("Система заказов напитков");

        List<DrinkComponent> orderItemList = new ArrayList<>();
        boolean isOrderingActiveFlag = true;

        while (isOrderingActiveFlag) {
            System.out.println("Выберите производителя:");
            System.out.println("1. Lipton");
            System.out.println("2. Nescafe");
            System.out.print("Ваш выбор (1-2): ");

            int brandChoiceId = scanner.nextInt();
            scanner.nextLine();


            DrinkFactory selectedBrandFactory = (brandChoiceId == 1) ? liptonFactory : nescafeFactory;

            DrinkComponent baseDrinkComponent = selectBaseDrink(scanner, selectedBrandFactory);
            DrinkComponent finalDrinkComponent = applyDecorators(scanner, baseDrinkComponent);

            orderItemList.add(finalDrinkComponent);
            System.out.println("Добавлено в заказ: " + finalDrinkComponent.getDescription() + " | " + finalDrinkComponent.getPrice() + "p");

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

    private DrinkComponent selectBaseDrink(Scanner scanner, DrinkFactory factory) {
        System.out.println("Выберите напиток:");
        System.out.println("1. Чай");
        System.out.println("2. Кофе");
        System.out.print("Ваш выбор: ");

        int drinkChoiceId = scanner.nextInt();
        scanner.nextLine();

        return (drinkChoiceId == 1) ? factory.createTea() : factory.createCoffee();
    }

    private DrinkComponent applyDecorators(Scanner scanner, DrinkComponent currentDrink) {
        DrinkComponent decoratedDrink = currentDrink;
        boolean isAddingIngredientsFlag = true;

        while (isAddingIngredientsFlag) {
            System.out.println("Добавить ингредиент?");
            System.out.println("1. Сахар (+15p)");
            System.out.println("2. Молоко (+30p)");
            System.out.println("0. Нет, оставить как есть");
            System.out.print("Ваш выбор: ");

            int ingredientChoiceId = scanner.nextInt();
            scanner.nextLine();

            if (ingredientChoiceId == 0) {
                isAddingIngredientsFlag = false;
            } else if (ingredientChoiceId == 1) {
                decoratedDrink = new SugarDecorator(decoratedDrink);
                System.out.println(" + Сахар. Новая цена: " + decoratedDrink.getPrice() + "p");
            } else if (ingredientChoiceId == 2) {
                decoratedDrink = new MilkDecorator(decoratedDrink);
                System.out.println(" + Молоко. Новая цена: " + decoratedDrink.getPrice() + "p");
            }
        }
        return decoratedDrink;
    }

    private void processOrder(List<DrinkComponent> orderItemList, Scanner scanner) {
        System.out.println("Оформление заказа");

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

        DiscountStrategy pricingStrategy = (clientTypeId == 2) ? studentStrategy : regularStrategy;

        double finalPrice = pricingStrategy.apply(rawPrice);

        System.out.println("ИТОГО К ОПЛАТЕ:");
        System.out.println("Базовая стоимость: " + rawPrice + "p");
        System.out.println("Цена со скидкой: " + finalPrice + "p");
    }
}