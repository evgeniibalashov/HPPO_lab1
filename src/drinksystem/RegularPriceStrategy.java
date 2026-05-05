package drinksystem;


public final class RegularPriceStrategy implements DiscountStrategy {
    @Override
    public double apply(double total) {
        return total;
    }
}