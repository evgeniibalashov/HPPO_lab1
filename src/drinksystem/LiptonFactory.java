package drinksystem;


public final class LiptonFactory implements DrinkFactory {

    @Override
    public DrinkComponent createTea() {
        // Lipton делает Черный чай за 120 рублей
        return new BlackTea("Lipton Black Tea", 120.0);
    }

    @Override
    public DrinkComponent createCoffee() {
        // Lipton делает Кофе за 180 рублей
        return new BlackCoffee("Lipton Coffee", 180.0);
    }
}