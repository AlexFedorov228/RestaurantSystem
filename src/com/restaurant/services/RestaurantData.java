package com.restaurant.services;

import com.restaurant.models.Dish;
import com.restaurant.models.Ingredient;
import com.restaurant.models.Order;

import java.io.Serializable;
import java.util.List;

/**
 * Допоміжний клас-контейнер для збереження всіх даних системи.
 * Це необхідно, щоб серіалізувати всі списки в один файл.
 */
public class RestaurantData implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Ingredient> ingredients;
    private List<Dish> dishes;
    private List<Order> orders;

    // Конструктор
    public RestaurantData(List<Ingredient> ingredients, List<Dish> dishes, List<Order> orders) {
        this.ingredients = ingredients;
        this.dishes = dishes;
        this.orders = orders;
    }

    // Getters
    public List<Ingredient> getIngredients() { return ingredients; }
    public List<Dish> getDishes() { return dishes; }
    public List<Order> getOrders() { return orders; }
}