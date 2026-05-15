package drinksystem;

import java.util.Objects;

public abstract class DrinkDecorator implements DrinkComponent {

    protected final DrinkComponent wrapped;

    public DrinkDecorator(DrinkComponent wrapped) {
        this.wrapped = Objects.requireNonNull(wrapped, "Оборачиваемый компонент не может быть null");
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice();
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription();
    }

    @Override
    public void accept(MenuVisitor visitor) {
        visitor.visit((DrinkComponent) this);
    }
}