$(document).ready(function () {
    hideLoader();
    let header = $('#window-header');

    let studentForm = $('#student-profile');
    let courseTable = $('#course-table');
    let exams = $('#exams');
    let examTable = $('#exam-table');
    let examForm = $('#exam-form');

    let firstName = $('#student-first-name');
    let lastName = $('#student-last-name');
    let birthdate = $('#student-birthdate');
    let gender = $('#student-gender');
    let username = $('#student-username');
    let password = $('#student-password');
    let submitStudent = $('#submit-student');

    let course = $('#course');
    let examError = $('#exam-error');

    studentForm.show();
    courseTable.hide();
    exams.hide();
    examForm.hide();

    $("#answer-label").hide();

    disableProfile();

    /**
     * Actions
     */

    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
        $(this).toggleClass('active');
    });

    $('#enable-profile-edit').click(function () {
        firstName.removeAttr('disabled');
        lastName.removeAttr('disabled');
        birthdate.removeAttr('disabled');
        gender.removeAttr('disabled');
        username.removeAttr('disabled');
        password.removeAttr('disabled');
        submitStudent.show();
    });

    $('#profile').click(function () {
        header.html('Profile');

        studentForm.show();
        courseTable.hide();
        exams.hide();
        // warning.html("");
    });

    $('#courses').click(function () {
        header.html('All Courses');

        studentForm.hide();
        courseTable.show();
        // warning.html("");
    });

    $('#results').click(function () {
        header.html('All Courses');

        studentForm.hide();
        courseTable.hide();
        exams.hide();
        // warning.html("");
    });

    /**
     * Form submit
     */

    studentForm.submit(function(event) {
        event.preventDefault();

        let student = {};
        let user = {};
        student['id'] = $('#submit-student').val();
        student['userId'] = $('#submit-student').attr('userId');
        student['firstName'] = firstName.val();
        student['lastName'] = lastName.val();
        student['birthdate'] = birthdate.val();
        student['gender'] = gender.val();

        user['id'] = $('#submit-student').attr('userId');
        user['username'] = username.val();
        user['password'] = password.val();
        student['user'] = user;

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/student/update",
            data: JSON.stringify(student),
            dataType: 'json'
        }).done(function(response){
            if (response.responseCode === 200) {
                firstName.html(response.t.firstName);
                lastName.html(response.t.lastName);
                birthdate.html(response.t.birthdate);
                gender.html(response.t.gender);
                username.html(response.t.user.username);
                password.html(response.t.user.password);

                disableProfile();
            } else
                alert(response.responseMessage);
        }).fail(function(e) {
            console.log(e);
        });
    });

    courseTable.on("click", "td", function() {
        showLoader();
        exams.show();
        course.html($(this).closest('tr').attr("val1") + ' Exams');

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/exams",
            data: {courseId: $(this).closest('tr').attr("val")},
            dataType: 'json'
        }).done(function(response){
            $("#exam-table tbody").empty();
            if (response.responseCode === 200) {
                examTable.show();
                examError.hide();
                $("#exam-table tbody").empty();
                response.t.forEach(function (exam) {
                    $('#exam-table tbody').append(insertExamRow(exam));
                });
            } else {
                examTable.hide();
                examError.show();
                examError.html(response);
            }
            hideLoader();
        }).fail(function(e) {
            console.log(e);
        });
    });

    // TODO
    examTable.on("click", "td", function() {
        header.html('Exam');
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/exam/details",
            data: {examId: $(this).closest('tr').attr("val")},
            dataType: 'json'
        }).done(function(response){
            if (response.responseCode === 200) {
                exams.hide();
                $('#course-exams').hide();
                examForm.show();
                alert(response.responseMessage);
                $('#exam-header').html(response.t.header);
                $('#exam-description').html(response.t.description);
            } else
                alert(response.responseMessage);
        }).fail(function(e) {
            console.log(e);
        });
    });

    /**
     * Private methods | Radio event listener
     */

    function showExam(question) {
        let examView = '<div><div id="' + question.id + '"></div><table class="table" id="exam-questions" style="box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);">' +
            '<thead class="thead-light"><tr>' +
            '<th scope="col" style="width: 10%">Add</th><th scope="col" style="width: 90%">Student</th>' +
            '</tr></thead>\n' +
            '<tbody>' +
            '</tbody>' +
            '</table></div>';
    }

    function question(answer) {

    }

    function singleChoice(answer) {

    }

    function multipleChoice(answer) {

    }

    function disableProfile() {
        firstName.attr('disabled','disabled');
        lastName.attr('disabled','disabled');
        birthdate.attr('disabled','disabled');
        gender.attr('disabled','disabled');
        username.attr('disabled','disabled');
        password.attr('disabled','disabled');
        submitStudent.hide();
    }

    function insertExamRow(exam) {
        return '<tr val="' + exam.id + '">' +
            '<td class="description">' + exam.header + '</td>' +
            '<td class="description">' + exam.description + '</td>' +
            '</tr>';
    }

    function hideLoader() {
        $("#preloader").css("display", "none");
    }

    function showLoader() {
        $("#preloader").css("display", "block");
    }
});