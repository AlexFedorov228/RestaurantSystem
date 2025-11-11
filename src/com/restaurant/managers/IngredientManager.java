package com.restaurant.managers;

import com.restaurant.interfaces.IManageable;
import com.restaurant.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Менеджер для керування Інгредієнтами. 
 * Реалізує інтерфейс IManageable<Ingredient>. [cite: 244]
 */
public class IngredientManager implements IManageable<Ingredient> {
    private List<Ingredient> ingredients; // [cite: 195]

    public IngredientManager() {
        this.ingredients = new ArrayList<>();
    }

    // Встановлює список інгредієнтів (використовується при завантаженні даних)
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        // Оновлюємо статичний лічильник ID
        int maxId = 0;
        for (Ingredient i : ingredients) {
            if (i.getId() > maxId) {
                maxId = i.getId();
            }
        }
        Ingredient.updateNextId(maxId);
    }

    @Override
    public void add(Ingredient entity) { // [cite: 199]
        ingredients.add(entity);
    }

    @Override
    public Ingredient getById(int id) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getId() == id) {
                return ingredient;
            }
        }
        return null;
    }

    @Override
    public List<Ingredient> getAll() {
        return new ArrayList<>(ingredients); // Повертаємо копію, щоб запобігти зовнішнім змінам
    }

    @Override
    public void update(int id, Ingredient entity) {
        Ingredient existing = getById(id);
        if (existing != null) {
            existing.setName(entity.getName());
            existing.setQuantity(entity.getQuantity());
            existing.setUnit(entity.getUnit());
        }
    }

    @Override
    public boolean remove(int id) { // [cite: 200]
        Ingredient toRemove = getById(id);
        if (toRemove != null) {
            return ingredients.remove(toRemove);
        }
        return false;
    }
}