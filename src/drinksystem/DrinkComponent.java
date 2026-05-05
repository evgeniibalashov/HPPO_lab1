package drinksystem;


public interface DrinkComponent {

    double getPrice();

    String getDescription();

    void accept(MenuVisitor visitor);
}