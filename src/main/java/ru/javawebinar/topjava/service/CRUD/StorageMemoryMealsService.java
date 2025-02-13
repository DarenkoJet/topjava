package ru.javawebinar.topjava.service.CRUD;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.slf4j.LoggerFactory.getLogger;

public class StorageMemoryMealsService implements MealsService {
    private static final Logger log = getLogger(StorageMemoryMealsService.class);
    
    private final ConcurrentMap<Integer, Meal> meals = new ConcurrentHashMap<>();
    
    private final Lock lock = new ReentrantLock();
    
    private final AtomicInteger counter = new AtomicInteger(1);
    
    @Override
    public synchronized Meal add(Meal mealNew) {
        Meal meal = new Meal(mealNew.getDateTime(), mealNew.getDescription(), mealNew.getCalories());
        meal.setId(counter.get());
        meals.put(counter.get(), meal);
        counter.addAndGet(1);
        return meal;
    }
    
    @Override
    public synchronized List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
    
    @Override
    public synchronized Meal update(Meal updatedMeal) {
        Meal meal = meals.get(updatedMeal.getId());
        meal.setDescription(updatedMeal.getDescription());
        meal.setDateTime(updatedMeal.getDateTime());
        meal.setCalories(updatedMeal.getCalories());
        return meal;
    }
    
    @Override
    public synchronized void delete(int id) {
        meals.remove(id);
    }
    
    @Override
    public synchronized Meal get(int id) {
        return meals.get(id);
    }
}