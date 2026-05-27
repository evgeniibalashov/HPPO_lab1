package drinksystem.concurrency;

import drinksystem.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DrinkServer implements Runnable {
    private final BlockingQueue<DrinkOrderRequest> orderQueue;
    private volatile boolean isRunning;

    public DrinkServer() {
        this.orderQueue = new LinkedBlockingQueue<>();
        this.isRunning = true;
    }

    public void addOrder(DrinkOrderRequest order) {
        try {
            orderQueue.put(order);
            System.out.println("Бариста принял: " + order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Бариста прерван при получении заказа.");
        }
    }

    public void stopServer() {
        isRunning = false;
    }

    @Override
    public void run() {
        System.out.println("Бариста запущен и готов принимать заказы.");
        while (isRunning || !orderQueue.isEmpty()) {
            try {
                DrinkOrderRequest order = orderQueue.take();
                processOrder(order);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Бариста прерван.");
                break;
            }
        }
        System.out.println("Бариста завершил работу.");
    }

    private void processOrder(DrinkOrderRequest order) {
        System.out.println("Бариста обрабатывает: " + order);

        DrinkFactory factory = (order.getBrandChoice() == 1) ? new LiptonFactory() : new NescafeFactory();

        DrinkComponent drink = (order.getDrinkChoice() == 1) ? factory.createTea() : factory.createCoffee();

        for (int decoratorCode : order.getDecorators()) {
            if (decoratorCode == 1) drink = new SugarDecorator(drink);
            else if (decoratorCode == 2) drink = new MilkDecorator(drink);
        }

        DiscountStrategy strategy = (order.getDiscountStrategy() == 2)
                ? new StudentDiscountStrategy()
                : new RegularPriceStrategy();
        double finalPrice = strategy.apply(drink.getPrice());

        // Печатаем чек напрямую (без Order)
        System.out.println("Бариста завершил обработку для " + order.getClientName() + ":");
        System.out.println("ЗАКАЗ для " + order.getClientName() + ": Заказ #" + order.getOrderId());
        System.out.println(drink.getDescription() + " | " + drink.getPrice() + "p");
        System.out.println("Цена со скидкой для " + order.getClientName() + ": " + finalPrice + "p");
        System.out.println("----------------------------------------");
    }
}