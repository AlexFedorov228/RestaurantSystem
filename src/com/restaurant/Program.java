package com.restaurant;

import com.restaurant.models.Dish;
import com.restaurant.models.Ingredient;
import com.restaurant.models.Order;
import com.restaurant.services.Restaurant;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Головний клас програми (точка входу). 
 * Відповідає за консольний інтерфейс користувача (UI) [cite: 151]
 * та обробку вводу.
 */
public class Program {

    // Створюємо екземпляри Scanner та Restaurant, які будуть
    // використовуватись протягом всього життєвого циклу програми.
    private static final Scanner scanner = new Scanner(System.in);
    private static final Restaurant restaurant = new Restaurant();

    public static void main(String[] args) {
        // 1. Завантажуємо дані при старті
        restaurant.loadData();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readIntInput();

            switch (choice) {
                case 1:
                    manageIngredients();
                    break;
                case 2:
                    manageDishes();
                    break;
                case 3:
                    manageOrders();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }
        }

        // 2. Зберігаємо дані при виході
        restaurant.saveData();
        scanner.close();
        System.out.println("Роботу програми завершено.");
    }

    // --- Головне Меню ---

    private static void printMainMenu() {
        System.out.println("\n--- Система Управління Рестораном ---");
        System.out.println("1. Управління Інгредієнтами");
        System.out.println("2. Управління Стравами (Меню)");
        System.out.println("3. Управління Замовленнями");
        System.out.println("0. Зберегти та вийти");
        System.out.print("Ваш вибір: ");
    }

    // --- Меню Інгредієнтів [cite: 128] ---

    private static void manageIngredients() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Управління Інгредієнтами ---");
            System.out.println("1. Додати інгредієнт");
            System.out.println("2. Переглянути всі інгредієнти");
            System.out.println("3. Оновити інгредієнт");
            System.out.println("4. Видалити інгредієнт");
            System.out.println("0. Назад до головного меню");
            System.out.print("Ваш вибір: ");
            
            int choice = readIntInput();
            switch (choice) {
                case 1: // [cite: 129]
                    System.out.print("Введіть назву: ");
                    String name = scanner.nextLine();
                    System.out.print("Введіть кількість: ");
                    double qty = readDoubleInput();
                    System.out.print("Введіть одиницю виміру (кг, л, шт): ");
                    String unit = scanner.nextLine();
                    restaurant.getIngredientManager().add(new Ingredient(name, qty, unit));
                    System.out.println("Інгредієнт додано.");
                    break;
                case 2: // [cite: 132]
                    System.out.println("Список інгредієнтів:");
                    restaurant.getIngredientManager().getAll()
                            .forEach(System.out::println);
                    break;
                case 3: // [cite: 130]
                    System.out.print("Введіть ID інгредієнта для оновлення: ");
                    int idUpdate = readIntInput();
                    Ingredient ingToUpdate = restaurant.getIngredientManager().getById(idUpdate);
                    if (ingToUpdate != null) {
                        System.out.print("Введіть нову назву (поточна: " + ingToUpdate.getName() + "): ");
                        String newName = scanner.nextLine();
                        System.out.print("Введіть нову кількість (поточна: " + ingToUpdate.getQuantity() + "): ");
                        double newQty = readDoubleInput();
                        // Оновлюємо об'єкт новими даними
                        ingToUpdate.setName(newName);
                        ingToUpdate.setQuantity(newQty);
                        // Тут ми оновлюємо сам об'єкт, оскільки менеджер тримає посилання на нього.
                        // restaurant.getIngredientManager().update(idUpdate, ingToUpdate); // Цей виклик не потрібен, якщо ми змінюємо об'єкт за посиланням
                        System.out.println("Інгредієнт оновлено.");
                    } else {
                        System.out.println("Інгредієнт з ID " + idUpdate + " не знайдено.");
                    }
                    break;
                case 4: // [cite: 131]
                    System.out.print("Введіть ID інгредієнта для видалення: ");
                    int idDelete = readIntInput();
                    // Перевірка, чи інгредієнт не використовується у стравах [cite: 149]
                    Ingredient ingToDelete = restaurant.getIngredientManager().getById(idDelete);
                    if (ingToDelete != null && restaurant.getDishManager().isIngredientUsed(ingToDelete)) {
                        System.out.println("Помилка: Не можна видалити інгредієнт, оскільки він використовується у страві.");
                    } else if (restaurant.getIngredientManager().remove(idDelete)) {
                        System.out.println("Інгредієнт видалено.");
                    } else {
                        System.out.println("Інгредієнт з ID " + idDelete + " не знайдено.");
                    }
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Невірний вибір.");
            }
        }
    }
    
    // --- Меню Страв [cite: 133] ---

    private static void manageDishes() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Управління Стравами (Меню) ---");
            System.out.println("1. Додати страву");
            System.out.println("2. Переглянути всі страви");
            System.out.println("3. Додати інгредієнт до страви");
            System.out.println("4. Видалити страву");
            System.out.println("0. Назад до головного меню");
            System.out.print("Ваш вибір: ");

            int choice = readIntInput();
            switch (choice) {
                case 1: // [cite: 134]
                    System.out.print("Введіть назву страви: ");
                    String name = scanner.nextLine();
                    System.out.print("Введіть ціну: ");
                    double price = readDoubleInput();
                    System.out.print("Введіть час приготування (хв): ");
                    int time = readIntInput();
                    restaurant.getDishManager().add(new Dish(name, price, time));
                    System.out.println("Страву додано.");
                    break;
                case 2: // [cite: 137]
                    System.out.println("Список страв (Меню):");
                    restaurant.getDishManager().getAll()
                            .forEach(System.out::println);
                    break;
                case 3: // [cite: 175]
                    System.out.print("Введіть ID страви, до якої додаємо інгредієнт: ");
                    int dishId = readIntInput();
                    Dish dish = restaurant.getDishManager().getById(dishId);
                    if (dish == null) {
                        System.out.println("Страву не знайдено.");
                        continue;
                    }
                    
                    System.out.println("Доступні інгредієнти:");
                    restaurant.getIngredientManager().getAll().forEach(System.out::println);
                    System.out.print("Введіть ID інгредієнта для додавання: ");
                    int ingId = readIntInput();
                    Ingredient ing = restaurant.getIngredientManager().getById(ingId);
                    if (ing != null) {
                        dish.addIngredient(ing);
                        System.out.println("Інгредієнт '" + ing.getName() + "' додано до страви '" + dish.getName() + "'.");
                    } else {
                        System.out.println("Інгредієнт не знайдено.");
                    }
                    break;
                case 4: // [cite: 136]
                    System.out.print("Введіть ID страви для видалення: ");
                    int idDelete = readIntInput();
                    if (restaurant.getDishManager().remove(idDelete)) {
                        System.out.println("Страву видалено.");
                    } else {
                        System.out.println("Страву не знайдено.");
                    }
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Невірний вибір.");
            }
        }
    }

    // --- Меню Замовлень [cite: 138] ---

    private static void manageOrders() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Управління Замовленнями ---");
            System.out.println("1. Створити нове замовлення");
            System.out.println("2. Переглянути всі замовлення");
            System.out.println("3. Переглянути деталі замовлення");
            System.out.println("4. Видалити замовлення");
            System.out.println("0. Назад до головного меню");
            System.out.print("Ваш вибір: ");

            int choice = readIntInput();
            switch (choice) {
                case 1: // [cite: 139]
                    createNewOrder();
                    break;
                case 2: // [cite: 140]
                    System.out.println("Список активних замовлень:");
                    restaurant.getOrderManager().getAll()
                            .forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Введіть ID замовлення для перегляду: ");
                    int orderId = readIntInput();
                    Order order = restaurant.getOrderManager().getById(orderId);
                    if (order != null) {
                        System.out.println("Деталі замовлення: " + order);
                        System.out.println("Страви у замовленні:");
                        order.getDishes().forEach(d -> System.out.println("- " + d.getName()));
                    } else {
                        System.out.println("Замовлення не знайдено.");
                    }
                    break;
                case 4: // [cite: 142]
                    System.out.print("Введіть ID замовлення для видалення: ");
                    int idDelete = readIntInput();
                    if (restaurant.getOrderManager().remove(idDelete)) {
                        System.out.println("Замовлення видалено.");
                    } else {
                        System.out.println("Замовлення не знайдено.");
                    }
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Невірний вибір.");
            }
        }
    }

    /**
     * Допоміжний метод для покрокового створення замовлення. [cite: 139]
     */
    private static void createNewOrder() {
        System.out.print("Введіть номер столика: ");
        int tableNum = readIntInput();
        Order newOrder = new Order(tableNum);
        
        System.out.println("Додавання страв до замовлення. (Введіть 0 для завершення)");
        List<Dish> menu = restaurant.getDishManager().getAll();
        if (menu.isEmpty()) {
            System.out.println("Меню порожнє. Неможливо створити замовлення.");
            return;
        }

        while (true) {
            System.out.println("Меню:");
            menu.forEach(System.out::println);
            System.out.print("Введіть ID страви для додавання (0 для завершення): ");
            int dishId = readIntInput();
            
            if (dishId == 0) {
                break;
            }
            
            Dish dish = restaurant.getDishManager().getById(dishId);
            if (dish != null) {
                newOrder.addDish(dish); // [cite: 187]
                System.out.println("'" + dish.getName() + "' додано до замовлення.");
            } else {
                System.out.println("Страву з таким ID не знайдено.");
            }
        }
        
        if (newOrder.getDishes().isEmpty()) {
            System.out.println("Замовлення скасовано (не додано жодної страви).");
        } else {
            restaurant.getOrderManager().add(newOrder);
            System.out.println("Нове замовлення успішно створено!");
            System.out.println(newOrder);
        }
    }

    // --- Допоміжні методи для валідації вводу [cite: 147] ---

    /**
     * Безпечно зчитує ціле число з консолі.
     * @return Введене користувачем число.
     */
    private static int readIntInput() {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера (споживаємо '\n')
                return input;
            } catch (InputMismatchException e) { // [cite: 149]
                System.out.print("Помилка: введіть ціле число. Спробуйте ще раз: ");
                scanner.nextLine(); // Очистка буфера
            }
        }
    }

    /**
     * Безпечно зчитує дробове число з консолі.
     * @return Введене користувачем число.
     */
    private static double readDoubleInput() {
        while (true) {
            try {
                double input = scanner.nextDouble();
                scanner.nextLine(); // Очистка буфера
                return input;
            } catch (InputMismatchException e) {
                System.out.print("Помилка: введіть число (напр., 10.5). Спробуйте ще раз: ");
                scanner.nextLine(); // Очистка буфера
            }
        }
    }
}