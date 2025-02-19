package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> usersToMealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    
    {
        MealsUtil.meals.stream().filter(meal -> meal.getCalories() > 500).forEach(meal -> save(meal, 1));
        MealsUtil.meals.stream().filter(meal -> meal.getCalories() <= 500).forEach(meal -> save(meal, 2));
    }
    
    @Override
    public Meal save(Meal meal, int userId) {
        usersToMealsMap.putIfAbsent(userId, new ConcurrentHashMap<>());
        Map<Integer, Meal> mealsMap = usersToMealsMap.get(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            mealsMap.put(meal.getId(), meal);
            return meal;
        }
        return mealsMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }
    
    @Override
    public boolean delete(int id, int userId) {
        if (usersToMealsMap.get(userId) != null) {
            return usersToMealsMap.get(userId).remove(id) != null;
        }
        return false;
    }
    
    @Override
    public Meal get(int id, int userId) {
        if (usersToMealsMap.get(userId) != null) {
            return usersToMealsMap.get(userId).get(id);
        }
        return null;
    }
    
    @Override
    public List<Meal> getAll(int userId) {
        if (usersToMealsMap.get(userId) != null) {
            return usersToMealsMap.get(userId).values()
                    .stream()
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}

