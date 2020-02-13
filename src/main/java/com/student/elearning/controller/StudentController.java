package com.student.elearning.controller;

import com.google.gson.Gson;
import com.student.elearning.dao.ExamTakenDao;
import com.student.elearning.dao.StudentCourseDao;
import com.student.elearning.dao.StudentDao;
import com.student.elearning.dao.UserDao;
import com.student.elearning.entity.Student;
import com.student.elearning.entity.User;
import com.student.elearning.network.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("")
public class StudentController {

    /** Backend implementation of CRUD operation and other queries */
    @Autowired UserDao userDao;
    @Autowired StudentDao studentDao;
    @Autowired ExamTakenDao examTakenDao;
    @Autowired StudentCourseDao studentCourseDao;

    /**
     * Shows the view when a student is logged in
     * @param user [logged in] redirected from SignInController
     * @param model type Model used for adding parameters to the view
     * @return student_view or redirect to sign_in view is user isn't remembered
     */
    @RequestMapping("/student")
    public String viewData(@ModelAttribute("student_user") User user, Model model) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session =  attr.getRequest().getSession(true);
        if (session.getAttribute("user_id") != null && session.getAttribute("user_status") != null && session.getAttribute("user_status").toString().equals("student")) {
            long userId = Long.parseLong(session.getAttribute("user_id").toString());
            Student student = studentDao.studentByUserId(userId);

            model.addAttribute("student", student);
            model.addAttribute("courses", studentCourseDao.getCoursesByStudentId(student.getId()));
            model.addAttribute("examTakenLists", examTakenDao.examsTakenByStudent(student.getId()));
            return "student";
        } else
            return "redirect:/accounts/sign_in";
    }

    /**
     * Insert new Student
     * @param student represents new student to be inserted
     * @return json response
     */
    @ResponseBody
    @RequestMapping(value = "/student/insert", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String insert(@RequestBody Student student) {
        Response<Student> response = new Response<>();

        User user = new User();
        user.setUsername(student.getFirstName().substring(0, 1) + "" + student.getLastName());
        user.setPassword(student.getFirstName().substring(0, 1) + "" + student.getLastName());
        user.setUserStatus("student");

        if (userDao.insert(user)) {
            student.setFacultyId(student.getFacultyId());
            student.setUserId(userDao.lastUser().getId());
            student.setStatus(true);

            if (studentDao.insert(student)) {
                response.setResponseCode(201);
                response.setResponseMessage("Created");
                response.setT(studentDao.getLastStudent());
                return new Gson().toJson(response);
            }
        }

        response.setResponseCode(500);
        response.setResponseMessage("Internal Server Error");
        response.setT(null);
        return new Gson().toJson(response);
    }

    /**
     * Updates the logged in student
     * @param student filled with new field values
     * @return json response
     */
    @ResponseBody
    @RequestMapping(value = "/student/update", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String update(@RequestBody Student student) {
        Response<Student> response = new Response<>();

        if (userDao.update(student.getUser()) && studentDao.update(student)) {
            response.setResponseCode(200);
            response.setResponseMessage("OK");
            response.setT(student);
            return new Gson().toJson(response);
        }

        response.setResponseCode(500);
        response.setResponseMessage("Internal Server Error");
        response.setT(null);
        return new Gson().toJson(response);
    }

    /**
     * Delete the selected Student
     * @param id gives student id selected to delete
     * @return to admin view
     */
    @RequestMapping(value="/student/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id) {
        studentDao.updateStatus(0, id);
        return "redirect:/admin";
    }
}
