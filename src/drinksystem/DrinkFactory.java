package drinksystem;

/**
 * Абстрактная фабрика для создания напитков.
 * Паттерн: Abstract Factory (Creational).
 *
 * Определяет контракт для всех конкретных фабрик (Lipton, Nescafe).
 * Позволяет создавать семейства связанных объектов (чай и кофе одного бренда),
 * не привязываясь к конкретным классам продуктов.
 */
public interface DrinkFactory {

    DrinkComponent createTea();

    DrinkComponent createCoffee();
}