package drinksystem;

import org.springframework.stereotype.Component;

@Component
public class RegularPriceStrategy implements DiscountStrategy {
    @Override
    public double apply(double total) {
        return total;
    }
}