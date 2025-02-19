package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIsNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MealService service;
    
    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(SecurityUtil.getAuthUserId()), SecurityUtil.authUserCaloriesPerDay());
    }
    
    public List<MealTo> getAllForPeriod(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getAllForPeriod");
        Predicate<Meal> mealPredicate = meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)
                && DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate);
        return MealsUtil.filterByPredicate(service.getAll(SecurityUtil.getAuthUserId()), SecurityUtil.authUserCaloriesPerDay(), mealPredicate);
    }
    
    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.getAuthUserId());
    }
    
    public void Save(Meal meal) {
        log.info("get {}", meal);
        service.save(meal, SecurityUtil.getAuthUserId());
    }
    
    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkIsNew(meal);
        return service.create(meal, SecurityUtil.getAuthUserId());
    }
    
    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.getAuthUserId());
    }
    
    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.getAuthUserId());
    }
}