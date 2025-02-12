package ru.javawebinar.topjava.service.CRUD;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.slf4j.LoggerFactory.getLogger;

public class StorageMemoryMealsService implements MealsService {
    private static final Logger log = getLogger(StorageMemoryMealsService.class);
    
    private final Map<Integer, Meal> meals = new HashMap<>();
    
    private final Lock lock = new ReentrantLock();
    
    private int counter = 1;
    
    @Override
    public Meal add(Meal mealNew) {
        Meal meal = new Meal(mealNew.getDateTime(), mealNew.getDescription(), mealNew.getCalories());
        try {
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                meal.setId(counter);
                meals.put(counter, meal);
                counter++;
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            return null;
        } finally {
            lock.unlock();
        }
        return meal;
    }
    
    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
    
    @Override
    public Meal update(Meal updatedMeal) {
        Meal meal = null;
        try {
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                meal = meals.get(updatedMeal.getId());
                meal.setDescription(updatedMeal.getDescription());
                meal.setDateTime(updatedMeal.getDateTime());
                meal.setCalories(updatedMeal.getCalories());
                meals.put(updatedMeal.getId(), meal);
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            return null;
        } finally {
            lock.unlock();
        }
        return meal;
    }
    
    @Override
    public void delete(int id) {
        try {
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                meals.remove(id);
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public Meal get(int id) {
        return meals.get(id);
    }
}