<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: fperhati
  Date: 09/01/2020
  Time: 10:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>eLearning</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <style type="text/css">
        .login-form {
            width: 340px;
            margin: 200px auto;
        }
        .login-form form {
            margin-bottom: 15px;
            background: #f7f7f7;
            box-shadow: 0 2px 2px rgba(0, 0, 0, 0.3);
            padding: 30px;
        }
        .login-form h2 {
            margin: 0 0 15px;
        }
        .btn {
            font-size: 15px;
            font-weight: bold;
        }
    </style>
</head>

<body>
<div class="login-form">
    <form:form action="updateStudent" method="post" modelAttribute="student">
        <h2 class="text-center">Edit Data</h2>
        <div class="form-group">
            <form:input type="text" class="form-control" placeholder="First Name" required="required" path="firstName"/>
        </div>
        <div class="form-group">
            <form:input type="text" class="form-control" placeholder="Last Name" required="required" path="lastName"/>
        </div>
        <div class="form-group">
            <form:input type="text" class="form-control" placeholder="Username" required="required" path="user.username"/>
        </div>
        <div class="form-group">
            <form:input type="text" class="form-control" placeholder="Password" required="required" path="user.password"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block" >Save</button>
        </div>
        <table>
            <tr>
                <td><form:hidden  path="id" /></td>
                <td><form:hidden  path="user.id" /></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>