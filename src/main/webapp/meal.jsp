<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<% String action = "insert".equalsIgnoreCase(request.getParameter("action")) ? "Добавить" : "Редактировать"; %>
<html>
<head>
    <title><%= action %> еду</title>
</head>
<body>
<h3><a href="index.html">Домой</a></h3>
<hr>
<h2><%= action %> еду</h2>

<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="POST" action='meals' name="frmAddMeal">
    <label>ID : <input type="number" name="mealId" value="${meal.id}" readonly/></label><br/>
    <label>Дата/Время: <input type="datetime-local" name="datetime" value="${meal.dateTime}"/></label><br/>
    <label>Описание: <input type="text" name="description" value="${meal.description}"/></label><br/>
    <label>Калории: <input type="number" name="calories" value="${meal.calories}"/></label><br/>
    <input type="submit" value="Save"/>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>