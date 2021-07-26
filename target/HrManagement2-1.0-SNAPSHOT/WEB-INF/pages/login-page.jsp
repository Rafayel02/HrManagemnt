<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<c:if test="${error}">
    <p>Invalid values, please check!!!!!</p>
</c:if>
<c:if test="${!error}">
    <p>Input username and password to login!</p>
</c:if>

<form action="${pageContext.request.contextPath}/login" method="post">
    <br>
    <label>
        Username: <input name="username" type="text">
    </label><br>
    <label>
        Password: <input name="password" type="password">
    </label>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <br>
    <input type="submit" value="login">
</form>

<form action="${pageContext.request.contextPath}/register" method="get">
    <p>Create account...</p>
    <input type="submit" value="register">
</form>

</body>
</html>
