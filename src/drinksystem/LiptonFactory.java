package drinksystem;


public final class LiptonFactory implements DrinkFactory {

    @Override
    public DrinkComponent createTea() {
        return new BlackTea("Lipton Black Tea", 120.0);
    }

    @Override
    public DrinkComponent createCoffee() {
        return new BlackCoffee("Lipton Coffee", 180.0);
    }
}