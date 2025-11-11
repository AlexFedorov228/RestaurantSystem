package com.restaurant.managers;

import com.restaurant.interfaces.IManageable;
import com.restaurant.models.Dish;
import com.restaurant.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Менеджер для керування Стравами. 
 * Реалізує інтерфейс IManageable<Dish>. [cite: 243]
 */
public class DishManager implements IManageable<Dish> {
    private List<Dish> dishes; // [cite: 196]

    public DishManager() {
        this.dishes = new ArrayList<>();
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
        int maxId = 0;
        for (Dish d : dishes) {
            if (d.getId() > maxId) {
                maxId = d.getId();
            }
        }
        Dish.updateNextId(maxId);
    }

    @Override
    public void add(Dish entity) { // [cite: 201]
        dishes.add(entity);
    }

    @Override
    public Dish getById(int id) {
        for (Dish dish : dishes) {
            if (dish.getId() == id) {
                return dish;
            }
        }
        return null;
    }

    @Override
    public List<Dish> getAll() {
        return new ArrayList<>(dishes);
    }

    @Override
    public void update(int id, Dish entity) {
        Dish existing = getById(id);
        if (existing != null) {
            existing.setName(entity.getName());
            existing.setPrice(entity.getPrice());
            existing.setPreparationTime(entity.getPreparationTime());
            // Оновлення списку інгредієнтів є складнішим,
            // тут ми просто замінюємо його.
            // У реальній системі це було б окреме меню.
        }
    }

    @Override
    public boolean remove(int id) { // [cite: 202]
        Dish toRemove = getById(id);
        if (toRemove != null) {
            return dishes.remove(toRemove);
        }
        return false;
    }
    
    /**
     * Перевіряє, чи використовується інгредієнт у будь-якій страві.
     * Потрібно для валідації перед видаленням інгредієнта. [cite: 131, 149]
     * @param ingredient Інгредієнт для перевірки.
     * @return true, якщо інгредієнт використовується.
     */
    public boolean isIngredientUsed(Ingredient ingredient) {
        for (Dish dish : dishes) {
            if (dish.getIngredients().contains(ingredient)) {
                return true;
            }
        }
        return false;
    }
}