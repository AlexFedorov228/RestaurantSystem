package com.restaurant.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель, що представляє Страву. [cite: 168]
 * Кожна страва складається зі списку інгредієнтів.
 */
public class Dish implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name; // [cite: 171]
    private List<Ingredient> ingredients; // [cite: 172]
    private double price; // [cite: 173]
    private int preparationTime; // (З діаграми класів) 

    private static int nextId = 1;

    public Dish(String name, double price, int preparationTime) {
        this.id = nextId++;
        this.name = name;
        this.price = price;
        this.preparationTime = preparationTime;
        this.ingredients = new ArrayList<>();
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public int getPreparationTime() { return preparationTime; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setPreparationTime(int preparationTime) { this.preparationTime = preparationTime; }

    /**
     * Додає інгредієнт до складу страви. [cite: 175]
     * @param ingredient Інгредієнт для додавання.
     */
    public void addIngredient(Ingredient ingredient) {
        if (ingredient != null) {
            this.ingredients.add(ingredient);
        }
    }

    /**
     * Видаляє інгредієнт зі складу страви. [cite: 176]
     * @param ingredient Інгредієнт для видалення.
     */
    public void removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
    }
    
    public static void updateNextId(int maxId) {
        nextId = maxId + 1;
    }

    // Метод для виведення інформації про страву [cite: 177]
    @Override
    public String toString() {
        return String.format("ID: %d, Страва: %s, Ціна: %.2f грн, Час: %d хв",
                id, name, price, preparationTime);
    }
}