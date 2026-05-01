package drinksystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Objects;

/**
 * Архитектура приложения демонстрирует 5 паттернов:
 * 1. Abstract Factory (выбор бренда)
 * 2. Decorator (добавление сахара/молока)
 * 3. Composite (сборка заказа в Меню)
 * 4. Visitor (расчет цены и печать чека)
 * 5. Strategy (выбор скидки)
 */
public final class LabMain {

    private LabMain() {
    }

    public static void main(String[] args) {

        Scanner userInputScanner = new Scanner(System.in);

        runApplication(userInputScanner);

        userInputScanner.close();
    }

    private static void runApplication(Scanner scanner) {
        System.out.println(" Система заказов напитков ");

        // Список для хранения выбранных напитков перед формированием заказа
        List<DrinkComponent> orderItemList = new ArrayList<>();


        boolean isOrderingActiveFlag = true;

        while (isOrderingActiveFlag) {
            System.out.println("Выберите производителя:");
            System.out.println("1. Lipton");
            System.out.println("2. Nescafe");
            System.out.print("Ваш выбор (1-2): ");

            int brandChoiceId = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера после nextInt()

            // 1. CREATION: Abstract Factory
            // Фабрика скрывает создание конкретных классов (BlackTea, GreenTea)
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

    /**
     * Выбор базового напитка (Чай или Кофе).
     */
    private static DrinkComponent selectBaseDrink(Scanner scanner, DrinkFactory factory) {
        System.out.println( "Выберите напиток:");
        System.out.println("1. Чай");
        System.out.println("2. Кофе");
        System.out.print("Ваш выбор: ");

        int drinkChoiceId = scanner.nextInt();
        scanner.nextLine();

        // Фабрика возвращает нужный объект
        return (drinkChoiceId == 1) ? factory.createTea() : factory.createCoffee();
    }

    /**
     * Цикл добавления добавок к напитку.
     * Демонстрация паттерна Decorator.
     */
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

    /**
     * Формирование итогового заказа, расчет и вывод.
     * Демонстрация Composite, Visitor и Strategy.
     */
    private static void processOrder(List<DrinkComponent> orderItemList, Scanner scanner) {
        System.out.println(" Оформление заказа ");

        // 3. STRUCTURAL: Composite
        // Создаем единый объект Menu, который объединяет все напитки
        Menu finalUserMenu = new Menu("Заказ #" + System.currentTimeMillis());
        for (DrinkComponent item : orderItemList) {
            finalUserMenu.addItem(item);
        }

        // 4. BEHAVIORAL: Visitor
        // Используем Visitor для генерации чека (отделяем форматирование от данных)
        ReceiptVisitor receiptPrinter = new ReceiptVisitor();
        finalUserMenu.accept(receiptPrinter);
        System.out.println("Ваш чек:");
        System.out.println(receiptPrinter.printReceipt());

        // Используем Visitor для подсчета суммы
        PriceVisitor priceCalculator = new PriceVisitor();
        finalUserMenu.accept(priceCalculator);
        double rawPrice = priceCalculator.getTotalPrice();

        // 5. BEHAVIORAL: Strategy
        // Выбор алгоритма расчета цены (скидка или нет)
        System.out.println("Выберите тип клиента:");
        System.out.println("1. Обычный");
        System.out.println("2. Студент (скидка 10%)");
        System.out.print("Ваш выбор: ");

        int clientTypeId = scanner.nextInt();
        scanner.nextLine();

        // Подменяем стратегию на лету без изменения кода расчета
        DiscountStrategy pricingStrategy = (clientTypeId == 2)
                ? new StudentDiscountStrategy()
                : new RegularPriceStrategy();

        double finalPrice = pricingStrategy.apply(rawPrice);

        System.out.println("ИТОГО К ОПЛАТЕ:");
        System.out.println("Базовая стоимость: " + rawPrice + "р");
        System.out.println("Цена со скидкой: " + finalPrice + "р");
    }
}