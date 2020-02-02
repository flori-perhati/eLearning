$(document).ready(function () {
    let header = $('#window-header');

    let pedagogueForm = $('#pedagogue-profile');
    let courseForm = $('#course-form');
    let examForm = $('#exam-form');
    let questionForm = $('#question-form');

    let courseTable = $('#course-table');
    let singleChoiceTable = $('#single-choice-table');
    let multipleChoiceTable = $('#multiple-choice-table');

    let questionBlock = $('#yes-no');

    let firstName = $('#pedagogue-first-name');
    let lastName = $('#pedagogue-last-name');
    let birthdate = $('#pedagogue-birthdate');
    let gender = $('#pedagogue-gender');
    let username = $('#pedagogue-username');
    let password = $('#pedagogue-password');
    let submitPedagogue = $('#submit-pedagogue');

    let courseDescription = $('#course-description');

    pedagogueForm.show();
    courseForm.hide();
    examForm.hide();
    questionForm.hide();

    courseTable.hide();
    questionBlock.hide();
    singleChoiceTable.hide();
    multipleChoiceTable.hide();

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
        pedagogue['id'] = $('#submit-pedagogue').val();
        pedagogue['firstName'] = firstName.val();
        pedagogue['lastName'] = lastName.val();
        pedagogue['birthdate'] = birthdate.val();
        pedagogue['gender'] = gender.val();

        user['username'] = username.val();
        user['password'] = password.val();
        pedagogue['user'] = user;

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/updatePedagogue",
            data: JSON.stringify(pedagogue),
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

    courseForm.submit(function(event) {
        event.preventDefault();

        let course = {};
        course['pedagogueId'] = $('#submit-course').val();
        course['description'] = courseDescription.val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/createCourse",
            data: JSON.stringify(course),
            dataType: 'json'
        }).done(function(response){
            if (response === "Something went wrong!")
                alert(response);
            else {
                courseForm.trigger("reset");
                $('#course-table tr:last').after(insertCourseRow(response));
                alert('Success');
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    $('.add-student-to-course').click(function () {
        let courseId = $('.add-student-to-course').data("value");
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/addStudentToCourse",
            data: JSON.stringify(courseId),
            dataType: 'json'
        }).done(function(response){
            if (response === "Something went wrong!")
                alert(response);
            else {
                courseForm.trigger("reset");
                // $('#course-table tr:last').after(insertCourseRow(response));
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    $('input[type=radio]').click(function(){
        if (this.name === "question-type") {
            $("#answer-label").show();
            if (this.value === "Yes/No") {
                questionBlock.show();

                singleChoiceTable.hide();
                $('.single-choice-area').removeAttr('required');

                multipleChoiceTable.hide();
                $('.multiple-choice-area').removeAttr('required');
            } else if (this.value === "Single Choice") {
                questionBlock.hide();

                singleChoiceTable.show();
                $('.single-choice-area').prop('required',true);

                multipleChoiceTable.hide();
                $('.multiple-choice-area').removeAttr('required');
            } else if (this.value === "Multiple Choice") {
                questionBlock.hide();

                singleChoiceTable.hide();
                $('.single-choice-area').removeAttr('required');

                multipleChoiceTable.show();
                $('.multiple-choice-area').prop('required',true);
            }
        }
    });

    questionForm.submit(function (event) {
        event.preventDefault();

        let question = {};
        question["pedagogueId"] = $('#submit-question').val();
        question["value"] = $('#question-description').val();
        question["questionType"] = $("input[name='question-type']:checked").val();

        let answers = [];
        switch (question["questionType"]) {
            case "Yes/No":
                let answer = {};
                answer["isCorrect"] = true;
                answer["value"] = $("input[name='question-boolean-value']:checked").val();
                answers.push(answer)
                break;
            case "Single Choice":
                $("table#single-choice-table tbody tr").each(function () {
                    let answer = {};
                    answer["isCorrect"] = $(this).find('td.answer-choice').find('input[type="radio"]').is(':checked');
                    answer["value"] = $(this).find('td.answer-value').find('textarea').val();
                    answers.push(answer);
                });
                break;
            case "Multiple Choice":
                $("table#multiple-choice-table tbody tr").each(function () {
                    let answer = {};
                    answer["isCorrect"] = $(this).find('td.answer-choice').find('input[type="checkbox"]').is(':checked');
                    answer["value"] = $(this).find('td.answer-value').find('textarea').val();
                    answers.push(answer);
                });
                break;
        }

        question["answers"] = answers;

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/createQuestion",
            data: JSON.stringify(question),
            dataType: 'json'
        }).done(function(response){
            if (response === "Something went wrong!")
                alert(response);
            else {
                questionForm.trigger("reset");
                $("#answer-label").hide();
                questionBlock.hide();
                singleChoiceTable.hide();
                multipleChoiceTable.hide();
                alert(response);
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    function insertQuestionRow(question) {
        return '<tr>' +
            '<td style="width: 85%">' + course.description +'</td>' +
            '<td>' +
            '<a data-value="' + course.id + '" class="add-student-to-course"><i class="fa fa-plus" style="font-size:20px;color:lightblue;text-shadow:2px 2px 4px #000000;"></i></a>' +
            '</td>' +
            '</tr>';
    }

    function insertCourseRow(course) {
        return '<tr>' +
            '<td style="width: 85%">' + course.description +'</td>' +
            '<td>' +
            '<a data-value="' + course.id + '" class="add-student-to-course"><i class="fa fa-plus" style="font-size:20px;color:lightblue;text-shadow:2px 2px 4px #000000;"></i></a>' +
            '</td>' +
            '</tr>';
    }

    function disableProfile() {
        firstName.attr('disabled','disabled');
        lastName.attr('disabled','disabled');
        birthdate.attr('disabled','disabled');
        gender.attr('disabled','disabled');
        username.attr('disabled','disabled');
        password.attr('disabled','disabled');
        submitPedagogue.hide();
    }
});