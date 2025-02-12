package ru.javawebinar.topjava.service.CRUD;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealsService implements BaseMealsService {
    Map<Integer, Meal> meals = new HashMap<>();
    
    private int counter = 0;
    
    @Override
    public Meal add(LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(counter, dateTime, description, calories);
        meals.put(counter, meal);
        counter++;
        return meal;
    }
    
    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
    
    @Override
    public Meal update(int id, LocalDateTime dateTime, String description, int calories) {
        Meal meal = meals.get(id);
        meal.setDescription(description);
        meal.setDateTime(dateTime);
        meal.setCalories(calories);
        meals.put(id, meal);
        return meal;
    }
    
    @Override
    public void delete(int id) {
        meals.remove(id);
    }
    
    public Meal get(int id) {
        return meals.get(id);
    }
}