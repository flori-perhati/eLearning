$(document).ready(function () {
    let pedagogueData = $('#pedagogue-table');
    let studentData = $('#student-table');
    let pedagogueForm = $('#submit-pedagogue');
    let studentForm = $('#submit-student');
    let header = $('#window-header');
    let warning = $('#no-data');

    pedagogueData.hide();
    studentData.hide();
    pedagogueForm.hide();
    studentForm.hide();

    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
        $(this).toggleClass('active');
    });

    $('#allPedagogues').click(function () {
        header.html('All Pedagogues');

        pedagogueData.show();
        studentData.hide();
        pedagogueForm.hide();
        studentForm.hide();

        let pedagogueList = "${pedagogues}";
        let p = pedagogueList[0];
        if (p === null) {
            warning.html("There are no pedagogues!");
        }
    });

    $('#addPedagogue').click(function () {
        header.html('Add Pedagogue');

        pedagogueData.hide();
        studentData.hide();
        pedagogueForm.show();
        studentForm.hide();

        warning.html("");
    });

    $('#allStudents').click(function () {
        header.html('All Students');

        pedagogueData.hide();
        studentData.show();
        pedagogueForm.hide();
        studentForm.hide();

        let studentList = "${students}";
        let s = studentList[0];
        if (s === null) {
            warning.html("There are no students!");
        }
    });

    $('#addStudent').click(function () {
        header.html('Add Student');

        pedagogueData.hide();
        studentData.hide();
        pedagogueForm.hide();
        studentForm.show();

        warning.html("");
    });

    pedagogueForm.submit(function(event) {
        event.preventDefault();
        let pedagogue = {};
        pedagogue['facultyId'] = $('#pedagogue-faculty').val();
        pedagogue['firstName'] = $('#pedagogue-first-name').val();
        pedagogue['lastName'] = $('#pedagogue-last-name').val();
        pedagogue['gender'] = $('#pedagogue-gender').val();
        pedagogue['birthdate'] = $('#pedagogue-birthdate').val();
        pedagogue['registrationDate'] = $('#pedagogue-registration-date').val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/savePedagogue",
            data: JSON.stringify(pedagogue),
            dataType: 'json'
        }).done(function(response){
            if (response === "Something went wrong!")
                alert(response);
            else {
                pedagogueForm.trigger("reset");
                $('#pedagogue-table tr:last').after(insertPedagogueRow(response));
                alert("Success!");
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    studentForm.submit(function(event) {
        event.preventDefault();
        let student = {};
        student['facultyId'] = $('#student-faculty').val();
        student['firstName'] = $('#student-first-name').val();
        student['lastName'] = $('#student-last-name').val();
        student['gender'] = $('#student-gender').val();
        student['birthdate'] = $('#student-birthdate').val();
        student['registrationDate'] = $('#student-registration-date').val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/saveStudent",
            data: JSON.stringify(student),
            dataType: 'json'
        }).done(function(response){
            if (response === "Something went wrong!")
                alert(response);
            else {
                studentForm.trigger("reset");
                $('#student-table tr:last').after(insertStudentRow(response));
                alert("Success!");
            }
        }).fail(function(e) {
            console.log(e);
        });
    });

    function insertPedagogueRow(pedagogue) {
        return '<tr>' +
                    '<td>' + pedagogue.firstName +'</td>' +
                    '<td>' + pedagogue.lastName + '</td>' +
                    '<td>' + pedagogue.faculty.description + '</td>' +
                    '<td>' + pedagogue.registrationDate + '</td>' +
                    '<td>' + pedagogue.user.username + '</td>' +
                    '<td>' + pedagogue.user.password + '</td>' +
                    '<td>' +
                        '<a href="deletePedagogue/"' + pedagogue.id + '" ><i class="fa fa-trash" style="font-size:20px;color:red;margin-left: 20px;"></i></a>'   +
                    '</td>' +
                    '</tr>';
    }

    function insertStudentRow(student) {
        return '<tr>' +
            '<td>' + student.firstName +'</td>' +
            '<td>' + student.lastName + '</td>' +
            '<td>' + student.faculty.description + '</td>' +
            '<td>' + student.registerDate + '</td>' +
            '<td>' + student.user.username + '</td>' +
            '<td>' + student.user.password + '</td>' +
            '<td>' +
                '<a href="deletePedagogue/"' + student.id + '" ><i class="fa fa-trash" style="font-size:20px;color:red;margin-left: 20px;"></i></a>'   +
            '</td>' +
            '</tr>';
    }
});

// function deletePedagogue(id) {
//     $.ajax({
//         type: "POST",
//         contentType: "application/json",
//         url: "/deletePedagogue",
//         data: JSON.stringify(id),
//         dataType: 'json'
//     }).done(function(response){
//         if (response === "error")
//             alert(response);
//         else {
//             $('#student-table tr:last').after(insertStudentRow(response));
//         }
//     }).fail(function(e) {
//         console.log(e);
//     });
// }
//
// function deleteStudent(id) {
//     $.ajax({
//         type: "POST",
//         contentType: "application/json",
//         url: "/saveStudent",
//         data: JSON.stringify(student),
//         dataType: 'json'
//     }).done(function(response){
//         if (response === "Something went wrong!")
//             alert(response);
//         else {
//             studentForm.trigger("reset");
//             $('#student-table tr:last').after(insertStudentRow(response));
//         }
//     }).fail(function(e) {
//         console.log(e);
//     });
// }