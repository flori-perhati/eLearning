package com.student.elearning.controller;

import com.student.elearning.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginViewController {

    @Autowired
    UserDao userDao;

    @RequestMapping("/")
    public String loginView(Model model) {
        model.addAttribute("user", userDao.lastUser());
        return "login_view";
    }
}
