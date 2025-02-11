package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealsService {
    private List<Meal> meals = new ArrayList<>();
    
    public MealsService(List<Meal> meals) {
        this.meals = new ArrayList<>(meals);
    }
    
    // Create
    public void addMeal(Meal meal) {
        meals.add(meal);
    }
    
    // Read
    public List<Meal> getAllMeals() {
        return meals;
    }
    
    public Meal getMealById(int id) {
        return meals.stream().filter(meal -> meal.getId() == id).findFirst().orElse(null);
    }
    
    // Update
    public void updateMeal(Meal updatedMeal) {
        Meal meal = getMealById(updatedMeal.getId());
        meal.setDescription(updatedMeal.getDescription());
        meal.setDateTime(updatedMeal.getDateTime());
        meal.setCalories(updatedMeal.getCalories());
    }
    
    // Delete
    public void deleteMeal(int id) {
        meals.remove(getMealById(id));
    }
    
    public Integer GetFreeId() {
        return meals.stream().map(Meal::getId).max(Integer::compareTo).orElse(0) + 1;
    }
    
}