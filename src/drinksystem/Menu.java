package drinksystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public final class Menu implements DrinkComponent {


    private final String title;


    private final List<DrinkComponent> itemList = new ArrayList<>();


    public Menu(String title) {
        this.title = Objects.requireNonNull(title, "Название меню не может быть null");
    }


    public void addItem(DrinkComponent item) {

        itemList.add(Objects.requireNonNull(item, "Добавляемый элемент не может быть null"));
    }


    public List<DrinkComponent> getItemList() {
        return new ArrayList<>(itemList);
    }

    @Override
    public double getPrice() {
        double total = 0.0;
        for (DrinkComponent item : itemList) {
            total += item.getPrice();
        }
        return total;
    }

    @Override
    public String getDescription() {
        return title;
    }

    @Override
    public void accept(MenuVisitor visitor) {
        visitor.visit(this);
    }

}