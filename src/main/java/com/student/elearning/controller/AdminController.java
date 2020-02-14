package com.student.elearning.controller;

import com.student.elearning.dao.FacultyDao;
import com.student.elearning.dao.PedagogueDao;
import com.student.elearning.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    /** Implementation of CRUD operation and other queries */
    @Autowired StudentDao studentDao;
    @Autowired PedagogueDao pedagogueDao;
    @Autowired FacultyDao facultyDao;

    /**
     * Shows the view when admin is logged in
     * @param model used for adding attributes to the view
     * @return admin view or redirect to sign_in view is user isn't remembered
     */
    @RequestMapping("/admin")
    public String adminView(Model model) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session =  attr.getRequest().getSession(true);

        if (session.getAttribute("user_status") != null && session.getAttribute("user_status").toString().equals("admin")) {
            model.addAttribute("faculties", facultyDao.getFaculties());
            model.addAttribute("students", studentDao.getStudents());
            model.addAttribute("pedagogues", pedagogueDao.getPedagogues());
            return "admin";
        } else
            return "redirect:/accounts/sign_in";
    }

    /**
     * Redirect to sign_in view when user logs out
     * @return sign_in view
     */
    @RequestMapping("/accounts/sign_out")
    public String logout() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session =  attr.getRequest().getSession(true);
        session.invalidate();
        return "redirect:/accounts/sign_in";
    }
}
