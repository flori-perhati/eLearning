<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Flori
  Date: 08/01/2020
  Time: 9:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>eLearning</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style type="text/css">
        .btn {
            font-size: 15px;
            font-weight: bold;
        }
        /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
        .row.content {height: 1500px}

        /* Set gray background color and 100% height */
        .sidenav {
            background-color: #f1f1f1;
            height: 100%;
        }

        /* Set black background color, white text and some padding */
        footer {
            background-color: #555;
            color: white;
            padding: 15px;
        }

        /* On small screens, set height to 'auto' for sidenav and grid */
        @media screen and (max-width: 767px) {
            .sidenav {
                height: auto;
                padding: 15px;
            }
            .row.content {height: auto;}
        }

        body {
            color: #566787;
            background: #fafafa;
            font-family: 'Varela Round', sans-serif;
            font-size: 13px;
        }
        .table-wrapper {
            background: #fff;
            padding: 20px 25px;
            margin: 10px 0;
            border-radius: 3px;
            box-shadow: 0 2px 2px rgba(0,0,0,.05);
        }
        .table-title {
            background: #435d7d;
            color: #fff;
            padding: 16px 30px 15px 15px;
            margin: -20px -25px 10px;
            border-radius: 3px 3px 0 0;
        }
        .table-title h2 {
            margin: 5px 0 0;
            font-size: 24px;
        }
        .table-title .btn-group {
            float: right;
        }
        .table-title .btn {
            color: #fff;
            float: right;
            font-size: 13px;
            border: none;
            min-width: 50px;
            border-radius: 2px;
            border: none;
            outline: none !important;
            margin-left: 10px;
        }
        .table-title .btn i {
            float: left;
            font-size: 21px;
            margin-right: 5px;
        }
        .table-title .btn span {
            float: left;
            margin-top: 2px;
        }
        table.table tr th, table.table tr td {
            border-color: #e9e9e9;
            padding: 12px 15px;
            vertical-align: middle;
        }
        table.table-striped tbody tr:nth-of-type(odd) {
            background-color: #fcfcfc;
        }
        table.table-striped.table-hover tbody tr:hover {
            background: #f5f5f5;
        }
        table.table th i {
            font-size: 13px;
            margin: 0 5px;
            cursor: pointer;
        }
        table.table td:last-child i {
            opacity: 0.9;
            font-size: 22px;
            margin: 0 5px;
        }
        table.table td a {
            font-weight: bold;
            color: #566787;
            display: inline-block;
            text-decoration: none;
            outline: none !important;
        }
        table.table td a:hover {
            color: #2196F3;
        }
        table.table td a.edit {
            color: #FFC107;
        }
        table.table td a.delete {
            color: #F44336;
        }
        table.table td i {
            font-size: 19px;
        }
        .pagination li a {
            border: none;
            font-size: 13px;
            min-width: 30px;
            min-height: 30px;
            color: #999;
            margin: 0 2px;
            line-height: 30px;
            border-radius: 2px !important;
            text-align: center;
            padding: 0 6px;
        }
        .pagination li a:hover {
            color: #666;
        }
        .pagination li.active a, .pagination li.active a.page-link {
            background: #03A9F4;
        }
        .pagination li.active a:hover {
            background: #0397d6;
        }
        .pagination li.disabled i {
            color: #ccc;
        }
        .pagination li i {
            font-size: 16px;
            padding-top: 6px
        }
        .custom-checkbox input[type="checkbox"] {
            opacity: 0;
            position: absolute;
            margin: 5px 0 0 3px;
            z-index: 9;
        }
        .custom-checkbox label:before{
            width: 18px;
            height: 18px;
        }
        .custom-checkbox label:before {
            content: '';
            margin-right: 10px;
            display: inline-block;
            vertical-align: text-top;
            background: white;
            border: 1px solid #bbb;
            border-radius: 2px;
            box-sizing: border-box;
            z-index: 2;
        }
        .custom-checkbox input[type="checkbox"]:checked + label:after {
            content: '';
            position: absolute;
            left: 6px;
            top: 3px;
            width: 6px;
            height: 11px;
            border: solid #000;
            border-width: 0 3px 3px 0;
            transform: inherit;
            z-index: 3;
            transform: rotateZ(45deg);
        }
        .custom-checkbox input[type="checkbox"]:checked + label:before {
            border-color: #03A9F4;
            background: #03A9F4;
        }
        .custom-checkbox input[type="checkbox"]:checked + label:after {
            border-color: #fff;
        }
        .custom-checkbox input[type="checkbox"]:disabled + label:before {
            color: #b8b8b8;
            cursor: auto;
            box-shadow: none;
            background: #ddd;
        }
        /* Modal styles */
        .modal .modal-dialog {
            max-width: 400px;
        }
        .modal .modal-header, .modal .modal-body, .modal .modal-footer {
            padding: 20px 30px;
        }
        .modal .modal-content {
            border-radius: 3px;
        }
        .modal .modal-footer {
            background: #ecf0f1;
            border-radius: 0 0 3px 3px;
        }
        .modal .modal-title {
            display: inline-block;
        }
        .modal .form-control {
            border-radius: 2px;
            box-shadow: none;
            border-color: #dddddd;
        }
        .modal textarea.form-control {
            resize: vertical;
        }
        .modal .btn {
            border-radius: 2px;
            min-width: 100px;
        }
        .modal form label {
            font-weight: normal;
        }
    </style>
    <script type="text/javascript">
        function profileClick() {
            document.getElementById("profile").classList.add("active");
            document.getElementById("course").classList.remove("active");
            document.getElementById("student").classList.remove("active");
            document.getElementById("exam").classList.remove("active");
            document.getElementById("question").classList.remove("active");

            document.getElementById("main-header").textContent = "Profile";

            document.getElementById("profile-container").hidden = false;
            document.getElementById("course-container").hidden = true;
            document.getElementById("student-container").hidden = true;
            document.getElementById("exam-container").hidden = true;
            document.getElementById("question-container").hidden = true;
        }
        function courseClick() {
            document.getElementById("profile").classList.remove("active");
            document.getElementById("course").classList.add("active");
            document.getElementById("student").classList.remove("active");
            document.getElementById("exam").classList.remove("active");
            document.getElementById("question").classList.remove("active");

            document.getElementById("main-header").textContent = "Courses";

            document.getElementById("profile-container").hidden = true;
            document.getElementById("course-container").hidden = false;
            document.getElementById("student-container").hidden = true;
            document.getElementById("exam-container").hidden = true;
            document.getElementById("question-container").hidden = true;
        }
        function studentClick() {
            document.getElementById("profile").classList.remove("active");
            document.getElementById("course").classList.remove("active");
            document.getElementById("student").classList.add("active");
            document.getElementById("exam").classList.remove("active");
            document.getElementById("question").classList.remove("active");

            document.getElementById("main-header").textContent = "Students";

            document.getElementById("profile-container").hidden = true;
            document.getElementById("course-container").hidden = true;
            document.getElementById("student-container").hidden = false;
            document.getElementById("exam-container").hidden = true;
            document.getElementById("question-container").hidden = true;
        }
        function examClick() {
            document.getElementById("profile").classList.remove("active");
            document.getElementById("course").classList.remove("active");
            document.getElementById("student").classList.remove("active");
            document.getElementById("exam").classList.add("active");
            document.getElementById("question").classList.remove("active");

            document.getElementById("main-header").textContent = "Exams";

            document.getElementById("profile-container").hidden = true;
            document.getElementById("course-container").hidden = true;
            document.getElementById("student-container").hidden = true;
            document.getElementById("exam-container").hidden = false;
            document.getElementById("question-container").hidden = true;
        }
        function questionClick() {
            document.getElementById("profile").classList.remove("active");
            document.getElementById("course").classList.remove("active");
            document.getElementById("student").classList.remove("active");
            document.getElementById("exam").classList.remove("active");
            document.getElementById("question").classList.add("active");

            document.getElementById("main-header").textContent = "Questions";

            document.getElementById("profile-container").hidden = true;
            document.getElementById("course-container").hidden = true;
            document.getElementById("student-container").hidden = true;
            document.getElementById("exam-container").hidden = true;
            document.getElementById("question-container").hidden = false;
        }

        $(document).ready(function(){
            $('input[type=radio]').click(function(){
                if (this.name === "question-type") {
                    if (this.value === "Yes/No") {
                        document.getElementById("answer-label").hidden = false;
                        document.getElementById("yes/no").hidden = false;
                        document.getElementById("single-choice").hidden = true;
                        document.getElementById("multiple-choice").hidden = true;
                    }

                    if (this.value === "Single Choice") {
                        document.getElementById("answer-label").hidden = false;
                        document.getElementById("yes/no").hidden = true;
                        document.getElementById("single-choice").hidden = false;
                        document.getElementById("multiple-choice").hidden = true;
                    }

                    if (this.value === "Multiple Choice") {
                        document.getElementById("answer-label").hidden = false;
                        document.getElementById("yes/no").hidden = true;
                        document.getElementById("single-choice").hidden = true;
                        document.getElementById("multiple-choice").hidden = false;
                    }
                }
            });
        });

        function addQuestion() {
            var question = {};
            question["pedagogueId"] = "${pedagogue.id}";
            question["description"] = document.getElementById("question-description").value;
            question["questionType"] = $("input[name='question-type']:checked").val();

            var answers = [];
            if (question["questionType"] === "Yes/No") {
                const answer = {};
                answer["value"] = $("input[name='question-boolean-value']:checked").val();
                answer["isCorrect"] = true;
                answers.push(answer)
            }
            if (question["questionType"] === "Single Choice") {
                $("table#single-choice tbody tr").each(function () {
                    const answer = {};
                    answer["value"] =  $(this).find('td.answer').find('textarea').val();
                    answer["isCorrect"] =  $(this).find('td.answer-value').find('input[type="radio"]').is(':checked');
                    answers.push(answer);
                });
            }
            if (question["questionType"] === "Multiple Choice") {
                $("table#multiple-choice tbody tr").each(function () {
                    const answer = {};
                    answer["value"] =  $(this).find('td.answer').find('textarea').val();
                    answer["isCorrect"] =  $(this).find('td.answer-value').find('input[type="checkbox"]').is(':checked');
                    answers.push(answer);
                });
            }

            question["answers"] = answers;

            $.ajax({
                type : "POST",
                contentType : 'application/json; charset=utf-8',
                dataType : 'json',
                url : "addQuestion/${pedagogue.id}",
                data : JSON.stringify(question)
            });
        }

        function addAnswer() {
            let questionType = $('input[name="question-type"]:checked').val();
            if (questionType === "Single Choice") {
                let singleChoiceTable = document.getElementById("single-choice");
                let row = singleChoiceTable.insertRow(singleChoiceTable.size);
                let cell1 = row.insertCell(0);
                let cell2 = row.insertCell(1);
                cell1.innerHTML = "<td style=\"padding:0 0 0 15px;\" class=\"answer\"><textarea type=\"text\" class=\"form-control\" required=\"required\"></textarea></td>";
                cell2.innerHTML = "<td style=\"padding:0 0 0 15px;\" class=\"answer-value\"><input type=\"radio\"></td>";
            }
            if (questionType === "Multiple Choice") {
                let multipleChoiceTable = document.getElementById("multiple-choice");
                let row1 = multipleChoiceTable.insertRow(multipleChoiceTable.size);
                let cell11 = row1.insertCell(0);
                let cell22 = row1.insertCell(1);
                cell11.innerHTML = "<td style=\"padding:0 0 0 15px;\" class=\"answer\"><textarea type=\"text\" class=\"form-control\" required=\"required\"></textarea></td>";
                cell22.innerHTML = "<td style=\"padding:0 0 0 15px;\" class=\"answer-value\"><input type=\"checkbox\"></td>";
            }
        }
    </script>
