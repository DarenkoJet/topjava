package ru.javawebinar.topjava.service.CRUD;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface BaseMealsService {
    Meal add(LocalDateTime dateTime, String description, int calories);
    
    List<Meal> getAll();
    
    Meal update(Meal updatedMeal);
    
    void delete(int id);
}
