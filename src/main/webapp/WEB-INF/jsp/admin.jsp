<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <title>eLearning - Admin</title>

        <!-- Bootstrap CSS CDN -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
        <!-- Our Custom CSS -->
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/style5.css"/>">
<%--        <link rel="stylesheet" type="text/css" href="<c:url value="/css/form.css"/>">--%>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/form.css"/>">

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
                    <li>
                        <a href="#pedagogueSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Pedagogues</a>
                        <ul class="collapse list-unstyled" id="pedagogueSubmenu">
                            <li><a id="allPedagogues">All Pedagogues</a></li>
                            <li><a id="addPedagogue">Add Pedagogue</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="#studentSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Students</a>
                        <ul class="collapse list-unstyled" id="studentSubmenu">
                            <li><a id="allStudents">All Students</a></li>
                            <li><a id="addStudent">Add Student</a></li>
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
                                <li class="nav-item"><a class="nav-link" href="/accounts/sign_out" id="logout">Logout</a></li>
                            </ul>
                        </div>
                    </div>
                </nav>

                <div id="no-data" style="color: orangered"></div>

                <table class="table" id="pedagogue-table" style="box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);">
                    <thead class="thead-light"><tr>
                        <th scope="col">First Name</th><th scope="col">Last Name</th><th scope="col">Faculty</th><th scope="col">Registration Date</th><th scope="col">Username</th><th scope="col">Password</th><th scope="col">Actions</th>
                    </tr></thead>
                    <tbody>
                    <c:forEach var="pedagogue" items="${pedagogues}">
                        <tr>
                            <td>${pedagogue.firstName}</td>
                            <td>${pedagogue.lastName}</td>
                            <td>${pedagogue.faculty.description}</td>
                            <td>${pedagogue.registrationDate}</td>
                            <td>${pedagogue.user.username}</td>
                            <td>${pedagogue.user.password}</td>
                            <td>
                                <a href='/pedagogue/delete/${pedagogue.id}' ><i class="fa fa-trash" style="font-size:20px;color:red;margin-left: 20px;"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <table class="table" id="student-table" style="box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);">
                    <thead class="thead-light"><tr>
                        <th scope="col">First Name</th><th scope="col">Last Name</th><th scope="col">Faculty</th><th scope="col">Registration Date</th><th scope="col">Username</th><th scope="col">Password</th><th scope="col">Actions</th>
                    </tr></thead>
                    <tbody>
                    <c:forEach var="student" items="${students}">
                        <tr>
                            <td>${student.firstName}</td>
                            <td>${student.lastName}</td>
                            <td>${student.faculty.description}</td>
                            <td>${student.registrationDate}</td>
                            <td>${student.user.username}</td>
                            <td>${student.user.password}</td>
                            <td>
                                <a href='/student/delete/${student.id}' ><i class="fa fa-trash" style="font-size:20px;color:red;margin-left: 20px;"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <form id="submit-pedagogue" class="login-block" method="post">
                    <h1>Add Pedagogue</h1>
                    <table style="width: 100%; margin-top: 10px;">
                        <tr>
                            <td style="width: 50%">First Name</td>
                            <td style="padding-left: 10px; width: 50%">Last Name</td>
                        </tr>
                        <tr>
                            <td style="padding-right: 10px"><label for="pedagogue-first-name"></label><input id="pedagogue-first-name" type="text" required/></td>
                            <td style="padding-left: 10px"><label for="pedagogue-last-name"></label><input id="pedagogue-last-name" type="text" required/></td>
                        </tr>
                        <tr>
                            <td>Gender</td>
                            <td style="padding-left: 10px">Birthdate</td>
                        </tr>
                        <tr>
                            <td style="padding-right: 10px;">
                                <label for="pedagogue-gender"></label><select id="pedagogue-gender" required>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                </select>
                            </td>
                            <td style="padding-left: 10px"><label for="pedagogue-birthdate"></label><input id="pedagogue-birthdate" type="text" onfocus="(this.type='date')" required/></td>
                        </tr>
                        <tr>
                            <td>Faculty</td>
                            <td style="padding-left: 10px">Registration Date</td>
                        </tr>
                        <tr>
                            <td style="padding-right: 10px">
                                <label for="pedagogue-faculty"></label><select id="pedagogue-faculty" required>
                                <c:forEach var="faculty" items="${faculties}">
                                    <option value="${faculty.id}">${faculty.description}</option>
                                </c:forEach>
                            </select>
                            </td>
                            <td style="padding-left: 10px"><label for="pedagogue-registration-date"></label><input id="pedagogue-registration-date" type="text" onfocus="(this.type='date')" required/></td>
                        </tr>
                    </table>
                    <button type="submit" style="float: right; width: 30%">Submit</button>
                </form>

                <form id="submit-student" class="login-block" method="post" action="#">
                    <h1>Add Student</h1>
                    <table style="width: 100%; margin-top: 10px;">
                        <tr>
                            <td style="width: 50%;">First Name</td>
                            <td style="padding-left: 10px; width: 50%">Last Name</td>
                        </tr>
                        <tr>
                            <td style="padding-right: 10px"><label for="student-first-name"></label><input id="student-first-name" type="text" required/></td>
                            <td style="padding-left: 10px"><label for="student-last-name"></label><input id="student-last-name" type="text" required/></td>
                        </tr>
                        <tr>
                            <td>Gender</td>
                            <td style="padding-left: 10px">Birthdate</td>
                        </tr>
                        <tr>
                            <td style="padding-right: 10px;">
                                <label for="student-gender"></label><select id="student-gender" required>
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                            </select>
                            </td>
                            <td style="padding-left: 10px"><label for="student-birthdate"></label><input id="student-birthdate" type="text" onfocus="(this.type='date')" required/></td>
                        </tr>
                        <tr>
                            <td>Faculty</td>
                            <td style="padding-left: 10px">Registration Date</td>
                        </tr>
                        <tr>
                            <td style="padding-right: 10px">
                                <label for="student-faculty"></label><select id="student-faculty" required>
                                <c:forEach var="faculty" items="${faculties}">
                                    <option value="${faculty.id}">${faculty.description}</option>
                                </c:forEach>
                            </select>
                            </td>
                            <td style="padding-left: 10px"><label for="student-registration-date"></label><input id="student-registration-date" type="text" onfocus="(this.type='date')" required/></td>
                        </tr>
                    </table>
                    <button type="submit" style="float: right; width: 30%">Submit</button>
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
        <script type="text/javascript" src="<c:url value="/js/admin.js"/>"></script>
    </body>
</html>