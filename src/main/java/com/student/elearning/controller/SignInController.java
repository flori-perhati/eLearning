package com.student.elearning.controller;

import com.student.elearning.dao.UserDao;
import com.student.elearning.model.MyModel;
import com.student.elearning.model.Pedagogue;
import com.student.elearning.model.Student;
import com.student.elearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class SignInController {

    @Autowired
    UserDao userDao;

    @RequestMapping("/")
    public ModelAndView loginView(Model model) {
        return new ModelAndView("sign_in");
    }

    @RequestMapping("/validateUser")
    public ModelAndView validateUser(Model model) {
        return new ModelAndView("redirect:/admin");
    }

//    @RequestMapping(value = "/validateUser", method = RequestMethod.POST)
//    public ModelAndView validateUser(@RequestBody User user, RedirectAttributes redirectAttributes) {
//        User loggedUser = userDao.validateUser(user);
//        if (loggedUser != null) {
//            user.setId(loggedUser.getId());
//            user.setUserStatus(loggedUser.getUserStatus());
//            switch (loggedUser.getUserStatus()) {
//                case "admin":
//                    return new ModelAndView("admin");
//                case "pedagogue":
//                    redirectAttributes.addFlashAttribute("user", user);
//                    return new ModelAndView("pedagogue_view");
//                case "student":
//                    return new ModelAndView("student_view");
//            }
//        }
//        return new ModelAndView("sign_in");
//    }

    @RequestMapping("/admin")
    public ModelAndView viewData() {
        return new ModelAndView("admin");
    }
}
