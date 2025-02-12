package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.CRUD.MealsService;
import ru.javawebinar.topjava.service.CRUD.StorageMemoryMealsService;
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
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    
    private static final int CALORIES_COUNT = 2000;
    
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    
    private static final String LIST_MEALS = "/meals.jsp";
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    private MealsService mealsService;
    
    @Override
    public void init() {
        mealsService = new StorageMemoryMealsService();
        log.debug("init Meals");
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        meals.forEach(meal -> mealsService.add(meal));
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");
        if (action == null) {
            action = "default";
        }
        switch (action) {
            case "delete":
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                mealsService.delete(mealId);
                request.setAttribute("mealsTo", MealsUtil.filteredByStreams(mealsService.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_COUNT));
                request.setAttribute("dateTimeFormatter", DATE_TIME_FORMATTER);
                response.sendRedirect(request.getRequestURI());
                return;
            case "edit":
                forward = INSERT_OR_EDIT;
                request.setAttribute("meal", mealsService.get(Integer.parseInt(request.getParameter("mealId"))));
                break;
            case "insert":
                forward = INSERT_OR_EDIT;
                Meal meal = new Meal();
                request.setAttribute("meal", meal);
                break;
            default:
                forward = LIST_MEALS;
                request.setAttribute("mealsTo", MealsUtil.filteredByStreams(mealsService.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_COUNT));
                request.setAttribute("dateTimeFormatter", DATE_TIME_FORMATTER);
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String description = request.getParameter("description");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"));
        String mealId = request.getParameter("mealId");
        Meal meal = new Meal(dateTime, description, calories);
        if (mealId == null || mealId.isEmpty()) {
            mealsService.add(meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            mealsService.update(meal);
        }
        response.sendRedirect(request.getRequestURI());
    }
}
