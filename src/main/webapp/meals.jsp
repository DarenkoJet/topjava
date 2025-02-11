<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Список еды</title>
</head>
<body>
<h3><a href="index.html">Домой</a></h3>
<hr>
<h2>Список еды</h2>
<p><a href="meals?action=insert">Добавить</a></p>
<table width="600 px">
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th></th>
        <th></th>
    </tr>
    <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
    <jsp:useBean id="dateTimeFormatter" scope="request" type="java.time.format.DateTimeFormatter"/>
    <c:forEach var="meal" items="${mealsTo}">
        <tr style="color: ${meal.excess ? "red" : "green"};">
            <td>${meal.dateTime.format(dateTimeFormatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&mealId=${meal.id}">Редактировать</a></td>
            <td><a href="meals?action=delete&mealId=${meal.id}">Удалить</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>