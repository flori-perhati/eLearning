<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <style>
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
        .table-title .btn {
            color: #fff;
            float: right;
            font-size: 13px;
            border: none;
            min-width: 50px;
            border-radius: 2px;
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
        table.table tr th:first-child {
            width: 100px;
        }
        table.table tr th:last-child {
            width: 100px;
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
            document.getElementById("result").classList.remove("active");

            document.getElementById("main-header").textContent = "Profile";

            document.getElementById("profile-container").hidden = false;
            document.getElementById("course-container").hidden = true;
            document.getElementById("result-container").hidden = true;
        }
        function courseClick() {
            document.getElementById("profile").classList.remove("active");
            document.getElementById("course").classList.add("active");
            document.getElementById("result").classList.remove("active");

            document.getElementById("main-header").textContent = "Courses";

            document.getElementById("profile-container").hidden = true;
            document.getElementById("course-container").hidden = false;
            document.getElementById("result-container").hidden = true;
        }
        function resultClick() {
            document.getElementById("profile").classList.remove("active");
            document.getElementById("course").classList.remove("active");
            document.getElementById("result").classList.add("active");

            document.getElementById("main-header").textContent = "Results";

            document.getElementById("profile-container").hidden = true;
            document.getElementById("course-container").hidden = true;
            document.getElementById("result-container").hidden = false;
        }
    </script>
</head>

<body>
<div class="container-fluid">
    <div class="row content">
        <div class="col-sm-3 sidenav">
            <h4 id="username">Admin</h4>
            <ul class="nav nav-pills nav-stacked">
                <li class="active" onclick="profileClick()" id="profile"><a>Profile</a></li>
                <li onclick="courseClick()" id="course"><a>Courses</a></li>
                <li onclick="resultClick()" id="result"><a>Results</a></li>
            </ul><br>
        </div>

        <div class="col-sm-9">
            <h4><small>RECENT DATA</small></h4><hr><h2 id="main-header">Students</h2><br>

            <div id="profile-container">
                <form:form action="editStudent/${student.id}" method="post" modelAttribute="student">
                    <table>
                        <tbody>
                        <tr><td><label>First Name</label><div class="form-group"><form:input type="text" disabled="true" class="form-control" path="firstName"/></div></td>
                            <td style="margin-right: 50px"><label>Last Name</label><div class="form-group"><form:input type="text" disabled="true" class="form-control" path="lastName"/></div></td></tr>
                        <tr><td><label>Faculty Description</label><div class="form-group"><form:input type="text" disabled="true" class="form-control" path="faculty.description"/></div></td>
                            <td style="margin-right: 50px"><label>Registration Date</label><div class="form-group"><form:input type="text" disabled="true" class="form-control" path="registerDate"/></div></td></tr>
                        <tr><td><label>Username</label><div class="form-group"><form:input type="text" disabled="true" class="form-control" path="user.username"/></div></td>
                            <td style="margin-right: 50px"><label>Password</label><div class="form-group"><form:input type="text" disabled="true" class="form-control" path="user.password"/></div></td></tr>
                        <tr><td><div class="form-group"><button type="submit" class="btn btn-primary btn-block" value="Edit">Edit</button></div></td><td></td><td></td></tr>
                        </tbody>
                    </table>
                </form:form>
            </div>

            <!-- Course List -->
            <div id="course-container" class="container" hidden="true">
                <div class="table-wrapper">
                    <div class="table-title">
                        <div class="row"><div class="col-sm-6"><h2>Manage Data</h2></div></div>
                    </div>
                    <table class="table table-striped table-hover">
                        <thead><tr><th style="width: auto">Course Description</th><th style="width: auto">Pedagogue</th><th style="width: auto">Actions</th></tr></thead>
                        <tbody>
                        <c:forEach var="course" items="${courses}">
                            <tr>
                                <td style="width: auto">${course.description}</td>
                                <td style="width: auto">${course.pedagogue.fullName}</td>
                                <td style="width: auto">
                                    <a href="exams/${course.id}" class="btn btn-success"><i class="material-icons">&#xE147;</i></a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Pedagogue List -->
            <div id="result-container" class="container" hidden="true">
                <div class="table-wrapper">
                    <div class="table-title">
                        <div class="row"><div class="col-sm-6"><h2>Manage Data</h2></div></div>
                    </div>
                    <table class="table table-striped table-hover">
                        <thead><tr><th style="width: auto">Exam</th><th style="width: auto">Course</th><th style="width: auto">Pedagogue</th>
                            <th style="width: auto">Holding Date</th><th style="width: auto">Estimation</th></tr></thead><tbody>
                    <c:forEach var="examResult" items="${examResults}">
                        <tr>
                            <td style="width: auto">${examResult.title}</td>
                            <td style="width: auto">${examResult.course.description}</td>
                            <td style="width: auto">${examResult.pedagogue.fullName}</td>
                            <td style="width: auto">${examResult.holdingDate}</td>
                            <td style="width: auto">${examResult.estimation}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    </table>
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
