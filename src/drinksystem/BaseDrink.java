package drinksystem;

import java.util.Objects;

/**
 * Реализует интерфейс DrinkComponent
 */
public class BaseDrink implements DrinkComponent {
    private final String name;
    private final double price;

    public BaseDrink(String name, double price) {
        this.name = Objects.requireNonNull(name, "Название напитка не может быть null");
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return name;
    }

    @Override
    public void accept(MenuVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }
}