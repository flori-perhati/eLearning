<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
    <head>

        <!-- Access the bootstrap Css like this,
            Spring boot will handle the resource mapping automcatically -->
        <link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

        <script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <!--
        <spring:url value="/css/login_view.css" var="springCss" />
        <link href="${springCss}" rel="stylesheet" />
         -->
        <c:url value="/css/login_view.css" var="jstlCss" />
        <link href="${jstlCss}" rel="stylesheet" />
        <title>Sign In</title>

    </head>
    <body>
        <h1>Spring Boot Web JSP Example</h1>
        <h2>${user.username}</h2>
        <h3>${user.password}</h3>
        <h4>${user.userStatus}</h4>
    </body>
</html>