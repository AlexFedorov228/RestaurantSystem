package com.restaurant.interfaces;

import java.util.List;

/**
 * Узагальнений (Generic) інтерфейс для керування сутностями. [cite: 214, 241]
 * Визначає стандартні методи CRUD (Create, Read, Update, Delete).
 * @param <T> Тип сутності, якою керує менеджер (наприклад, Dish, Ingredient).
 */
public interface IManageable<T> {
    
    /**
     * Додає нову сутність до сховища.
     * @param entity Сутність для додавання.
     */
    void add(T entity); // [cite: 232]

    /**
     * Отримує сутність за її унікальним ID.
     * @param id ID сутності.
     * @return Сутність або null, якщо не знайдено.
     */
    T getById(int id);

    /**
     * Повертає список всіх сутностей.
     * @return List<T> список сутностей.
     */
    List<T> getAll(); // [cite: 232]

    /**
     * Оновлює існуючу сутність.
     * @param id ID сутності для оновлення.
     * @param entity Нові дані для сутності.
     */
    void update(int id, T entity); // [cite: 232]

    /**
     * Видаляє сутність за її ID.
     * @param id ID сутності для видалення.
     * @return true, якщо видалення успішне, інакше false.
     */
    boolean remove(int id); // [cite: 232]
}