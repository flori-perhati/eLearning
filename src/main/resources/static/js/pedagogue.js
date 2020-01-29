$(document).ready(function () {
    let pedagogueForm = $('#pedagogue-profile');
    let courseForm = $('#course-form');
    let courseTable = $('#course-table');
    let examForm = $('#exam-form');
    let questionForm = $('#question-form');
    let header = $('#window-header');

    let firstName = $('#pedagogue-first-name');
    let lastName = $('#pedagogue-last-name');
    let birthplace = $('#pedagogue-birthplace');
    let birthdate = $('#pedagogue-birthdate');
    let gender = $('#pedagogue-gender');
    let username = $('#pedagogue-username');
    let password = $('#pedagogue-password');
    let submitPedagogue = $('#submit-pedagogue');

    let courseDescription = $('#course-description');

    pedagogueForm.show();
    courseForm.hide();
    courseTable.hide();
    examForm.hide();
    questionForm.hide();

    firstName.attr('disabled','disabled');
    lastName.attr('disabled','disabled');
    birthplace.attr('disabled','disabled');
    birthdate.attr('disabled','disabled');
    gender.attr('disabled','disabled');
    username.attr('disabled','disabled');
    password.attr('disabled','disabled');
    submitPedagogue.hide();

    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
        $(this).toggleClass('active');
    });

    $('#enable-profile-edit').click(function () {
        firstName.removeAttr('disabled');
        lastName.removeAttr('disabled');
        birthplace.removeAttr('disabled');
        birthdate.removeAttr('disabled');
        gender.removeAttr('disabled');
        username.removeAttr('disabled');
        password.removeAttr('disabled');
        submitPedagogue.show();
    });

    $('#profile').click(function () {
        header.html('Profile');

        pedagogueForm.show();
        courseForm.hide();
        courseTable.hide();
        examForm.hide();
        questionForm.hide();

        // warning.html("");
    });

    $('#allCourses').click(function () {
        header.html('All Courses');

        pedagogueForm.hide();
        courseForm.show();
        courseTable.show();
        examForm.hide();
        questionForm.hide();

        // warning.html("");
    });

    $('#addExam').click(function () {
        header.html('All Courses');

        pedagogueForm.hide();
        courseForm.hide();
        courseTable.hide();
        examForm.show();
        questionForm.hide();

        // warning.html("");
    });

    $('#addQuestion').click(function () {
        header.html('All Courses');

        pedagogueForm.hide();
        courseForm.hide();
        courseTable.hide();
        examForm.hide();
        questionForm.show();

        // warning.html("");
    });

    pedagogueForm.submit(function(event) {
        event.preventDefault();

        let pedagogue = {};
        let user = {};
        pedagogue['id'] = "";
        pedagogue['firstName'] = firstName.val();
        pedagogue['lastName'] = lastName.val();
        pedagogue['birthplace'] = birthplace.val();
        pedagogue['birthdate'] = birthdate.val();
        pedagogue['gender'] = gender.val();

        user['username'] = username.val();
        user['password'] = password.val();
        pedagogue['user'] = user;

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/editPedagogue",
            data: JSON.stringify(pedagogue),
            dataType: 'json'
        }).done(function(response){
            if (response === "Something went wrong!")
                alert(response);
            else {
                firstName.html(response.firstName);
                lastName.html(response.lastName);
                birthplace.html(response.birthplace);
                birthdate.html(response.birthdate);
                gender.html(response.gender);
                username.html(response.user.username);
                password.html(response.user.password);

                firstName.disable();
                lastName.disable();
                birthplace.disable();
                birthdate.disable();
                gender.disable();
                username.disable();
                password.disable();
                submitPedagogue.hide();
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    pedagogueForm.submit(function(event) {
        event.preventDefault();

        let course = {};
        course['pedagogueId'] = "";
        course['description'] = courseDescription.val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/saveCourse",
            data: JSON.stringify(course),
            dataType: 'json'
        }).done(function(response){
            if (response === "Something went wrong!")
                alert(response);
            else {
                $('#course-table tr:last').after(insertCourseRow(response));
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    function insertCourseRow(course) {
        return '<tr>' +
            '<td style="width: 85%">' + course.description +'</td>' +
            '<td>' +
            '<a href="#" ><i class="fa fa-edit" style="font-size:20px;color:#7386D5;text-shadow:2px 2px 4px #000000;"></i></a>' +
            '<a href="#" ><i class="fa fa-plus" style="font-size:20px;color:lightblue;margin-left: 20px;text-shadow:2px 2px 4px #000000;"></i></a>' +
            '<a href="#" ><i class="fa fa-trash" style="font-size:20px;color:red;margin-left: 20px;text-shadow:2px 2px 4px #000000;"></i></a>' +
            '</td>' +
            '</tr>';
    }
});