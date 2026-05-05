package drinksystem;


public final class StudentDiscountStrategy implements DiscountStrategy {
    private static final double DISCOUNT_RATE = 0.9;

    @Override
    public double apply(double total) {
        return total * DISCOUNT_RATE;
    }
}