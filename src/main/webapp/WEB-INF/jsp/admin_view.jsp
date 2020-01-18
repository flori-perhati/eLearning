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
            function studentClick() {
                document.getElementById("student").classList.add("active");
                document.getElementById("pedagogue").classList.remove("active");

                document.getElementById("main-header").textContent = "Students";

                document.getElementById("student-container").hidden = false;
                document.getElementById("pedagogue-container").hidden = true;
            }

            function pedagogueClick() {
                document.getElementById("student").classList.remove("active");
                document.getElementById("pedagogue").classList.add("active");

                document.getElementById("main-header").textContent = "Pedagogues";

                document.getElementById("student-container").hidden = true;
                document.getElementById("pedagogue-container").hidden = false;
            }
        </script>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row content">
                <div class="col-sm-3 sidenav">
                    <h4 id="username">Admin</h4>
                    <ul class="nav nav-pills nav-stacked">
                        <li class="active" onclick="studentClick()" id="student"><a>Students</a></li>
                        <li onclick="pedagogueClick()" id="pedagogue"><a>Pedagogues</a></li>
                    </ul><br>
                </div>

                <div class="col-sm-9">
                    <h4><small>RECENT DATA</small></h4><hr><h2 id="main-header">Students</h2>

                    <!-- Student List -->
                    <div id="student-container" class="container">
                        <div class="table-wrapper">
                            <div class="table-title">
                                <div class="row">
                                    <div class="col-sm-6"><h2>Manage Data</h2></div>
                                    <div class="col-sm-6"><a href="#addStudentModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add</span></a></div>
                                </div>
                            </div>
                            <table class="table table-striped table-hover">
                                <thead><tr><th>First Name</th><th>Last Name</th><th>Faculty</th><th>Register Date</th><th>Username</th><th>Password</th><th>Actions</th></tr></thead>
                                <tbody>
                                <c:forEach var="student" items="${students}">
                                    <tr>
                                        <td>${student.firstName}</td>
                                        <td>${student.lastName}</td>
                                        <td>${student.faculty.description}</td>
                                        <td>${student.registerDate}</td>
                                        <td>${student.user.username}</td>
                                        <td>${student.user.password}</td>
                                        <td>
                                            <a href="editStudent/${student.id}" class="edit" ><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                                            <a href="deleteStudent/${student.id}" class="delete" ><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Add Student Modal HTML -->
                    <div id="addStudentModal" class="modal fade">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form:form method="post" action="saveStudent" modelAttribute="myModel">
                                    <div class="modal-header">
                                        <h4 class="modal-title">Add Student</h4>
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <label>First Name</label>
                                            <form:input type="text" class="form-control" required="required" path="student.firstName"/>
                                        </div>
                                        <div class="form-group">
                                            <label>Last Name</label>
                                            <form:input type="text" class="form-control" required="required" path="student.lastName"/>
                                        </div>
                                        <div class="form-group">
                                            <label>Registration Date</label>
                                            <form:input type="date" class="form-control" required="required" path="student.registerDate"/>
                                        </div>
                                        <div class="form-group">
                                            <label>Faculty</label>
                                            <form:select class="form-control" required="required" path="student.faculty.description">
                                                <form:options items="${faculties}" />
                                            </form:select>
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

                    <!-- Pedagogue List -->
                    <div id="pedagogue-container" class="container" hidden="true">
                        <div class="table-wrapper">
                            <div class="table-title">
                                <div class="row">
                                    <div class="col-sm-6"><h2>Manage Data</h2></div>
                                    <div class="col-sm-6"><a href="#addPedagogueModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add</span></a></div>
                                </div>
                            </div>
                            <table class="table table-striped table-hover">
                                <thead><tr><th>First Name</th><th>Last Name</th><th>Faculty</th><th>Register Date</th><th>Username</th><th>Password</th><th>Actions</th></tr></thead><tbody>
                                <c:forEach var="pedagogue" items="${pedagogues}">
                                    <tr>
                                        <td>${pedagogue.firstName}</td>
                                        <td>${pedagogue.lastName}</td>
                                        <td>${pedagogue.faculty.description}</td>
                                        <td>${pedagogue.registerDate}</td>
                                        <td>${pedagogue.user.username}</td>
                                        <td>${pedagogue.user.password}</td>
                                        <td>
                                            <a href="editPedagogue/${pedagogue.id}" class="edit"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                                            <a href="deletePedagogue/${pedagogue.id}" class="delete"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Add Course Modal HTML -->
                    <div id="addPedagogueModal" class="modal fade">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form:form method="post" action="savePedagogue" modelAttribute="myModel">
                                    <div class="modal-header">
                                        <h4 class="modal-title">Add Pedagogue</h4>
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <label>First Name</label>
                                            <form:input type="text" class="form-control" required="required" path="pedagogue.firstName"/>
                                        </div>
                                        <div class="form-group">
                                            <label>Last Name</label>
                                            <form:input type="text" class="form-control" required="required" path="pedagogue.lastName"/>
                                        </div>
                                        <div class="form-group">
                                            <label>Registration Date</label>
                                            <form:input type="date" class="form-control" required="required" path="pedagogue.registerDate"/>
                                        </div>
                                        <div class="form-group">
                                            <label>Faculty</label>
                                            <form:select class="form-control" required="required" path="pedagogue.faculty.description">
                                                <form:options items="${faculties}" />
                                            </form:select>
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

                </div>
            </div>
        </div>

        <footer class="container-fluid">
            <p>eLearning</p>
        </footer>

    </body>
</html>
