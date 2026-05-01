package drinksystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс Меню (Заказ).
 * Паттерн: Composite (Structural).
 *
 * Позволяет клиентам единообразно работать с одиночными объектами (напитками)
 * и их композициями (списком напитков в заказе).
 */

public final class Menu implements DrinkComponent {

    // Заголовок заказа
    private final String title;

    // Список компонентов.
    private final List<DrinkComponent> itemList = new ArrayList<>();

    /**
     * Конструктор создает пустое меню с заданным названием.
     */
    public Menu(String title) {
        this.title = Objects.requireNonNull(title, "Название меню не может быть null");
    }

    /**
     * Добавляет напиток или другой компонент в меню.
     */
    public void addItem(DrinkComponent item) {
        // Используем Objects.requireNonNull для защиты от null (стандарт 12/15)
        itemList.add(Objects.requireNonNull(item, "Добавляемый элемент не может быть null"));
    }

    /**
     * Возвращает список компонентов.
     */
    public List<DrinkComponent> getItemList() {
        return new ArrayList<>(itemList);
    }

    @Override
    public double getPrice() {
        double total = 0.0;
        // Проходим по всем элементам и суммируем их цены.
        // Это работает рекурсивно, если бы внутри были вложенные меню.
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