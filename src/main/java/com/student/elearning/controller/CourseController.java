package com.student.elearning.controller;

import com.google.gson.Gson;
import com.student.elearning.dao.CourseDao;
import com.student.elearning.dao.StudentCourseDao;
import com.student.elearning.dao.StudentDao;
import com.student.elearning.entity.Course;
import com.student.elearning.entity.Student;
import com.student.elearning.entity.StudentCourse;
import com.student.elearning.network.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CourseController {

    /** Implementation of CRUD operation and other queries */
    @Autowired CourseDao courseDao;
    @Autowired StudentDao studentDao;
    @Autowired StudentCourseDao studentCourseDao;

    /**
     * Insert new Course
     * @param course represents new course to be inserted
     * @return json response
     */
    @ResponseBody
    @RequestMapping(value = "/course/insert", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String insert(@RequestBody Course course) {
        Response<Course> response = new Response<>();
        if (courseDao.insert(course)) {
            response.setResponseCode(201);
            response.setResponseMessage("Created");
            response.setT(courseDao.lastCourse());
            return new Gson().toJson(response);
        }

        response.setResponseCode(500);
        response.setResponseMessage("Internal Server Error");
        response.setT(null);
        return new Gson().toJson(response);
    }

    /**
     * Retrieve students that are not registered to selected course
     * @param courseId gives course id selected to register students
     * @param facultyId gives faculty id of logged in pedagogue
     * @return json response
     */
    @ResponseBody
    @RequestMapping(value = "/course/students", method = RequestMethod.GET, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String courseStudents(@RequestParam("courseId") long courseId, @RequestParam("facultyId") long facultyId) {
        Response<List<Student>> response = new Response<>();

        List<Student> students = studentDao.getStudentsForCourse(facultyId, courseId, studentCourseDao);
        if (!students.isEmpty()) {
            response.setResponseCode(200);
            response.setResponseMessage("OK");
            response.setT(students);
            return new Gson().toJson(response);
        }

        response.setResponseCode(204);
        response.setResponseMessage("No Content");
        response.setT(null);
        return new Gson().toJson(response);
    }

    /**
     * Register selected students to selected course
     * @param studentCourses represent students which will register to selected course
     * @return json response
     */
    @ResponseBody
    @RequestMapping(value = "/course/students/add", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addStudentsToCourse(@RequestBody List<StudentCourse> studentCourses) {
        Response<String> response = new Response<>();

        for (StudentCourse studentCourse : studentCourses) {
            studentCourseDao.insert(studentCourse);
        }

        response.setResponseCode(200);
        response.setResponseMessage("OK");
        response.setT("");
        return new Gson().toJson(response);
    }
}
