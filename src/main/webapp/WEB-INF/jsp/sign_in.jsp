<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>eLearning - Sign In</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="<c:url value="/css/sign_in.css"/>">
</head>
    <body>
        <div id="sign_in">
            <form>
                <h1>Sign In</h1>
                <input type="text" placeholder="Username">
                <input type="password" placeholder="Password">
                <button>Sign in</button>
            </form>
        </div>

        <script src="<c:url value="/js/"/>"></script>
    </body>
</html>