package drinksystem.concurrency;

import java.util.*;

public class DrinkClient implements Runnable {
    private final String clientName;
    private final DrinkServer server;
    private final int numberOfOrders;
    private final Random random;

    public DrinkClient(String clientName, DrinkServer server, int numberOfOrders) {
        this.clientName = clientName;
        this.server = server;
        this.numberOfOrders = numberOfOrders;
        this.random = new Random();
    }

    @Override
    public void run() {
        System.out.println(clientName + " зашёл в кофейню и начинает делать заказы.");

        for (int i = 1; i <= numberOfOrders; i++) {

            int brand = random.nextInt(2) + 1;           // 1 или 2
            int drink = random.nextInt(2) + 1;           // 1 или 2
            int numDecorators = random.nextInt(3);       // 0, 1 или 2 добавки
            List<Integer> decorators = new ArrayList<>();
            for (int j = 0; j < numDecorators; j++) {
                decorators.add(random.nextInt(2) + 1);   // 1 или 2
            }
            int strategy = random.nextInt(2) + 1;        // 1 или 2

            DrinkOrderRequest order = new DrinkOrderRequest(i, clientName, brand, drink, decorators, strategy);

            server.addOrder(order);

            try {
                Thread.sleep(random.nextInt(1500) + 1000); // 1–2.5 сек
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(clientName + " прерван при отправке заказов.");
                break;
            }
        }

        System.out.println(clientName + " завершил все заказы и ушёл из кофейни.");
    }
}