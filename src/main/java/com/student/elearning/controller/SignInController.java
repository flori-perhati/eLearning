package com.student.elearning.controller;

import com.student.elearning.dao.PedagogueDao;
import com.student.elearning.dao.StudentDao;
import com.student.elearning.dao.UserDao;
import com.student.elearning.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class SignInController {

    /** Implementation of CRUD operation and other queries */
    @Autowired UserDao userDao;
    @Autowired StudentDao studentDao;
    @Autowired PedagogueDao pedagogueDao;

    /**
     * Shows sign_in view
     * @param model used for adding attributes to the view
     * @return sign_in view
     */
    @RequestMapping(value = "/accounts/sign_in", method = RequestMethod.GET)
    public String loginView(Model model) {
        model.addAttribute("noUser", false);
        model.addAttribute("user", new User());
        return "sign_in";
    }

    /**
     * Shows specific view based on user status
     * @param user represents user to be logged in
     * @param bindingResult used for validation
     * @param rememberMe used for kepping user logged in
     * @param session used for to save user to session
     * @param redirectAttributes used for redirecting attributes to another controller
     * @return to specific view for the logged user or sign_in view with warning message
     */
    @RequestMapping(value = "/accounts/sign_in/validate", method = RequestMethod.POST)
    public String validateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam(value = "remember-me", defaultValue="false") boolean rememberMe, HttpSession session, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sign_in";
        } else {
            User loggedUser = userDao.validateUser(user, studentDao, pedagogueDao);
            if (loggedUser != null) {
                if (rememberMe) {
                    session.setAttribute("user_id", loggedUser.getId());
                    session.setAttribute("username", loggedUser.getUsername());
                    session.setAttribute("password", loggedUser.getPassword());
                    session.setAttribute("user_status", loggedUser.getUserStatus());
                    session.setAttribute("remember_me", true);
                }

                redirectAttributes.addFlashAttribute(loggedUser.getUserStatus() + "_user", loggedUser);
                return "redirect:/" + loggedUser.getUserStatus();
            } else
                return "redirect:/accounts/sign_in/user_does_not_exist";
        }
    }

    /**
     * Shows negative message when user is not found
     * @param model used for adding attributes to the view
     * @return sign_in view with warning message
     */
    @RequestMapping("/accounts/sign_in/user_does_not_exist")
    public String noUser(Model model) {
        model.addAttribute("noUser", true);
        model.addAttribute("user", new User());
        return "sign_in";
    }
}
