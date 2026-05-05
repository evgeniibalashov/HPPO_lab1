package drinksystem;


public final class NescafeFactory implements DrinkFactory {

    @Override
    public DrinkComponent createTea() {
        // Nescafe делает Зеленый чай за 150 рублей
        return new GreenTea("Nescafe Green Tea", 150.0);
    }

    @Override
    public DrinkComponent createCoffee() {
        // Nescafe делает Кофе за 200 рублей
        return new BlackCoffee("Nescafe Coffee", 200.0);
    }
}