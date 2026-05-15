package drinksystem;


public final class ReceiptVisitor implements MenuVisitor {
    private final StringBuilder receiptBuilder = new StringBuilder();

    @Override
    public void visit(DrinkComponent component) {
        receiptBuilder.append("  ")
                .append(component.getDescription())
                .append(" | ")
                .append(component.getPrice())
                .append("р\n");
    }

    @Override
    public void visit(Order menu) {
        receiptBuilder.append("ЗАКАЗ: ")
                .append(menu.getDescription())
                .append("\n");

        for (DrinkComponent item : menu.getItemList()) {
            item.accept(this);
        }
    }

    public String printReceipt() {
        return receiptBuilder.toString();
    }
}