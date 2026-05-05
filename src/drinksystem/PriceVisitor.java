package drinksystem;


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

        for (DrinkComponent item : menu.getItemList()) {
            item.accept(this);
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}