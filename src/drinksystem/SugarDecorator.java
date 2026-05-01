package drinksystem;

/**
 * Декоратор для добавления сахара.
 */
public final class SugarDecorator extends DrinkDecorator {

    private static final double SUGAR_PRICE = 15.0;

    public SugarDecorator(DrinkComponent wrapped) {
        super(wrapped);
    }

    @Override
    public double getPrice() {
        return super.getPrice() + SUGAR_PRICE;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + сахар";
    }
}