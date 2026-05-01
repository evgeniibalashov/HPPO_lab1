package drinksystem;

import java.util.Objects;

/**
 * Базовый иммутабельный класс напитка.
 * Реализует интерфейс DrinkComponent, предоставляя основную функциональность.
 *
 * Иммутабельность достигнута через:
 * - final класс (запрещено наследование)
 * - private final поля (только для чтения)
 * - отсутствие сеттеров
 * - Objects.requireNonNull() в конструкторе (защита от null)
 *
 * Паттерн: основа для Decorator и Composite
 */
public class BaseDrink implements DrinkComponent {
    // Поля инициализируются при объявлении или в конструкторе (стандарт 13, 15)
    private final String name;
    private final double price;

    /**
     * Конструктор с параметрами в том же порядке, что и поля (стандарт 11).
     * @param name название напитка (не null)
     * @param price стоимость в рублях
     */
    public BaseDrink(String name, double price) {
        // Порядок параметров совпадает с порядком полей
        this.name = Objects.requireNonNull(name, "Название напитка не может быть null");
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return name;
    }

    @Override
    public void accept(MenuVisitor visitor) {
        // Двойная диспетчеризация для Visitor (пока MenuVisitor не создан, будет ошибка)
        // Временно можно закомментировать или создать заглушку
        visitor.visit(this);
    }

    /**
     * Геттер для name (иммутабельный класс — только чтение, нет сеттеров).
     */
    public String getName() {
        return name;
    }
}