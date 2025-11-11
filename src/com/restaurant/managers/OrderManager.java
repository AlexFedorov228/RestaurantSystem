package com.restaurant.managers;

import com.restaurant.interfaces.IManageable;
import com.restaurant.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Менеджер для керування Замовленнями. 
 * Реалізує інтерфейс IManageable<Order>. [cite: 245]
 */
public class OrderManager implements IManageable<Order> {
    private List<Order> orders; // [cite: 197]

    public OrderManager() {
        this.orders = new ArrayList<>();
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        int maxId = 0;
        for (Order o : orders) {
            if (o.getId() > maxId) {
                maxId = o.getId();
            }
        }
        Order.updateNextId(maxId);
    }

    @Override
    public void add(Order entity) { // [cite: 203]
        orders.add(entity);
    }

    @Override
    public Order getById(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public void update(int id, Order entity) {
        Order existing = getById(id);
        if (existing != null) {
            // Зазвичай оновлюють статус замовлення або додають/видаляють страви,
            // а не замінюють об'єкт. Для простоти, ми не реалізуємо
            // повне оновлення.
            System.out.println("Оновлення замовлень не підтримується у цій версії.");
        }
    }

    @Override
    public boolean remove(int id) { // [cite: 142]
        Order toRemove = getById(id);
        if (toRemove != null) {
            return orders.remove(toRemove);
        }
        return false;
    }
}