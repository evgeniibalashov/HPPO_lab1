package drinksystem;

/**
 * Стратегия обычной цены (без скидок).
 */
public final class RegularPriceStrategy implements DiscountStrategy {
    @Override
    public double apply(double total) {
        return total;
    }
}