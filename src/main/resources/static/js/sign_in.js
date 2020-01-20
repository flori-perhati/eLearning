$(document).ready(function(){
    $('form').submit(function(){
        let user = {};
        user['username'] = $('#username').val();
        user['password'] = $('#password').val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "http://localhost:8080/validateUser",
            data: JSON.stringify(user),
            dataType: 'json',
            success: function (user) {
                alert("success");
            },
            error: function (e) {
                alert("error");
            }
        });
    });
});