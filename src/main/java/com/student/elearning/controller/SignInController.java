package com.student.elearning.controller;

import com.student.elearning.dao.UserDao;
import com.student.elearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class SignInController {

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/accounts/sign_in", method = RequestMethod.GET)
    public String loginView(Model model) {
        model.addAttribute("noUser", false);
        model.addAttribute("user", new User());
        return "sign_in";
    }

    @RequestMapping(value = "/accounts/sign_in/validate", method = RequestMethod.POST)
    public String validateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam(value = "remember-me", defaultValue="false") boolean rememberMe, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "sign_in";
        } else {
            User loggedUser = userDao.validateUser(user);
            if (loggedUser != null) {
                user.setId(loggedUser.getId());
                user.setUserStatus(loggedUser.getUserStatus());
                if (rememberMe) {
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("password", user.getPassword());
                    session.setAttribute("user_status", user.getUserStatus());
                    session.setAttribute("remember_me", true);
                }
                return "redirect:/admin";
            } else
                return "redirect:/accounts/sign_in/user_do_not_exist";
        }
    }

    @RequestMapping("/accounts/sign_in/user_do_not_exist")
    public String noUser(Model model) {
        model.addAttribute("noUser", true);
        model.addAttribute("user", new User());
        return "sign_in";
    }

    @RequestMapping("/admin")
    public String viewData() {
        return "admin";
    }

//    @ResponseBody
//    @RequestMapping(value = "/validateUser", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public String validateUser(@RequestBody User user, RedirectAttributes redirectAttributes) {
//        User loggedUser = userDao.validateUser(user);
//        String response;
//        if (loggedUser != null) {
//            user.setId(loggedUser.getId());
//            user.setUserStatus(loggedUser.getUserStatus());
//            response = loggedUser.getUserStatus();
//        } else
//            response = "not_found";
//
//        return new Gson().toJson(response);
//    }
}
