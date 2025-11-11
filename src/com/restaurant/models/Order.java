package com.restaurant.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель, що представляє Замовлення. [cite: 179]
 * Кожне замовлення містить список страв.
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id; // [cite: 182]
    private int tableNumber; // (З діаграми класів) 
    private LocalDateTime orderDate; // [cite: 183]
    private List<Dish> dishes; // [cite: 184]
    private double totalCost; // [cite: 185]

    private static int nextId = 1;

    public Order(int tableNumber) {
        this.id = nextId++;
        this.tableNumber = tableNumber;
        this.orderDate = LocalDateTime.now();
        this.dishes = new ArrayList<>();
        this.totalCost = 0.0;
    }

    // Getters
    public int getId() { return id; }
    public int getTableNumber() { return tableNumber; }
    public List<Dish> getDishes() { return dishes; }
    public double getTotalCost() { return totalCost; }

    /**
     * Додає страву до замовлення та перераховує загальну вартість. [cite: 187]
     * @param dish Страва для додавання.
     */
    public void addDish(Dish dish) {
        if (dish != null) {
            this.dishes.add(dish);
            calculateTotalCost(); // [cite: 189]
        }
    }

    /**
     * Видаляє страву з замовлення та перераховує загальну вартість. [cite: 188]
     * @param dish Страва для видалення.
     */
    public void removeDish(Dish dish) {
        if (this.dishes.remove(dish)) {
            calculateTotalCost();
        }
    }

    /**
     * Внутрішній метод для обчислення загальної вартості замовлення. [cite: 189]
     */
    private void calculateTotalCost() {
        this.totalCost = 0.0;
        for (Dish dish : dishes) {
            this.totalCost += dish.getPrice();
        }
    }
    
    public static void updateNextId(int maxId) {
        nextId = maxId + 1;
    }

    // Метод для виведення інформації про замовлення [cite: 190]
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = orderDate.format(formatter);
        return String.format("ID: %d, Столик: %d, Дата: %s, Сума: %.2f грн",
                id, tableNumber, formattedDate, totalCost);
    }
}