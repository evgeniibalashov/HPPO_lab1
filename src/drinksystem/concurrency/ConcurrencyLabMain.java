package drinksystem.concurrency;

import java.util.ArrayList;
import java.util.List;

public class ConcurrencyLabMain {
    public static void main(String[] args) throws InterruptedException {

        DrinkServer barista = new DrinkServer();
        Thread serverThread = new Thread(barista, "Бариста");
        serverThread.start();

        int numberOfClients = 3;
        int ordersPerClient = 5;

        List<Thread> clientThreads = new ArrayList<>();

        for (int i = 1; i <= numberOfClients; i++) {
            String clientName = "Гость-" + i;
            DrinkClient client = new DrinkClient(clientName, barista, ordersPerClient);
            Thread clientThread = new Thread(client, clientName);
            clientThreads.add(clientThread);
            clientThread.start();
        }

        for (Thread t : clientThreads) {
            t.join();
        }

        Thread.sleep(2000); //

        barista.stopServer();
        serverThread.interrupt(); //
        serverThread.join();      //

        System.out.println("\n=== Симуляция кофейни завершена ===");
    }
}