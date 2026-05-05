package drinksystem;


public interface DiscountStrategy {
    double apply(double total);
}