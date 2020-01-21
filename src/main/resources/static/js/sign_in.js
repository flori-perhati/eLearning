$(document).ready(function(){
    $('#submit-user').click(function(){
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
                document.location='admin';
            },
            error: function (e) {
                document.location='admin';
            }
        });
    });
});