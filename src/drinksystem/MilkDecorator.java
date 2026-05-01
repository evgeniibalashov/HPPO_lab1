package drinksystem;

/**
 * Декоратор для добавления молока.
 */
public final class MilkDecorator extends DrinkDecorator {
    private static final double MILK_PRICE = 30.0;

    public MilkDecorator(DrinkComponent wrapped) {
        super(wrapped);
    }

    @Override
    public double getPrice() {
        return super.getPrice() + MILK_PRICE;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + молоко";
    }
}