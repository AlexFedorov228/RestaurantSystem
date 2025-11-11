package com.restaurant.services;

import java.io.*;

/**
 * Менеджер даних, відповідальний за збереження та завантаження. [cite: 207]
 * Використовує Java Serialization для збереження у бінарний файл. 
 */
public class DataManager {

    /**
     * Зберігає стан системи (всі списки) у файл. [cite: 212]
     * @param data Об'єкт RestaurantData, що містить всі дані.
     * @param filepath Шлях до файлу збереження.
     */
    public void saveData(RestaurantData data, String filepath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(data);
            System.out.println("Дані успішно збережено у " + filepath);
        } catch (IOException e) {
            System.err.println("Помилка збереження даних: " + e.getMessage());
        }
    }

    /**
     * Завантажує стан системи з файлу. [cite: 213]
     * @param filepath Шлях до файлу збереження.
     * @return RestaurantData об'єкт з даними, або null у разі помилки.
     */
    public RestaurantData loadData(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) {
            System.out.println("Файл даних не знайдено. Завантажено порожню систему.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            RestaurantData data = (RestaurantData) ois.readObject();
            System.out.println("Дані успішно завантажено з " + filepath);
            return data;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Помилка завантаження даних: " + e.getMessage());
            return null;
        }
    }
}