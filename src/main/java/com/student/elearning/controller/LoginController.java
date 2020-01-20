package com.student.elearning.controller;

import com.student.elearning.dao.UserDao;
import com.student.elearning.model.Question;
import com.student.elearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserDao userDao;

//    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
//    public String loginView(Model model) {
//        model.addAttribute("user", new User());
//        return "login_view";
//    }

//    @RequestMapping(value="/validateUser", method = RequestMethod.POST)
//    public String validateUser(@RequestBody Question question, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes){
//        User loggedUser = userDao.validateUser(user);
//        if (loggedUser != null) {
//            user.setId(loggedUser.getId());
//            user.setUserStatus(loggedUser.getUserStatus());
//            switch (loggedUser.getUserStatus()) {
//                case "admin":
//                    return "redirect:/admin_view";
//                case "pedagogue":
//                    redirectAttributes.addFlashAttribute("user", user);
//                    return "redirect:/pedagogue_view";
//                case "student":
//                    return "redirect:/student_view";
//            }
//        }
//        return "login_view";
//    }
}
