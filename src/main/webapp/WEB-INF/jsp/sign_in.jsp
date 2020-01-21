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
        <div class="login-block">
            <h1>Sign In</h1>
            <label for="username"></label><input type="text" value="" placeholder="Username" id="username" required/>
            <label for="password"></label><input type="password" value="" placeholder="Password" id="password" required/>
            <input type="checkbox" id="box"><label for="box"><b>Remember me</b></label>
            <button id="submit-user">Submit</button>
        </div>

        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script type="text/javascript" src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.4.1.min.js"></script>
        <script type="text/javascript" src="<c:url value="/js/sign_in.js"/>"></script>

        <div>text</div>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#login_frm").submit(function(){
                    $("#msgbox").removeClass().addClass('myinfo').text('Validating Your Login ').fadeIn(1000);
                    this.timer = setTimeout(function () {
                        $.ajax({
                            url: 'check.jsp',
                            data: 'un='+ $('#login_id').val() +'&pw=' + $('#password').val(),
                            type: 'post',
                            success: function(msg){
                                if(msg != 'ERROR') {                // and direct to the success page
                                    $("#msgbox").html('Login Verified, Logging in.....').addClass('myinfo').fadeTo(900,1,
                                        function() {
                                            document.location='login.jsp?user='+msg;
                                        });
                                } else {
                                    $("#msgbox").fadeTo(200,0.1,function() {
                                        $(this).html('Sorry, Wrong Combination Of Username And Password.').removeClass().addClass('myerror').fadeTo(900,1);
                                    });
                                }
                            }
                        });
                    }, 200);
                    return false;
                });
            });
        </script>
    </body>
</html>