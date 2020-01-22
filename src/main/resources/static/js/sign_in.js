$(document).ready(function(){
    $('#submit-form').submit(function(event){
        event.preventDefault();
        let user = {};
        user['username'] = $('#username').val();
        user['password'] = $('#password').val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/validateUser",
            data: JSON.stringify(user),
            dataType: 'json',
            success: function (response) {
                alert(response);
                console.log(response);
                // document.location='admin';
            },
            error: function (e) {
                console.log(e);
                // document.location='admin';
            }
        });
    });
});