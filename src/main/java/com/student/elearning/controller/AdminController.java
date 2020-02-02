package com.student.elearning.controller;

import com.google.gson.Gson;
import com.student.elearning.dao.FacultyDao;
import com.student.elearning.dao.PedagogueDao;
import com.student.elearning.dao.StudentDao;
import com.student.elearning.dao.UserDao;
import com.student.elearning.entity.Pedagogue;
import com.student.elearning.entity.Student;
import com.student.elearning.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("faculties", facultyDao.getFaculties());
        model.addAttribute("students", studentDao.getStudents());
        model.addAttribute("pedagogues", pedagogueDao.getPedagogues());
        return "admin";
    }

    @ResponseBody
    @RequestMapping(value = "/savePedagogue", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String savePedagogue(@RequestBody Pedagogue pedagogue) {
        User user = new User();
        user.setUsername(pedagogue.getFirstName().substring(0, 1) + "" + pedagogue.getLastName());
        user.setPassword(pedagogue.getFirstName().substring(0, 1) + "" + pedagogue.getLastName());
        user.setUserStatus("pedagogue");
        userDao.insert(user);
        pedagogue.setFacultyId(pedagogue.getFacultyId());
        pedagogue.setUserId(userDao.lastUser().getId());
        pedagogue.setStatus(true);

        if (pedagogueDao.insert(pedagogue))
            return new Gson().toJson(pedagogueDao.getLastPedagogue());
        else
            return new Gson().toJson("Something went wrong!");
    }

    /**
     * Delete the selected Pedagogue from db
     * @param id gives pedagogue id selected to delete
     * @return to admin_view to see the changes that have been made
     */
    @RequestMapping(value="/deletePedagogue/{id}", method = RequestMethod.GET)
    public String deletePedagogue(@PathVariable int id){
        pedagogueDao.updateStatus(0, id);
        return "redirect:/admin";
    }

    @ResponseBody
    @RequestMapping(value = "/saveStudent", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveStudent(@RequestBody Student student) {
        User user = new User();
        user.setUsername(student.getFirstName().substring(0, 1) + "" + student.getLastName());
        user.setPassword(student.getFirstName().substring(0, 1) + "" + student.getLastName());
        user.setUserStatus("student");
        userDao.insert(user);
        student.setFacultyId(student.getFacultyId());
        student.setUserId(userDao.lastUser().getId());
        student.setStatus(true);

        if (studentDao.insert(student))
            return new Gson().toJson(studentDao.getLastStudent());
        else
            return new Gson().toJson("Something went wrong!");
    }

    /**
     * Delete the selected Student from db
     * @param id gives student id selected to delete
     * @return to admin_view to see the changes that have been made
     */
    @RequestMapping(value="/deleteStudent/{id}", method = RequestMethod.GET)
    public String deleteStudent(@PathVariable int id){
        studentDao.updateStatus(0, id);
        return "redirect:/admin";
    }
}
