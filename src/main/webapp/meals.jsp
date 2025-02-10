<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Meals</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Значения</th>
    </tr>
    <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${mealsTo}">
        <c:choose>
            <c:when test="${meal.getExcess() == true}"><tr style="color: red;"></c:when>
            <c:otherwise><tr style="color: green;"></c:otherwise>
        </c:choose>
        <td><c:out value="${meal.getDateTime().format(DateTimeFormatter.ofPattern(\"dd.MM.yyyy hh:mm\"))}"/></td>
        <td><c:out value="${meal.getDescription()}"/></td>
        <td><c:out value="${meal.getCalories()}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>