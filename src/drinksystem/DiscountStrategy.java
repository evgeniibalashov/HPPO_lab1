package drinksystem;

/**
 * Стратегия применения скидки.
 * Паттерн: Strategy (Behavior).
 * Позволяет менять алгоритм расчета цены на лету (например, студент и обычный клиент).
 */
public interface DiscountStrategy {
    double apply(double total);
}