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

    studentForm.submit(function(event) {
        event.preventDefault();

        let student = {};
        let user = {};
        student['id'] = $('#submit-student').val();
        student['firstName'] = firstName.val();
        student['lastName'] = lastName.val();
        student['birthdate'] = birthdate.val();
        student['gender'] = gender.val();

        user['username'] = username.val();
        user['password'] = password.val();
        student['user'] = user;

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/updateStudent",
            data: JSON.stringify(student),
            dataType: 'json'
        }).done(function(response){
            if (response === "Something went wrong!")
                alert(response);
            else {
                firstName.html(response.firstName);
                lastName.html(response.lastName);
                birthdate.html(response.birthdate);
                gender.html(response.gender);
                username.html(response.user.username);
                password.html(response.user.password);

                disableProfile();
            }
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
            url: "/getExams",
            data: {courseId: $(this).closest('tr').attr("val")},
            dataType: 'json'
        }).done(function(response){
            $("#exam-table tbody").empty();
            if (response === "This course doesn't have exams!") {
                examTable.hide();
                examError.show();
                examError.html(response);
            } else {
                examTable.show();
                examError.hide();
                $("#exam-table tbody").empty();
                response.forEach(function (exam) {
                    $('#exam-table tbody').append(insertExamRow(exam));
                });
            }
            hideLoader();
        }).fail(function(e) {
            console.log(e);
        });
    });

    examTable.on("click", "td", function() {
        exams.show();
        alert($(this).closest('tr').attr("val"));

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/getExamData",
            data: {examId: $(this).closest('tr').attr("val")},
            dataType: 'json'
        }).done(function(response){
            if (response === "Something went wrong!")
                alert(response);
            else {
                alert("Success");
                // generate exam
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    function showExam(question) {
        let examView = '<div><div id="' + question.id + '"></div><table class="table" id="exam-questions" style="box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);">' +
            '<thead class="thead-light"><tr>' +
            '<th scope="col" style="width: 10%">Add</th><th scope="col" style="width: 90%">Student</th>' +
            '</tr></thead>\n' +
            '<tbody>' +
            '</tbody>' +
            '</table></div>';
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