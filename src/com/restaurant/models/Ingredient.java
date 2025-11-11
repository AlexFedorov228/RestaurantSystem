package com.restaurant.models;

import java.io.Serializable;

/**
 * Модель, що представляє Інгредієнт. [cite: 157]
 * Реалізує Serializable для збереження/завантаження даних у файл.
 */
public class Ingredient implements Serializable {
    // Використовується для контролю версій під час серіалізації
    private static final long serialVersionUID = 1L;

    private int id;
    private String name; // [cite: 161]
    private double quantity; // [cite: 162]
    private String unit; // [cite: 163]

    private static int nextId = 1;

    // Конструктор для створення нового інгредієнта
    public Ingredient(String name, double quantity, String unit) {
        this.id = nextId++;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getQuantity() { return quantity; }
    public String getUnit() { return unit; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setQuantity(double quantity) { this.quantity = quantity; } // [cite: 165]
    public void setUnit(String unit) { this.unit = unit; }

    /**
     * Статичний метод для синхронізації лічильника ID після завантаження даних.
     * @param maxId Найбільший ID серед завантажених інгредієнтів.
     */
    public static void updateNextId(int maxId) {
        nextId = maxId + 1;
    }

    // Метод для виведення інформації про інгредієнт [cite: 166]
    @Override
    public String toString() {
        return String.format("ID: %d, Назва: %s, Кількість: %.2f %s",
                id, name, quantity, unit);
    }
}