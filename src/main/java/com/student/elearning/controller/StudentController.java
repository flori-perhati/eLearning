package com.student.elearning.controller;

import com.student.elearning.dao.CourseDao;
import com.student.elearning.dao.FacultyDao;
import com.student.elearning.dao.StudentDao;
import com.student.elearning.dao.UserDao;
import com.student.elearning.model.Student;
import com.student.elearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
@RequestMapping("")
public class StudentController {

    @Autowired
    StudentDao studentDao;
    @Autowired
    FacultyDao facultyDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    UserDao userDao;

    /**
     * Shows the view when a student is logged in
     * @param model type Model used for adding parameters to the view
     * @return student_view
     */
    @RequestMapping("/student_view")
    public String viewData(@ModelAttribute("user") User user, Model model) {
        Student student = studentDao.studentByUserId(user.getId());

        model.addAttribute("student", student);

        model.addAttribute("courses", new ArrayList<>());
        model.addAttribute("examResults", new ArrayList<>());
        return "student_view";
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
        model.addAttribute("student", studentDao.studentDetailsToEdit(id));
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
