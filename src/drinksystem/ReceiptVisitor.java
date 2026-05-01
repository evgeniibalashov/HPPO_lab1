package drinksystem;

/**
 * Visitor для формирования текстового чека.
 * Собирает описания всех компонентов в одну строку.
 */
public final class ReceiptVisitor implements MenuVisitor {
    private final StringBuilder receiptBuilder = new StringBuilder();

    @Override
    public void visit(BaseDrink drink) {
        receiptBuilder.append("  ")
                .append(drink.getDescription())
                .append(" | ")
                .append(drink.getPrice())
                .append("р\n");
    }

    @Override
    public void visit(DrinkDecorator decorator) {
        receiptBuilder.append("  ")
                .append(decorator.getDescription())
                .append(" | ")
                .append(decorator.getPrice())
                .append("р\n");
    }

    @Override
    public void visit(Menu menu) {
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