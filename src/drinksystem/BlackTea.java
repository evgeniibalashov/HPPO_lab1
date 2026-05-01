package drinksystem;

/**
 * Чёрный чай — конкретная реализация BaseDrink.
 * Наследует всю базовую функциональность, не добавляя новой логики.
 */
public final class BlackTea extends BaseDrink {
    public BlackTea(String name, double price) {
        super(name, price);
    }
}