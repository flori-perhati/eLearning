<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>eLearning - Sign In</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/sign_in.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/checkbox.css"/>">
    <style type="text/css">
        .error {
            font-size: 12px;
            font-family: Montserrat, serif;
            color: red;
        }
    </style>
</head>
    <body>
        <c:if test="${sessionScope.remember_me != null} && ${sessionScope.remember_me}">
            <c:if test="${sessionScope.user_status != null}">
                <c:redirect url="/${sessionScope.user_status}"/>
            </c:if>
        </c:if>
        <br><br><br><br><br>
        <form:form id="submit-form" class="login-block" method="post" action="/accounts/sign_in/validate" modelAttribute="user">
            <h1>Sign In</h1>
            <small><form:errors cssClass="error" path="username"/></small>
            <label for="username"></label><form:input type="text" value="" placeholder="Username" id="username" required="" path="username"/>
            <small><form:errors cssClass="error" path="password"/></small>
            <label for="password"></label><form:input type="password" value="" placeholder="Password" id="password" required="" path="password"/>
            <input type="checkbox" id="box" name="remember-me"><label for="box"><b>Remember me</b></label>
            <button type="submit" id="submit-user">Submit</button>

            <c:if test="${noUser}">
                <br><br>
                <div class="error">User does not exist!!!</div>
            </c:if>
        </form:form>
    </body>
</html>