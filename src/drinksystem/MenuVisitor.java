package drinksystem;

/**
 * Интерфейс для паттерна Visitor.
 * Позволяет добавлять новые операции над структурой объектов (напитками и меню)
 * без изменения самих классов этих объектов.
 */
public interface MenuVisitor {
    void visit(BaseDrink drink);
    void visit(DrinkDecorator decorator);
    void visit(Menu menu);
}