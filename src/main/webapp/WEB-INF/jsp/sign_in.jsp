<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>eLearning - Sign In</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/sign_in.css"/>">
</head>
    <body>
        <br><br><br><br><br>
        <form method="post" class="login-block">
            <h1>Sign In</h1>
            <label for="username"></label><input type="text" value="" placeholder="Username" id="username" required/>
            <label for="password"></label><input type="password" value="" placeholder="Password" id="password" required/>
            <input type="checkbox" id="box"><label for="box"><b>Remember me</b></label>
            <button type="submit">Submit</button>
        </form>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.4.1.min.js"></script>
        <script src="<c:url value="/js/sign_in.js"/>"></script>
    </body>
</html>