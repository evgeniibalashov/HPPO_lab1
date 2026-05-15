package drinksystem;


public final class PriceVisitor implements MenuVisitor {
    private double totalPrice;

    @Override
    public void visit(DrinkComponent component) {
        totalPrice += component.getPrice();
    }

    @Override
    public void visit(Order order) {

        for (DrinkComponent item : order.getItemList()) {
            item.accept(this);
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}