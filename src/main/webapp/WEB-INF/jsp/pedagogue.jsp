<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>eLearning - Pedagogue</title>

    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- Our Custom CSS -->
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/style5.css"/>">
    <%--        <link rel="stylesheet" type="text/css" href="<c:url value="/css/form.css"/>">--%>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/form.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/radio.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/checkbox.css"/>">

    <!-- Font Awesome JS -->
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>
</head>

<body>

<div class="wrapper">
    <!-- Sidebar Holder -->
    <nav id="sidebar">
        <div class="sidebar-header"><h3>eLearning</h3></div>

        <ul class="list-unstyled components">
            <p>Menu</p>
            <li><a id="allCourses">Course</a></li>
            <li>
                <a href="#examSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Exam</a>
                <ul class="collapse list-unstyled" id="examSubmenu">
                    <li><a id="allExams">All Exams</a></li>
                    <li><a id="addExam" data-value="${pedagogue.id}">Add Exam</a></li>
                </ul>
            </li>
            <li>
                <a href="#questionSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Question</a>
                <ul class="collapse list-unstyled" id="questionSubmenu">
                    <li><a id="allQuestions">All Questions</a></li>
                    <li><a id="addQuestion">Add Question</a></li>
                </ul>
            </li>
        </ul>
    </nav>

    <!-- Page Content Holder -->
    <div id="content">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">

                <button type="button" id="sidebarCollapse" class="navbar-btn">
                    <span></span>
                    <span></span>
                    <span></span>
                </button>
                <button class="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fas fa-align-justify"></i>
                </button>
                <div id="window-header" style="padding-left: 20px"></div>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="nav navbar-nav ml-auto">
                        <li class="nav-item active"><a class="nav-link" id="profile">Profile</a></li>
                        <li class="nav-item"><a class="nav-link" href="/accounts/sign_out" id="logout">Logout</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <div id="no-data" style="color: orangered"></div>

        <form id="pedagogue-profile" class="login-block" method="post">
            <div><h1 style="display: inline">Profile Data</h1> <a id="enable-profile-edit"><i class="fa fa-edit" style="display: inline;font-size:20px;color:#7386D5;float: right"></i></a></div>
            <table style="width: 100%; margin-top: 10px;">
                <tr>
                    <td>First Name</td>
                    <td style="padding-left: 10px">Last Name</td>
                </tr>
                <tr>
                    <td style="padding-right: 10px"><label for="pedagogue-first-name"></label><input value="${pedagogue.firstName}" id="pedagogue-first-name" type="text" required/></td>
                    <td style="padding-left: 10px"><label for="pedagogue-last-name"></label><input value="${pedagogue.lastName}" id="pedagogue-last-name" type="text" required/></td>
                </tr>
                <tr>
                    <td>Gender</td>
                    <td style="padding-left: 10px">Birthdate</td>
                </tr>
                <tr>
                    <td style="padding-right: 10px"><label for="pedagogue-gender"></label><input value="${pedagogue.gender}" id="pedagogue-gender" type="text" required/></td>
                    <td style="padding-left: 10px"><label for="pedagogue-birthdate"></label><input value="${pedagogue.birthdate}" id="pedagogue-birthdate" type="text" onfocus="(this.type='date')" required/></td>
                </tr>
                <tr>
                    <td>Username</td>
                    <td style="padding-left: 10px">Password</td>
                </tr>
                <tr>
                    <td style="padding-right: 10px"><label for="pedagogue-username"></label><input value="${pedagogue.user.username}" id="pedagogue-username" type="text" required/></td>
                    <td style="padding-left: 10px"><label for="pedagogue-password"></label><input value="${pedagogue.user.password}" id="pedagogue-password" type="text" required/></td>
                </tr>
            </table>
            <button id="submit-pedagogue" type="submit" value="${pedagogue.id}" userId="${pedagogue.userId}" style="float: right; width: 30%">Save Changes</button>
        </form>

        <form id="course-form" class="login-block" method="post" style="width: 100%;margin-bottom: 50px">
            <h1>Add Course</h1>
            <label for="course-description"></label><input id="course-description" type="text" placeholder="Description" required/>
            <button id="submit-course" type="submit" value="${pedagogue.id}" style="float: right; width: 20%">Submit</button>
        </form>

        <table id="course-table" class="table" style="box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);">
            <thead class="thead-light"><tr>
                <th scope="col">Course Description</th><th scope="col">Add Students</th>
            </tr></thead>
            <tbody>
            <c:forEach var="course" items="${courses}">
                <tr>
                    <td style="width: 85%">${course.description}</td>
                    <td>
                        <a courseId="${course.id}" description="${course.description}" facultyId="${pedagogue.facultyId}" class="add-student-to-course"><i class="fa fa-plus" style="font-size:20px;color:lightblue;text-shadow:2px 2px 4px #000000;"></i></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <form id="add-students" class="login-block" method="post" style="width: 50%;margin-top: 50px;float: left;">
            <div><h1 style="display: inline">Add Students</h1> <a id="hide-student-form" style="display: inline; color: red; float: right"><i><u>Close</u></i></a></div>
            <div id="selected-course" style="margin-top:20px; margin-bottom: 20px"></div>

            <table class="table" id="students-table" style="box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);">
                <thead class="thead-light"><tr>
                    <th scope="col" style="width: 10%">Add</th><th scope="col" style="width: 90%">Student</th>
                </tr></thead>
                <tbody>
                </tbody>
            </table>

            <button id="submit-students" type="submit" style="float: right; width: 20%">Submit</button>
        </form>

        <table id="exam-table" class="table" style="box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);">
            <thead class="thead-light"><tr>
                <th scope="col">Course</th><th scope="col">Exam Header</th><th scope="col">Description</th><th scope="col" style="width: 15%">Actions</th>
            </tr></thead>
            <tbody>
            <c:forEach var="exam" items="${exams}">
                <tr>
                    <td>${exam.course.description}</td>
                    <td>${exam.header}</td>
                    <td>${exam.description}</td>
                    <td>
                        <a examId="${exam.id}" class="exam-results" style="color: #6d7fcc"><i><u>Results</u></i></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <form id="exam-form" class="login-block" method="post" style="width: 75%;margin-bottom: 60px">
            <h1>Create Exam</h1>
            <div style="margin-bottom: 10px">Exam Header</div>
            <label for="exam-header"></label><textarea id="exam-header" type="text" required></textarea>
            <div style="margin-bottom: 10px">Exam Description</div>
            <label for="exam-description"></label><textarea id="exam-description" type="text" required></textarea>
            <div style="margin-bottom: 10px">Course</div>
            <label for="course"></label><select id="course" required>
            </select>

            <table class="table" id="questions-table" style="box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);margin-top: 40px">
                <thead class="thead-light"><tr>
                    <th scope="col" style="width: 10%">Actions</th><th scope="col" style="width: 20%">Question Type</th><th scope="col" style="width: 60%">Description</th><th scope="col" style="width: 10%; text-align: end">Points</th>
                </tr></thead>
                <tbody>
                </tbody>
            </table>

            <button value="${pedagogue.id}" id="submit-exam" type="submit" style="float: right; width: 20%">Submit</button>
        </form>

        <table id="question-table" class="table" style="box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);">
            <thead class="thead-light"><tr>
                <th scope="col" style="width: 20%">Type</th><th scope="col">Description</th>
            </tr></thead>
            <tbody>
            <c:forEach var="question" items="${questions}">
                <tr>
                    <td>${question.questionType}</td>
                    <td>${question.value}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <form id="question-form" class="login-block" method="post" style="width: 75%;margin-bottom: 60px">
            <h1>Create Question</h1>
            <div style="margin-bottom: 10px">Question Description</div>
            <label for="question-description"></label><textarea id="question-description" type="text" required></textarea>
            <div style="margin-bottom: 10px">Question Type</div>
            <input type="radio" id="radio1" name="question-type" value="Yes/No"><label for="radio1"><b>Yes/No</b></label>
            <input type="radio" id="radio2" name="question-type" value="Single Choice"><label for="radio2"><b>Single Choice</b></label>
            <input type="radio" id="radio3" name="question-type" value="Multiple Choice"><label for="radio3"><b>Multiple Choice</b></label>

            <div id="answer-label" style="margin-top: 20px; margin-bottom: 10px;">Answers</div>

            <div id="yes-no">
                <input type="radio" id="r-1" name="question-boolean-value" value="Yes"><label for="r-1"><b>Yes</b></label>
                <input type="radio" id="r-2" name="question-boolean-value" value="No"><label for="r-2"><b>No</b></label>
            </div>

            <table id="single-choice-table" style="width: 100%; margin-top: 10px;">
                <thead><tr>
                    <th style="padding-bottom: 10px; width: 10%; font-size: 14px; color: #6d7fcc; font-family: Montserrat, serif;">Is Correct</th>
                    <th style="padding-bottom: 10px; font-size: 14px; color: #6d7fcc; font-family: Montserrat, serif;">Description</th></tr></thead>
                <tr>
                    <td class="answer-choice" style="width: 10%; display: block; "><input type="radio" id="r1" name="single-choice"><label for="r1"></label></td>
                    <td class="answer-value"><label for="ra1"></label><textarea id="ra1" class="single-choice-area" type="text" required></textarea></td>
                </tr>
                <tr>
                    <td class="answer-choice" style="width: 10%; display: block; "><input type="radio" id="r2" name="single-choice"><label for="r2"></label></td>
                    <td class="answer-value"><label for="ra2"></label><textarea id="ra2" class="single-choice-area" type="text" required></textarea></td>
                </tr>
                <tr>
                    <td class="answer-choice" style="width: 10%; display: block; "><input type="radio" id="r3" name="single-choice"><label for="r3"></label></td>
                    <td class="answer-value"><label for="ra3"></label><textarea id="ra3" class="single-choice-area" type="text" required></textarea></td>
                </tr>
                <tr>
                    <td class="answer-choice" style="width: 10%; display: block; "><input type="radio" id="r4" name="single-choice"><label for="r4"></label></td>
                    <td class="answer-value"><label for="ra4"></label><textarea id="ra4" class="single-choice-area" type="text" required></textarea></td>
                </tr>
            </table>

            <table id="multiple-choice-table" style="width: 100%; margin-top: 10px;">
                <thead><tr>
                    <th style="padding-bottom: 10px; width: 10%; font-size: 14px; color: #6d7fcc; font-family: Montserrat, serif;">Is Correct</th>
                    <th style="padding-bottom: 10px; font-size: 14px; color: #6d7fcc; font-family: Montserrat, serif;">Description</th></tr></thead>
                <tr>
                    <td class="answer-choice" style="width: 10%; display: block; "><input type="checkbox" id="ch1"><label for="ch1"></label></td>
                    <td class="answer-value"><label for="cha1"></label><textarea id="cha1" class="multiple-choice-area" type="text" required></textarea></td>
                </tr>
                <tr>
                    <td class="answer-choice" style="width: 10%; display: block; "><input type="checkbox" id="ch2"><label for="ch2"></label></td>
                    <td class="answer-value"><label for="cha2"></label><textarea id="cha2" class="multiple-choice-area" type="text" required></textarea></td>
                </tr>
                <tr>
                    <td class="answer-choice" style="width: 10%; display: block; "><input type="checkbox" id="ch3"><label for="ch3"></label></td>
                    <td class="answer-value"><label for="cha3"></label><textarea id="cha3" class="multiple-choice-area" type="text" required></textarea></td>
                </tr>
                <tr>
                    <td class="answer-choice" style="width: 10%; display: block; "><input type="checkbox" id="ch4"><label for="ch4"></label></td>
                    <td class="answer-value"><label for="cha4"></label><textarea id="cha4" class="multiple-choice-area" type="text" required></textarea></td>
                </tr>
            </table>

            <button id="submit-question" type="submit" value="${pedagogue.id}" style="float: right; width: 20%">Submit</button>
        </form>
    </div>
</div>

<!-- jQuery CDN - Slim version (=without AJAX) -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<!-- Popper.JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<!-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="<c:url value="/js/pedagogue.js"/>"></script>
</body>
</html>