package ru.javawebinar.topjava.service.CRUD;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MealsService implements BaseMealsService {
    private final List<Meal> meals = new ArrayList<>();
    
    private int counter = 0;
    
    @Override
    public Meal add(LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(counter++, dateTime, description, calories);
        meals.add(meal);
        return meal;
    }
    
    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals);
    }
    
    @Override
    public Meal update(Meal updatedMeal) {
        Meal meal = meals.stream().filter(m -> m.getId().equals(updatedMeal.getId())).findFirst().orElse(null);
        meal.setDescription(updatedMeal.getDescription());
        meal.setDateTime(updatedMeal.getDateTime());
        meal.setCalories(updatedMeal.getCalories());
        return meal;
    }
    
    @Override
    public void delete(int id) {
        meals.removeIf(meal -> meal.getId() == id);
    }
}