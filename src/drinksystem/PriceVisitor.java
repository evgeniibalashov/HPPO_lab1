package drinksystem;

/**
 * Visitor для подсчета итоговой стоимости.
 * Проходит по всей структуре (меню -> напитки -> добавки) и суммирует цены.
 */
public final class PriceVisitor implements MenuVisitor {
    private double totalPrice;

    @Override
    public void visit(BaseDrink drink) {
        totalPrice += drink.getPrice();
    }

    @Override
    public void visit(DrinkDecorator decorator) {
        totalPrice += decorator.getPrice();
    }

    @Override
    public void visit(Menu menu) {
        // Для меню мы не добавляем цену самого меню, а проходимся по его элементам.
        // Элементы сами вызовут visit для себя.
        for (DrinkComponent item : menu.getItemList()) {
            item.accept(this);
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}