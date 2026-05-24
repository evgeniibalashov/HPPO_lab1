package drinksystem.concurrency;

import java.util.List;

public class DrinkOrderRequest {
    private final int orderId;
    private final String clientName;
    private final int brandChoice;
    private final int drinkChoice;
    private final List<Integer> decorators;
    private final int discountStrategy;

    public DrinkOrderRequest(int orderId, String clientName, int brandChoice,
                             int drinkChoice, List<Integer> decorators, int discountStrategy) {
        this.orderId = orderId;
        this.clientName = clientName;
        this.brandChoice = brandChoice;
        this.drinkChoice = drinkChoice;
        this.decorators = decorators;
        this.discountStrategy = discountStrategy;
    }

    public int getOrderId() { return orderId; }
    public String getClientName() { return clientName; }
    public int getBrandChoice() { return brandChoice; }
    public int getDrinkChoice() { return drinkChoice; }
    public List<Integer> getDecorators() { return decorators; }
    public int getDiscountStrategy() { return discountStrategy; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[")
                .append(clientName)
                .append("] Заказ #")
                .append(orderId)
                .append(": ");

        sb.append(brandChoice == 1 ? "Lipton " : "Nescafe ");

        sb.append(drinkChoice == 1 ? "Чай" : "Кофе");

        if (!decorators.isEmpty()) {
            sb.append(" + ");
            for (int i = 0; i < decorators.size(); i++) {
                if (i > 0) sb.append(" + ");
                sb.append(decorators.get(i) == 1 ? "сахар" : "молоко");
            }
        }

        sb.append(" | ").append(discountStrategy == 2 ? "Студентская скидка" : "Обычная цена");

        return sb.toString();
    }
}