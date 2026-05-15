package drinksystem;


public interface MenuVisitor {
    void visit(DrinkComponent component);
    void visit(Order order);
}