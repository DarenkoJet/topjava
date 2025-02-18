package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    
    Integer userID = null;
    
    // null if updated meal does not belong to userId
    Meal save(Meal meal, Integer userID);
    
    // false if meal does not belong to userId
    boolean delete(int id, Integer userID);
    
    // null if meal does not belong to userId
    Meal get(int id, Integer userID);
    
    // ORDERED dateTime desc
    Collection<Meal> getAll(Integer userID);
}
