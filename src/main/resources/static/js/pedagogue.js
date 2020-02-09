$(document).ready(function () {
    let header = $('#window-header');

    let pedagogueForm = $('#pedagogue-profile');
    let courseForm = $('#course-form');
    let addStudentsForm = $('#add-students');
    let examForm = $('#exam-form');
    let questionForm = $('#question-form');

    let courseTable = $('#course-table');
    let examTable = $('#exam-table');
    let questionTable = $('#question-table');
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
    questionTable.hide();
    questionBlock.hide();
    singleChoiceTable.hide();
    multipleChoiceTable.hide();

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
        submitPedagogue.show();
    });

    $('#profile').click(function () {
        header.html('Profile');

        pedagogueForm.show();
        courseForm.hide();
        courseTable.hide();
        examTable.hide();
        questionTable.hide();
        examForm.hide();
        questionForm.hide();
        addStudentsForm.hide();

        // warning.html("");
    });

    $('#allCourses').click(function () {
        header.html('All Courses');

        pedagogueForm.hide();
        courseForm.show();
        courseTable.show();
        questionTable.hide();
        examTable.hide();
        examForm.hide();
        questionForm.hide();
        addStudentsForm.hide();

        // warning.html("");
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
        questionTable.hide();
        examTable.show();
        examForm.hide();
        questionForm.hide();
        addStudentsForm.hide();

        // warning.html("");
    });

    $('#addExam').click(function () {
        header.html('Add Exam');

        pedagogueForm.hide();
        courseForm.hide();
        courseTable.hide();
        examTable.hide();
        questionTable.hide();
        examForm.show();
        questionForm.hide();
        addStudentsForm.hide();

        // warning.html("");

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/questions",
            data: {pedagogueId: $('#addExam').data("value")},
            dataType: 'json'
        }).done(function(response){
            $("#course").empty();
            $("#questions-table tbody").empty();
            if (response.responseCode === 200) {
                let courses = response.t.courses;
                let questions = response.t.questions;
                if (questions.length < 5)
                    alert("You must have at least 5 questions created!");
                else {
                    courses.forEach(function (course) {
                        let o = new Option(course.description, course.id);
                        $("#course").append(o);
                    });
                    questions.forEach(function (question) {
                        $('#questions-table tbody').append(insertQuestionRow(question));
                    });
                }
            } else
                alert(response.responseMessage)
        }).fail(function(e) {
            console.log(e);
        });
    });

    $('#allQuestions').click(function () {
        header.html('All Questions');

        pedagogueForm.hide();
        courseForm.hide();
        courseTable.hide();
        questionTable.show();
        examTable.hide();
        examForm.hide();
        questionForm.hide();
        addStudentsForm.hide();

        // warning.html("");
    });

    $('#addQuestion').click(function () {
        header.html('All Question');

        pedagogueForm.hide();
        courseForm.hide();
        courseTable.hide();
        questionTable.hide();
        examTable.hide();
        examForm.hide();
        questionForm.show();
        addStudentsForm.hide();

        // warning.html("");
    });



    /**
     * Form submit
     */

    pedagogueForm.submit(function(event) {
        event.preventDefault();

        let pedagogue = {};
        let user = {};
        pedagogue['id'] = $('#submit-pedagogue').val();
        pedagogue['userId'] = $('#submit-pedagogue').attr('userId');;
        pedagogue['firstName'] = firstName.val();
        pedagogue['lastName'] = lastName.val();
        pedagogue['birthdate'] = birthdate.val();
        pedagogue['gender'] = gender.val();

        user['id'] = $('#submit-pedagogue').attr('userId');
        user['username'] = username.val();
        user['password'] = password.val();
        pedagogue['user'] = user;

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/pedagogue/update",
            data: JSON.stringify(pedagogue),
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

    courseForm.submit(function(event) {
        event.preventDefault();

        let course = {};
        course['pedagogueId'] = $('#submit-course').val();
        course['description'] = courseDescription.val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/course/insert",
            data: JSON.stringify(course),
            dataType: 'json'
        }).done(function(response){
            if (response.responseCode === 201) {
                courseForm.trigger("reset");
                $('#course-table tr:last').after(insertCourseRow(response.t));
            } else
                alert(response);
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
                studentCourse['courseId'] = $(this).find('td.student-id').find('input[type="checkbox"]').attr("courseId");
                studentCourse['studentId'] = $(this).find('td.student-id').find('input[type="checkbox"]').val();
                studentCourses.push(studentCourse);
            }
        });

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/course/students/add",
            data: JSON.stringify(studentCourses),
            dataType: 'json'
        }).done(function(response){
            if (response.responseCode === 200) {
                addStudentsForm.hide();
                $('#selected-course').text("");
                $("#students-table tbody").empty();
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

        exam['courseId'] = $('#course').val();
        exam['pedagogueId'] = $('#submit-exam').val();
        exam['header'] = $('#exam-header').val();
        exam['description'] = $('#exam-description').val();

        let course = {};
        course['id'] = $('#course').val();
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
            else if (questions.length < 4)
                alert("At least 5 questions must be selected!");
            else
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "/exam/insert",
                    data: JSON.stringify(exam),
                    dataType: 'json'
                }).done(function (response) {
                    if (response.responseCode === 201) {
                        examForm.trigger("reset");
                        $('#exam-table tbody').append(insertExamRow(response.t));
                    } else
                        alert(response.responseMessage);
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
            url: "/question/insert",
            data: JSON.stringify(question),
            dataType: 'json'
        }).done(function(response){
            if (response.responseCode === 201) {
                questionForm.trigger("reset");
                $("#answer-label").hide();
                questionBlock.hide();
                singleChoiceTable.hide();
                multipleChoiceTable.hide();
                $('#question-table tbody').append(insertQuestion(response.t));
            } else
                alert(response.responseMessage);
        }).fail(function(e) {
            console.log(e);
        });
    });

    /**
     *
     */

    $('.add-student-to-course').click(function () {
        let description = $(this).attr("description");
        let courseId = $(this).attr("courseId");
        let facultyId = $(this).attr("facultyId");

        $('#selected-course').text(description);
        addStudentsForm.show();

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/course/students",
            data: {courseId: courseId, facultyId: facultyId},
            dataType: 'json'
        }).done(function(response){
            if (response.responseCode === 200) {
                $("#students-table tbody").empty();
                response.t.forEach(function (student) {
                    $('#students-table tbody').append(insertStudentRow(student));
                });
            } else
                alert(response.responseMessage);
        }).fail(function(e) {
            console.log(e);
        });
    });

    //TODO
    $('.exam-results').click(function () {
        let examId = $(this).attr("examId");

        // show results form

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/exam/results",
            data: {examId: examId},
            dataType: 'json'
        }).done(function(response){
            if (response === "There are no result for this exam!")
                alert(response);
            else {
                $("#exam-result-table tbody").empty();
                response.t.forEach(function (examResult) {
                    // $('#students-table tbody').append(insertStudentRow(student));
                    $('#exam-result-table tbody').append(insertExamResultRow(examResult));
                });
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    /**
     * Private methods | Radio event listener
     */

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
            '<td class="student-id"><input type="checkbox" value="' + student.id + '" courseid="' + student.courseId + '" id="' + student.id + '"><label for="' + student.id + '"></label></td>' +
            '<td class="student">' + student.firstName + ' ' + student.lastName + '</td>' +
            '</tr>';
    }

    function insertExamRow(exam) {
        return '<tr>' +
            '<td>' + exam.course.description +'</td>' +
            '<td>' + exam.header +'</td>' +
            '<td>' + exam.description +'</td>' +
            '<td>' +
            '<a examId="' + exam.id + '" class="exam-results"><i class="fa fa-plus" style="font-size:20px;color:lightblue;text-shadow:2px 2px 4px #000000;"></i></a>' +
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

    function insertQuestion(question) {
        return '<tr>' +
            '<td>' + question.questionType + '</td>' +
            '<td>' + question.value + '</td>' +
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