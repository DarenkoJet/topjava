package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealService {
    
    private final MealRepository repository;
    
    public MealService(MealRepository repository) {
        this.repository = repository;
    }
    
    public Meal create(Meal meal) {
        return repository.save(meal, SecurityUtil.authUserId());
    }
    
    public void delete(int id) {
        checkNotFound(repository.delete(id, SecurityUtil.authUserId()), id);
    }
    
    public Meal get(int id) {
        return checkNotFound(repository.get(id, SecurityUtil.authUserId()), id);
    }
    
    public List<Meal> getAll() {
        return new ArrayList<>(repository.getAll(SecurityUtil.authUserId()));
    }
    
    public void update(Meal meal) {
        checkNotFound(repository.save(meal, SecurityUtil.authUserId()), meal.getId());
    }
    
}