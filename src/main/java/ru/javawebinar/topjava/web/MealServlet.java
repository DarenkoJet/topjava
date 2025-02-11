package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealsService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    
    private static final int CALORIES_COUNT = 2000;
    
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    
    private static final String LIST_MEALS = "/mealsList.jsp";
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    private static MealsService mealsService;
    
    @Override
    public void init() {
        log.debug("init Meals");
        mealsService = new MealsService(Arrays.asList(
                new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        ));
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to Meals");
        String forward;
        String action = request.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            forward = LIST_MEALS;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealsService.deleteMeal(mealId);
            request.setAttribute("mealsTo", MealsUtil.filteredByStreams(mealsService.getAllMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_COUNT));
            request.setAttribute("dateTimeFormatter", DATE_TIME_FORMATTER);
            response.sendRedirect("http://localhost:8080/topjava/mealsList");
            return;
        } else if ("edit".equalsIgnoreCase(action)) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealsService.getMealById(mealId);
            request.setAttribute("meal", meal);
        } else if ("insert".equalsIgnoreCase(action)) {
            forward = INSERT_OR_EDIT;
            Meal meal = new Meal();
            request.setAttribute("meal", meal);
        } else {
            forward = LIST_MEALS;
            request.setAttribute("mealsTo", MealsUtil.filteredByStreams(mealsService.getAllMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_COUNT));
            request.setAttribute("dateTimeFormatter", DATE_TIME_FORMATTER);
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        meal.setDescription(request.getParameter("description"));
        try {
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"));
            meal.setDateTime(dateTime);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        String mealId = request.getParameter("mealid");
        if (mealId == null || mealId.isEmpty()) {
            meal.setId(mealsService.GetFreeId());
            mealsService.addMeal(meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            mealsService.updateMeal(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEALS);
        request.setAttribute("mealsTo", MealsUtil.filteredByStreams(mealsService.getAllMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_COUNT));
        request.setAttribute("dateTimeFormatter", DATE_TIME_FORMATTER);
        view.forward(request, response);
    }
}
