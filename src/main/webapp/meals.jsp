<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meal List</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal List</h2>
<table>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
    <jsp:useBean id="dateTimeFormatter" scope="request" type="java.time.format.DateTimeFormatter"/>
    <c:forEach var="meal" items="${mealsTo}">
        <tr style="color: ${meal.excess ? "red" : "green"};">
            <td>${meal.dateTime.format(dateTimeFormatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>