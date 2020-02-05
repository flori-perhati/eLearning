package com.student.elearning.controller;

import com.google.gson.Gson;
import com.student.elearning.dao.*;
import com.student.elearning.entity.Exam;
import com.student.elearning.entity.Pedagogue;
import com.student.elearning.entity.Student;
import com.student.elearning.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class StudentController {

    @Autowired
    StudentCourseDao studentCourseDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    FacultyDao facultyDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    ExamDao examDao;
    @Autowired
    UserDao userDao;

//    @RequestMapping("/pedagogue")
//    public String pedagogue(Model model, HttpSession session) {
////        long userId = (long) session.getAttribute("user_id");
//        long userId = 2;
//        Pedagogue pedagogue = pedagogueDao.pedagogueByUserId(userId);
//
//        model.addAttribute("pedagogue", pedagogue);
//        model.addAttribute("courses", courseDao.getCoursesByPedagogueId(pedagogue.getId()));
//        model.addAttribute("exams", examDao.examByPedagogue(pedagogue.getId()));
//        model.addAttribute("students", new ArrayList<>());
//        model.addAttribute("questions", new ArrayList<>());
//        model.addAttribute("faculties", facultyDao.getFaculties());
//        return "pedagogue";
//    }

    /**
     * Shows the view when a student is logged in
     * @param model type Model used for adding parameters to the view
     * @return student_view
     */
    @RequestMapping("/student")
    public String viewData(@ModelAttribute("user") User user, Model model) {
        long userId = 3;
        Student student = studentDao.studentByUserId(userId);

        model.addAttribute("student", student);

        model.addAttribute("courses", studentCourseDao.getCoursesByStudentId(student.getId()));
        model.addAttribute("examResults", new ArrayList<>());
        return "student";
    }

    @ResponseBody
    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String savePedagogue(@RequestBody Student student, HttpSession session) {
        User user = student.getUser();
//        long userId = (long) session.getAttribute("user_id");
        long userId = 3;
        user.setId(userId);

        return (userDao.update(user) && studentDao.update(student)) ? new Gson().toJson(student) : new Gson().toJson("Something went wrong!");
    }

    @ResponseBody
    @RequestMapping(value = "/getExams", method = RequestMethod.GET, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getStudents(@RequestParam("courseId") long courseId) {
        List<Exam> exams = examDao.examByCourse(courseId);
        if (exams.isEmpty())
            return new Gson().toJson("This course doesn't have exams!");
        else
            return new Gson().toJson(exams);
    }

    /**
     * Shows the view for inserting new Student to db
     * @param model type Model used for adding parameters to the view
     * @return path to the specific view
     */
    @RequestMapping("/addStudent")
    public String show(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("faculties", facultyDao.getFaculties());
        return "add_student_view";
    }

    /**
     * Shows the view for updating the student selected
     * @param id used for getting Student details
     * @param model type Model used for adding parameters to the view
     * @return path to the specific view
     */
    @RequestMapping(value="/editStudent/{id}")
    public String editStudent(@PathVariable int id, Model model){
//        model.addAttribute("student", studentDao.studentDetailsToEdit(id));
        return "edit_student_view";
    }

    /**
     * Updates the modified student
     * @param student type Student filled with new data
     * @return to admin_view to see the changes that have been made
     */
    @RequestMapping(value="/editStudent/updateStudent", method = RequestMethod.POST)
    public String updateStudent(@ModelAttribute("student") Student student){
        studentDao.update(student);
        userDao.update(student.getUser());
        return "redirect:/admin_view";
    }
}
