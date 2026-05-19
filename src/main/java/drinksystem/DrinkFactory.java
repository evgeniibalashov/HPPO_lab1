package drinksystem;

public interface DrinkFactory {

    DrinkComponent createTea();

    DrinkComponent createCoffee();
}