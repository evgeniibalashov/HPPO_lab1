package drinksystem;

import java.util.Objects;

/**
 * Абстрактный декоратор.
 * Реализует DrinkComponent, чтобы декоратор можно было использовать везде, где ожидается напиток.
 * Хранит ссылку на обернутый компонент (wrapped) и делегирует ему базовые вызовы.
 */
public abstract class DrinkDecorator implements DrinkComponent {
    // Поле final, так как ссылка на обернутый объект не должна меняться после создания
    protected final DrinkComponent wrapped;

    public DrinkDecorator(DrinkComponent wrapped) {
        this.wrapped = Objects.requireNonNull(wrapped, "Оборачиваемый компонент не может быть null");
    }

    @Override
    public double getPrice() {
        // По умолчанию возвращаем цену обернутого объекта.
        // Конкретные декораторы переопределят этот метод, добавляя свою стоимость.
        return wrapped.getPrice();
    }

    @Override
    public String getDescription() {
        // По умолчанию возвращаем описание обернутого объекта.
        return wrapped.getDescription();
    }

    @Override
    public void accept(MenuVisitor visitor) {
        visitor.visit(this);
    }

    public DrinkComponent getWrapped() {
        return wrapped;
    }
}