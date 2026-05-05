package drinksystem;


public interface MenuVisitor {
    void visit(BaseDrink drink);
    void visit(DrinkDecorator decorator);
    void visit(Menu menu);
}