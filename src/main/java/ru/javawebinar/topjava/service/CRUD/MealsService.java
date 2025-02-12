package ru.javawebinar.topjava.service.CRUD;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealsService {
    Meal add(Meal meal);
    
    List<Meal> getAll();
    
    Meal update(Meal meal);
    
    void delete(int id);
    
    Meal get(int id);
}
