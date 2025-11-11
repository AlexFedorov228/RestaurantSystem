package com.restaurant.services;

import com.restaurant.managers.DishManager;
import com.restaurant.managers.IngredientManager;
import com.restaurant.managers.OrderManager;

/**
 * Основний клас системи, що виступає фасадом. 
 * Він володіє екземплярами всіх менеджерів 
 * та делегує їм виконання операцій.
 * Також керує процесами збереження та завантаження даних.
 */
public class Restaurant {
    private DishManager dishManager;
    private IngredientManager ingredientManager;
    private OrderManager orderManager;
    private DataManager dataManager;
    private String dataFilePath = "data/restaurant.dat"; // Шлях до файлу даних

    public Restaurant() {
        this.dishManager = new DishManager();
        this.ingredientManager = new IngredientManager();
        this.orderManager = new OrderManager();
        this.dataManager = new DataManager();
    }

    /**
     * Метод завантаження даних.
     * Викликає DataManager і передає завантажені списки відповідним менеджерам.
     */
    public void loadData() { // 
        RestaurantData data = dataManager.loadData(dataFilePath);
        if (data != null) {
            ingredientManager.setIngredients(data.getIngredients());
            dishManager.setDishes(data.getDishes());
            orderManager.setOrders(data.getOrders());
        } else {
            // Якщо файл не існує або пошкоджений, починаємо з порожніми списками
            System.out.println("Запуск з новою, порожньою системою.");
        }
    }

    /**
     * Метод збереження даних.
     * Збирає списки з усіх менеджерів у RestaurantData і передає DataManager'у.
     */
    public void saveData() { // 
        RestaurantData data = new RestaurantData(
                ingredientManager.getAll(),
                dishManager.getAll(),
                orderManager.getAll()
        );
        dataManager.saveData(data, dataFilePath);
    }

    // Getters для доступу до менеджерів з UI (Program.java)
    public DishManager getDishManager() { return dishManager; }
    public IngredientManager getIngredientManager() { return ingredientManager; }
    public OrderManager getOrderManager() { return orderManager; }
}