</head>

<body>
<div class="container-fluid">
    <div class="row content">
        <div class="col-sm-3 sidenav">
            <h4 id="username">Pedagogue</h4>
            <ul class="nav nav-pills nav-stacked">
                <li class="active" onclick="profileClick()" id="profile"><a>Profile</a></li>
                <li onclick="courseClick()" id="course"><a>Courses</a></li>
                <li onclick="studentClick()" id="student"><a>Students</a></li>
                <li onclick="examClick()" id="exam"><a>Exams</a></li>
                <li onclick="questionClick()" id="question"><a>Questions</a></li>
            </ul><br>
        </div>

        <div class="col-sm-9">
            <h4><small>RECENT DATA</small></h4><hr><h2 id="main-header">Profile</h2><br>

            <div id="profile-container">
                <form:form action="editPedagogue/${pedagogue.id}" method="post" modelAttribute="pedagogue">
                    <table>
                        <tbody>
                            <tr><td><label>First Name</label><div class="form-group"><form:input type="text" disabled="true" class="form-control" path="firstName"/></div></td>
                                <td><div style="margin-left: 50px"><label>Last Name</label><div class="form-group"><form:input type="text" disabled="true" class="form-control" path="lastName"/></div></div></td></tr>
                            <tr><td><label>Faculty Description</label><div class="form-group"><form:input type="text" disabled="true" class="form-control" path="faculty.description"/></div></td>
                                <td><div style="margin-left: 50px"><label>Registration Date</label><div class="form-group"><form:input type="text" disabled="true" class="form-control" path="registerDate"/></div></div></td></tr>
                            <tr><td><label>Username</label><div class="form-group"><form:input type="text" disabled="true" class="form-control" path="user.username"/></div></td>
                                <td><div style="margin-left: 50px"><label>Password</label><div class="form-group"><form:input type="text" disabled="true" class="form-control" path="user.password"/></div></div></td></tr>
                            <tr><td><div class="form-group"><button type="submit" class="btn btn-primary btn-block" value="Edit">Edit</button></div></td><td></td><td></td></tr>
                        </tbody>
                    </table>
                </form:form>
            </div>

            <!-- Course List -->
            <div id="course-container" class="container" hidden="true">
                <div class="table-wrapper">
                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-6"><h2>Manage Data</h2></div>
                            <div class="col-sm-6"><a href="#addCourseModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Create</span></a></div>
                        </div>
                    </div>
                    <table class="table table-striped table-hover">
                        <thead><tr><th style="width: auto">Course Description</th><th>Actions</th></tr></thead>
                        <tbody>
                        <c:forEach var="course" items="${courses}">
                            <tr>
                                <td style="width: auto">${course.description}</td>
                                <td >
                                    <a href="deleteCourse/${course.id}" class="delete" ><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
                                    <a href="addStudentToCourse/${course.id}" class="edit" >Add Student</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Add Course Modal HTML -->
            <div id="addCourseModal" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form:form method="post" action="addCourse/${pedagogue.id}" modelAttribute="myModel">
                            <div class="modal-header">
                                <h4 class="modal-title">Add Course</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>Description</label>
                                    <form:input type="text" class="form-control" required="required" path="course.description"/>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                                <input type="submit" class="btn btn-info" value="Save">
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>

            <!-- Student List -->
            <div id="student-container" class="container" hidden="true">
                <div class="table-wrapper">
                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-6"><h2>Manage Data</h2></div>
                        </div>
                    </div>
                    <table class="table table-striped table-hover">
                        <thead><tr style="width: auto"><th style="width: auto">First Name</th><th style="width: auto">Last Name</th>
                            <th style="width: auto">Faculty</th><th style="width: auto">Register Date</th><th style="width: min-content">Actions</th></tr></thead>
                        <tbody>
                        <c:forEach var="student" items="${students}">
                            <tr>
                                <td style="width: auto">${student.firstName}</td>
                                <td style="width: auto">${student.lastName}</td>
                                <td style="width: auto">${student.faculty.description}</td>
                                <td style="width: auto">${student.registerDate}</td>
                                <td style="width: min-content"><a href="viewResults/${student.id}" class="edit" ><i class="material-icons" data-toggle="tooltip" title="Results">&#xE254;</i></a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Exam List -->
            <div id="exam-container" class="container" hidden="true">
                <div class="table-wrapper">
                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-6"><h2>Manage Data</h2></div>
                            <div class="col-sm-6"><a href="addStudent" class="btn btn-success"><i class="material-icons">&#xE147;</i> <span>Create</span></a></div>
                        </div>
                    </div>
                    <table class="table table-striped table-hover">
                        <thead><tr><th style="width: auto">Title</th><th style="width: auto">Course</th><th style="width: min-content">Actions</th></tr></thead>
                        <tbody>
                        <c:forEach var="exam" items="${exams}">
                            <tr>
                                <td style="width: auto">${exam.title}</td>
                                <td style="width: auto">${exam.course.desctiption}</td>
                                <td style="width: min-content">
                                    <a href="deleteExam/${exam.id}" class="delete" ><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE254;</i></a>
                                    <a href="examDetails/${exam.id}" ><i class="material-icons" data-toggle="tooltip" title="Details">&#xE872;</i></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Question List -->
            <div id="question-container" class="container" hidden="true">
                <div class="table-wrapper">
                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-6"><h2>Manage Data</h2></div>
                            <div class="col-sm-6"><a href="#addQuestionModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Create</span></a></div>
                        </div>
                    </div>
                    <table class="table table-striped table-hover">
                        <thead><tr><th style="width: auto">Question Description</th><th style="width: auto">Question Type</th><th style="width: min-content">Actions</th></tr></thead>
                        <tbody>
                        <c:forEach var="question" items="${questions}">
                            <tr>
                                <td style="width: auto">${question.description}</td>
                                <td style="width: auto">${question.questionType}</td>
                                <td style="width: min-content"><a href="deleteQuestion/${question.id}" class="delete" ><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Add Question Modal HTML -->
            <div id="addQuestionModal" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <form:form method="post" id="question-form">
                            <div class="modal-header">
                                <h4 class="modal-title">Add Question</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>Description<textarea id="question-description" type="text" class="form-control" required="required"></textarea></label>
                                </div>
                                <div class="form-group">
                                    <label>Question Type</label><br>
                                    <input type="radio" name="question-type" value="${questionTypes[0]}"> ${questionTypes[0]}<br>
                                    <input type="radio" name="question-type" value="${questionTypes[1]}"> ${questionTypes[1]}<br>
                                    <input type="radio" name="question-type" value="${questionTypes[2]}"> ${questionTypes[2]}<br>
                                </div>
                                <div class="form-group">
                                    <table id="answer-label">
                                        <tr>
                                            <label>Answers</label>
                                            <input type="button" onclick="addAnswer()" class="btn btn-default" data-dismiss="modal" value="Add">
                                        </tr>
                                    </table>
                                    <div id="yes/no" hidden="true">
                                        <label><input type="radio" name="question-boolean-value" value="Yes"> Yes<br></label>
                                        <label><input type="radio" name="question-boolean-value" value="No"> No<br></label>
                                    </div>
                                    <table id="single-choice" hidden="true">
                                        <thead><tr><th style="padding:0 0 0 15px;">Description</th><th style="padding:0 0 0 15px;">Is Correct</th></tr></thead>
                                        <tbody>
                                            <tr>
                                                <td style="padding:0 0 0 15px;" class="answer"><textarea type="text" class="form-control" required="required"></textarea></td>
                                                <td style="padding:0 0 0 15px;" class="answer-value"><input type="radio"></td>
                                            </tr>
                                            <tr>
                                                <td style="padding:0 0 0 15px;" class="answer"><textarea type="text" class="form-control" required="required"></textarea></td>
                                                <td style="padding:0 0 0 15px;" class="answer-value"><input type="radio"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <table id="multiple-choice" hidden="true">
                                        <thead><tr><th style="padding:0 0 0 15px;">Description</th><th style="padding:0 0 0 15px;">Is Correct</th></tr></thead>
                                        <tbody>
                                            <tr>
                                                <td style="padding:0 0 0 15px;" class="answer"><textarea type="text" class="form-control" required="required"></textarea></td>
                                                <td style="padding:0 0 0 15px;" class="answer-value"><input type="checkbox"></td>
                                            </tr>
                                            <tr>
                                                <td style="padding:0 0 0 15px;" class="answer"><textarea type="text" class="form-control" required="required"></textarea></td>
                                                <td style="padding:0 0 0 15px;" class="answer-value"><input type="checkbox"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                                <input onclick="addQuestion()" type="submit" class="btn btn-info" value="Save">
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<footer class="container-fluid">
    <p>eLearning</p>
</footer>

</body>
</html>
