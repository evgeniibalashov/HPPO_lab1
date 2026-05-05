package drinksystem;


public final class NescafeFactory implements DrinkFactory {

    @Override
    public DrinkComponent createTea() {

        return new GreenTea("Nescafe Green Tea", 150.0);
    }

    @Override
    public DrinkComponent createCoffee() {

        return new BlackCoffee("Nescafe Coffee", 200.0);
    }
}