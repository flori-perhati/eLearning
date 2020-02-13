$(document).ready(function () {
    // hideLoader();
    let header = $('#window-header');

    let studentForm = $('#student-profile');
    let courseTable = $('#course-table');
    let exams = $('#exams');
    let examTable = $('#exam-table');
    let examForm = $('#exam-form');
    let resultsTable = $('#results-table');

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
    resultsTable.hide();

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
        examForm.hide();
        $('#course-exams').hide();
        resultsTable.hide();
        // warning.html("");
    });

    $('#courses').click(function () {
        header.html('All Courses');

        studentForm.hide();
        courseTable.show();
        exams.hide();
        examForm.hide();
        $('#course-exams').show();
        resultsTable.hide();
        // warning.html("");
    });

    $('#hide-exam').click(function () {
        examForm.hide();
        $('#course-exams').show();
    });

    $('#results').click(function () {
        header.html('All Courses');

        studentForm.hide();
        courseTable.hide();
        exams.hide();
        examForm.hide();
        $('#course-exams').hide();
        resultsTable.show();
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
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/exams",
            data: {courseId: $(this).closest('tr').attr("val")},
            dataType: 'json'
        }).done(function(response){
            $("#exam-table tbody").empty();
            if (response.responseCode === 200) {
                exams.show();
                course.html($(this).closest('tr').attr("val1") + ' Exams');
                examTable.show();
                examError.hide();
                $("#exam-table tbody").empty();
                response.t.forEach(function (exam) {
                    $('#exam-table tbody').append(insertExamRow(exam));
                });
            } else {
                examTable.hide();
                examError.show();
                examError.text(response.responseMessage);
            }
            // hideLoader();
        }).fail(function(e) {
            console.log(e);
        });
    });

    examTable.on("click", "td", function() {
        header.html('Exam');
        $('#exam-course').html('<b>' + $('#course').text().substring(0, $('#course').text().length - 6) + '</b>');
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/exam/details",
            data: {examId: $(this).closest('tr').attr("val"), studentId: $('#submit-student').val()},
            dataType: 'json'
        }).done(function(response){
            if (response.responseCode === 200) {
                exams.hide();
                $('#question-container').html("");
                $('#course-exams').hide();
                examForm.show();

                $('#exam-header').attr('examId', response.t.id);
                $('#exam-header').attr('pedagogueId', response.t.pedagogueId);
                $('#exam-header').html('<b>' + response.t.header + '</b>');
                $('#exam-description').html('<b>' + response.t.description + '</b>');

                let index = 0;
                let l = "";
                response.t.examQuestions.forEach(function (examQuestion) {
                    l += showQuestions(examQuestion, index);
                    index++;
                });
                $('#question-container').html(l);
                $('#questions-number').attr('nrOfQuestions', index);
            } else
                alert(response.responseMessage);
        }).fail(function(e) {
            console.log(e);
        });
    });

    examForm.submit(function(event) {
        event.preventDefault();

        let currentDate = new Date();
        let months = ["January","February","March","April","May","June","July","August","September","October","November","December"];

        let examTaken = {};
        examTaken['examId'] = $('#exam-header').attr('examId');
        examTaken['studentId'] = $('#submit-student').val();
        examTaken['pedagogueId'] = $('#exam-header').attr('pedagogueId');
        examTaken['holdingDate'] = currentDate.getDate() + '  ' + months[currentDate.getMonth()] + ' ' + currentDate.getFullYear();

        let count = $('#questions-number').attr('nrOfQuestions');
        let totalPoints = 0;
        let studentPoints = 0;

        for (let index = 0; index < count; index ++) {
            let questionType = "";
            let questionId = 0;
            let questionPoints = 0;
            let earnedPoints = 0;

            $('table#question' + index + ' thead tr ').each(function(){
                questionType = $(this).find('th.question-value').attr('questionType');
                questionId = $(this).find('th.question-value').attr('questionId');
                questionPoints = parseFloat($(this).find('th.question-points').text().substring(0, $(this).find('th.question-points').text().length - 8));
            });

            switch (questionType) {
                case "Yes/No":
                    $("table#question" + index + " tbody tr").each(function () {
                        if ($(this).find('td.q'+ index +'').find('input[type="radio"]').is(':checked')) {
                            if ($(this).find('td.a'+ index +'').attr('isCorrect') === 'true')
                                earnedPoints = questionPoints;
                        }
                    });
                    break;
                case "Single Choice":
                    $("table#question" + index + " tbody tr").each(function () {
                        if ($(this).find('td.q'+ index +'').find('input[type="radio"]').is(':checked')) {
                            if ($(this).find('td.a'+ index +'').attr('isCorrect') === 'true')
                                earnedPoints = questionPoints;
                        }
                    });
                    break;
                case "Multiple Choice":

                    let numberOfCorrectAnswers = 0;
                    let studentsCorrectAnswers = 0;

                    $("table#question" + index + " tbody tr").each(function () {
                        if ($(this).find('td.a'+ index +'').attr('isCorrect') === 'true')
                            numberOfCorrectAnswers += 1;

                        let checkbox = $(this).find('td.q'+ index +'').find('input[type="checkbox"]');
                        if (checkbox.is(':checked')) {
                            if ($(this).find('td.a'+ index +'').attr('isCorrect') === 'true')
                                studentsCorrectAnswers += 1;
                        }
                    });
                    if (numberOfCorrectAnswers === 0) {
                        if (studentsCorrectAnswers === 0)
                            earnedPoints = questionPoints;
                    } else {
                        let correctAnswers = studentsCorrectAnswers / numberOfCorrectAnswers;
                        earnedPoints = correctAnswers * questionPoints;
                    }
                    break;
            }

            totalPoints += questionPoints;
            studentPoints += earnedPoints;
        }
        examTaken['result'] = studentPoints + '/' + totalPoints;

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/examTaken/insert",
            data: JSON.stringify(examTaken),
            dataType: 'json'
        }).done(function(response){
            if (response.responseCode === 200) {
                examForm.hide();
                $('#course-exams').show();
                $('#results-table tbody').append(insertExamTakenRow(response.t));
                alert('Exam finished, your result is: ' + response.t.result);
            } else
                alert(response.responseMessage);
        }).fail(function(e) {
            console.log(e);
        });
    });

    /**
     * Private methods | Radio event listener
     */

    function showQuestions(question, index) {
        let questionView = '<table class="table" id="question' + index + '" style="margin-bottom: 50px; box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);">' +
            '<thead class="thead-light"><tr>' +
            '<th scope="col" style="width: 80%" class="question-value" questionId="' + question.id + '" questionType="' + question.questionType + '">' + question.questionDescription + '</th>' +
            '<th scope="col" style="width: 20%; text-align: end" class="question-points">' + question.questionPoints + ' point/s</th>' +
            '</tr></thead><tbody>';

        switch (question.questionType) {
            case "Yes/No":
                questionView += yesNo(question.answers[0], index);
                break;
            case "Single Choice":
                question.answers.forEach(function (answer) {
                    questionView += singleChoice(answer, index);
                });
                break;
            case "Multiple Choice":
                question.answers.forEach(function (answer) {
                    questionView += multipleChoice(answer, index);
                });
                break;
        }

        questionView += '</tbody></table>';
        return questionView;
    }

    function yesNo(answer, index) {
        let yes = false;
        let no = false;

        if (answer.value === 'Yes')
            yes = true;
        else if (answer.value === 'No')
            no = true;

        return '<tr><td class="q' + index + '"><input type="radio" id="yes' + answer.id + '" name="' + index + '" value="Yes"><label for="yes' + answer.id + '">Yes</label></td>' +
            '<td class="a' + index + '" style="width: 20%; text-align: end" isCorrect="' + yes + '"></td></tr>' +
            '<tr><td class="q' + index + '"><input type="radio" id="no' + answer.id + '" name="' + index + '" value="No"><label for="no' + answer.id + '">No</label></td>' +
            '<td class="a' + index + '" style="width: 20%; text-align: end" isCorrect="' + no + '"></td></tr>';
    }

    function singleChoice(answer, index) {
        return '<tr><td class="q' + index + '"><input type="radio" id="' + answer.id + '" name="' + index + '"><label for="' + answer.id + '">' + answer.value + '</label></td>' +
            '<td class="a' + index + '" style="width: 20%; text-align: end" isCorrect="' + answer.isCorrect + '"></td></tr>';
    }

    function multipleChoice(answer, index) {
        return '<tr><td class="q' + index + '"><input type="checkbox" id="' + answer.id + '" name="' + index + '"><label for="' + answer.id + '">' + answer.value + '</label></td>' +
            '<td class="a' + index + '" style="width: 20%; text-align: end" isCorrect="' + answer.isCorrect + '"></td></tr>';
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

    function insertExamTakenRow(examTaken) {
        return '<tr>' +
            '<td>' + examTaken.course +'</td>' +
            '<td>' + examTaken.examHeader +'</td>' +
            '<td>' + examTaken.examDescription +'</td>' +
            '<td>' + examTaken.holdingDate +'</td>' +
            '<td style="width: 15%; text-align: right">' + examTaken.result +'</td>' +
            '</tr>';
    }

    // function hideLoader() {
    //     $("#preloader").css("display", "none");
    // }
    //
    // function showLoader() {
    //     $("#preloader").css("display", "block");
    // }
});