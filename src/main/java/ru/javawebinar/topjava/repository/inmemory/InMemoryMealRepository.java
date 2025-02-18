package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> usersToMealsMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    
    {
        MealsUtil.meals.forEach(meal -> save(meal, SecurityUtil.authUserId()));
    }
    
    @Override
    public Meal save(Meal meal, Integer userID) {
        if (!usersToMealsMap.containsKey(userID)) {
            Map<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
            usersToMealsMap.put(userID, mealsMap);
        }
        Map<Integer, Meal> mealsMap = usersToMealsMap.get(userID);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUser(userID);
            mealsMap.put(meal.getId(), meal);
            return meal;
        }
        return mealsMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }
    
    @Override
    public boolean delete(int id, Integer userID) {
        return usersToMealsMap.get(userID).remove(id) != null;
    }
    
    @Override
    public Meal get(int id, Integer userID) {
        return usersToMealsMap.get(userID).getOrDefault(id, null);
    }
    
    @Override
    public Collection<Meal> getAll(Integer userID) {
        if (usersToMealsMap.containsKey(userID)) {
            return usersToMealsMap.get(userID).values()
                    .stream()
                    .sorted(Comparator.comparing(Meal::getDateTime))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}

