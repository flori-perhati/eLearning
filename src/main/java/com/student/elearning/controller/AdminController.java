package com.student.elearning.controller;

import com.google.gson.Gson;
import com.student.elearning.dao.FacultyDao;
import com.student.elearning.dao.PedagogueDao;
import com.student.elearning.dao.StudentDao;
import com.student.elearning.dao.UserDao;
import com.student.elearning.model.MyModel;
import com.student.elearning.model.Pedagogue;
import com.student.elearning.model.Student;
import com.student.elearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    PedagogueDao pedagogueDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    FacultyDao facultyDao;
    @Autowired
    UserDao userDao;

    /**
     * Shows the view when admin is logged in
     * @param model type Model used for adding parameters to the view
     * @return admin_view
     */
    @RequestMapping("/admin")
    public String viewData(Model model) {
        List<Student> students = studentDao.getStudents();
        List<Pedagogue> pedagogues = pedagogueDao.getPedagogues();
        List<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");
//        model.addAttribute("myModel", new MyModel());
        model.addAttribute("genders", genders);
        model.addAttribute("faculties", facultyDao.getFaculties());
        model.addAttribute("students", students);
        model.addAttribute("pedagogues", pedagogues);
        return "admin";
    }

//    /**
//     * Insert new Student to db
//     * @param myModel type MyModel filled with student data
//     * @return to admin_view to see the changes that have been made
//     */
//    @RequestMapping(value="/saveStudent", method = RequestMethod.POST)
//    public String insertStudent(@ModelAttribute("myModel") MyModel myModel) {
//        Student student = myModel.getStudent();
//        User user = new User();
//        user.setUsername(student.getFirstName() + "." + student.getLastName());
//        user.setPassword(student.getFirstName() + "" + student.getLastName());
//        user.setUserStatus("student");
//        userDao.insert(user);
//        student.setFacultyId(Integer.parseInt(student.getFaculty().getDescription().substring(0, 1)));
//        student.setUserId(userDao.lastUser().getId());
//        studentDao.insert(student);
//        return "redirect:/admin_view";
//    }

    /**
     * Delete the selected Student from db
     * @param id gives student id selected to delete
     * @return to admin_view to see the changes that have been made
     */
    @RequestMapping(value="/deleteStudent/{id}", method = RequestMethod.GET)
    public String deleteStudent(@PathVariable int id){
        userDao.delete(studentDao.getStudentById(id).getUserId());
        studentDao.delete(id);
        return "redirect:/admin_view";
    }

//    /**
//     * Insert new Pedagogue to db
//     * @param myModel type MyModel filled with pedagogue data
//     * @return to admin_view to see the changes that have been made
//     */
//    @RequestMapping(value = "/savePedagogue", method = RequestMethod.POST)
//    public String insertPedagogue(@ModelAttribute("myModel") MyModel myModel) {
//        Pedagogue pedagogue = myModel.getPedagogue();
//        User user = new User();
//        user.setUsername(pedagogue.getFirstName() + "." + pedagogue.getLastName());
//        user.setPassword(pedagogue.getFirstName() + "" + pedagogue.getLastName());
//        user.setUserStatus("pedagogue");
//        userDao.insert(user);
//        pedagogue.setFacultyId(Integer.parseInt(pedagogue.getFaculty().getDescription().substring(0, 1)));
//        pedagogue.setUserId(userDao.lastUser().getId());
//        pedagogueDao.insert(pedagogue);
//        return "redirect:/admin_view";
//    }

    @ResponseBody
    @RequestMapping(value = "/savePedagogue", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String savePedagogue(@RequestBody Pedagogue pedagogue) {
        String response = "success";
        User user = new User();
        user.setUsername(pedagogue.getFirstName() + "." + pedagogue.getLastName());
        user.setPassword(pedagogue.getFirstName() + "" + pedagogue.getLastName());
        user.setUserStatus("pedagogue");
        userDao.insert(user);
        pedagogue.setFacultyId(1);
        pedagogue.setUserId(userDao.lastUser().getId());
        pedagogueDao.insert(pedagogue);
        return new Gson().toJson(pedagogueDao.getLastPedagogue());
    }

    @ResponseBody
    @RequestMapping(value = "/saveStudent", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveStudent(@RequestBody Student student) {
        String response = "success";
        User user = new User();
        user.setUsername(student.getFirstName() + "." + student.getLastName());
        user.setPassword(student.getFirstName() + "" + student.getLastName());
        user.setUserStatus("student");
        userDao.insert(user);
        student.setFacultyId(1);
        student.setUserId(userDao.lastUser().getId());
        studentDao.insert(student);
        return new Gson().toJson(response);
    }

    /**
     * Delete the selected Pedagogue from db
     * @param id gives pedagogue id selected to delete
     * @return to admin_view to see the changes that have been made
     */
    @RequestMapping(value="/deletePedagogue/{id}", method = RequestMethod.GET)
    public String deletePedagogue(@PathVariable int id){
        userDao.delete(pedagogueDao.getPedagogueById(id).getUserId());
        pedagogueDao.delete(id);
        return "redirect:/admin_view";
    }
}
