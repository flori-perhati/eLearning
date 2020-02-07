$(document).ready(function () {
    let header = $('#window-header');

    let pedagogueForm = $('#pedagogue-profile');
    let courseForm = $('#course-form');
    let addStudentsForm = $('#add-students');
    let examForm = $('#exam-form');
    let questionForm = $('#question-form');

    let courseTable = $('#course-table');
    let examTable = $('#exam-table');
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
    addStudentsForm.hide();
    examForm.hide();
    questionForm.hide();

    courseTable.hide();
    examTable.hide();
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
        examTable.hide();
        examForm.hide();
        questionForm.hide();

        // warning.html("");
    });

    $('#allCourses').click(function () {
        header.html('All Courses');

        pedagogueForm.hide();
        courseForm.show();
        courseTable.show();
        examTable.hide();
        examForm.hide();
        questionForm.hide();

        // warning.html("");
    });

    $('.add-student-to-course').click(function () {
        let description = $(this).attr("description");
        let courseId = $(this).attr("courseId");
        let facultyId = $(this).attr("facultyId");

        $('#selected-course').text(description);
        addStudentsForm.show();

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/getStudents",
            data: {courseId: courseId, facultyId: facultyId},
            dataType: 'json'
        }).done(function(response){
            if (response === "There are no students inserted!")
                alert(response);
            else {
                $("#students-table tbody").empty();
                response.forEach(function (student) {
                    $('#students-table tbody').append(insertStudentRow(student));
                });
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    $('#hide_student_form').click(function () {
        addStudentsForm.hide();
        $('#selected-course').text("");
        $("#students-table tbody").empty();
    });

    $('#allExams').click(function () {
        header.html('All Exams');

        pedagogueForm.hide();
        courseForm.hide();
        courseTable.hide();
        examTable.show();
        examForm.hide();
        questionForm.hide();

        // warning.html("");
    });

    $('#addExam').click(function () {
        header.html('All Courses');

        pedagogueForm.hide();
        courseForm.hide();
        courseTable.hide();
        examTable.hide();
        examForm.show();
        questionForm.hide();

        // warning.html("");

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/getQuestions",//?pedagogueId=" + $('#addExam').data("value"),
            data: {pedagogueId: $('#addExam').data("value")},
            dataType: 'json'
        }).done(function(response){
            $("#questions-table tbody").empty();
            if (response === "You have no question or course inserted!")
                alert(response);
            else {
                let courses = response.courses;
                let questions = response.questions;
                courses.forEach(function (course) {
                    let o = new Option(course.description, course.id);
                    $("#faculty").append(o);
                });
                questions.forEach(function (question) {
                    $('#questions-table tbody').append(insertQuestionRow(question));
                });
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    $('#addQuestion').click(function () {
        header.html('All Courses');

        pedagogueForm.hide();
        courseForm.hide();
        courseTable.hide();
        examTable.hide();
        examForm.hide();
        questionForm.show();

        // warning.html("");
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

    addStudentsForm.submit(function(event) {
        event.preventDefault();

        let studentCourses = [];

        $("table#students-table tbody tr").each(function () {
            if ($(this).find('td.student-id').find('input[type="checkbox"]').is(':checked')) {
                let studentCourse = {};
                studentCourse['courseId'] = '';
                studentCourse['studentId'] = $(this).find('td.student-id').find('input[type="checkbox"]').val();
                studentCourses.push(studentCourse);
            }
        });

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/addStudentToCourse",
            data: JSON.stringify(studentCourses),
            dataType: 'json'
        }).done(function(response){
            if (response === "Something went wrong!")
                alert(response);
            else {
                addStudentsForm.hide();
                $('#selected-course').text("");
                $("#students-table tbody").empty();
                alert(response)
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    examForm.submit(function (event) {
        event.preventDefault();

        let exam = {};
        let questions = [];
        let flag = false;

        exam['courseId'] = $('#faculty').val();
        exam['pedagogueId'] = $('#submit-exam').val();
        exam['header'] = $('#exam-header').val();
        exam['description'] = $('#exam-description').val();

        let course = {};
        course['id'] = $('#faculty').val();
        course['pedagogueId'] = $('#submit-exam').val();
        course['description'] = $('#faculty').html();

        exam['course'] = course;

        $("table#questions-table tbody tr").each(function () {
            if ($(this).find('td.question-id').find('input[type="checkbox"]').is(':checked')) {
                let question = {};

                let points = $(this).find('td.question-points').find('input[type="number"]').val();
                if(points.length === 0) {
                    alert("Please evaluate question/s selected!");
                    flag = true;
                    return false;
                }

                question['id'] = $(this).find('td.question-id').find('input[type="checkbox"]').val();
                question['questionType'] = $(this).find('td.question-type').html();
                question['value'] = $(this).find('td.question-value').html();
                question['points'] = points;
                questions.push(question);
            }
        });

        if (!flag) {
            exam['questions'] = questions;

            if (questions.length === 0)
                alert("Add questions to exam!");
            else
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/createExam",
                    data: JSON.stringify(exam),
                    dataType: 'json'
                }).done(function (response) {
                    if (response === "Something went wrong!")
                        alert(response);
                    else {
                        examForm.trigger("reset");
                        $('#exam-table tbody').append(insertExamRow(response));
                        alert("Success");
                    }
                }).fail(function (e) {
                    console.log(e);
                });
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

    function insertStudentRow(student) {
        return '<tr>' +
            '<td class="student-id"><input type="checkbox" value="' + student.id + '" id="' + student.id + '"><label for="' + student.id + '"></label></td>' +
            '<td class="student">' + student.firstName + ' ' + student.lastName + '</td>' +
            '</tr>';
    }

    function insertExamRow(exam) {
        return '<tr>' +
            '<td>' + exam.course.description +'</td>' +
            '<td>' + exam.header +'</td>' +
            '<td>' + exam.description +'</td>' +
            '<td>' +
            '<a data-value="' + exam + '" class="exam-results"><i class="fa fa-plus" style="font-size:20px;color:lightblue;text-shadow:2px 2px 4px #000000;"></i></a>' +
            '</td>' +
            '</tr>';
    }

    function insertQuestionRow(question) {
        return '<tr>' +
            '<td class="question-id"><input type="checkbox" value="' + question.id + '" id="' + question.id + '"><label for="' + question.id + '"></label></td>' +
            '<td class="question-type">' + question.questionType + '</td>' +
            '<td class="question-value">' + question.value + '</td>' +
            '<td class="question-points"><input type="number" style="text-align: end"></td>' +
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