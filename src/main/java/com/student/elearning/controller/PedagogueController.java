package com.student.elearning.controller;

import com.google.gson.Gson;
import com.student.elearning.dao.*;
import com.student.elearning.entity.Pedagogue;
import com.student.elearning.entity.User;
import com.student.elearning.network.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class PedagogueController {

    /** Implementation of CRUD operation and other queries */
    @Autowired UserDao userDao;
    @Autowired PedagogueDao pedagogueDao;
    @Autowired FacultyDao facultyDao;
    @Autowired CourseDao courseDao;
    @Autowired ExamDao examDao;
    @Autowired QuestionDao questionDao;

    /**
     * Shows the view when pedagogue is logged in
     * @param model used for adding attributes to the view
     * @return pedagogue view or redirect to sign_in view is user isn't remembered
     */
    @RequestMapping("/pedagogue")
    public String pedagogue(Model model) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session =  attr.getRequest().getSession(true);

        if (session.getAttribute("user_id") != null && session.getAttribute("user_status") != null && session.getAttribute("user_status").toString().equals("pedagogue")) {
            long userId = Long.parseLong(session.getAttribute("user_id").toString());
            Pedagogue pedagogue = pedagogueDao.pedagogueByUserId(userId);

            model.addAttribute("pedagogue", pedagogue);
            model.addAttribute("courses", courseDao.getCoursesByPedagogueId(pedagogue.getId()));
            model.addAttribute("exams", examDao.examByPedagogue(pedagogue.getId()));
            model.addAttribute("questions", questionDao.questionsByPedagogue(pedagogue.getId()));
            model.addAttribute("faculties", facultyDao.getFaculties());
            return "pedagogue";
        } else
            return "redirect:/accounts/sign_in";
    }

    /**
     * Insert new Pedagogue
     * @param pedagogue represents new pedagogue to be inserted
     * @return json response
     */
    @ResponseBody
    @RequestMapping(value = "/pedagogue/insert", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String insert(@RequestBody Pedagogue pedagogue) {
        Response<Pedagogue> response = new Response<>();

        User user = new User();
        user.setUsername(pedagogue.getFirstName().substring(0, 1) + "" + pedagogue.getLastName());
        user.setPassword(pedagogue.getFirstName().substring(0, 1) + "" + pedagogue.getLastName());
        user.setUserStatus("pedagogue");
        userDao.insert(user);

        if (userDao.insert(user)) {
            pedagogue.setFacultyId(pedagogue.getFacultyId());
            pedagogue.setUserId(userDao.lastUser().getId());
            pedagogue.setStatus(true);

            if (pedagogueDao.insert(pedagogue)) {
                response.setResponseCode(201);
                response.setResponseMessage("Created");
                response.setT(pedagogueDao.getLastPedagogue());
                return new Gson().toJson(response);
            }
        }

        response.setResponseCode(500);
        response.setResponseMessage("Internal Server Error");
        response.setT(null);
        return new Gson().toJson(response);
    }

    /**
     * Updates the logged in pedagogue
     * @param pedagogue filled with new field values
     * @return json response
     */
    @ResponseBody
    @RequestMapping(value = "/pedagogue/update", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String update(@RequestBody Pedagogue pedagogue) {
        Response<Pedagogue> response = new Response<>();

        if (userDao.update(pedagogue.getUser()) && pedagogueDao.update(pedagogue)) {
            response.setResponseCode(200);
            response.setResponseMessage("OK");
            response.setT(pedagogue);
            return new Gson().toJson(response);
        }

        response.setResponseCode(500);
        response.setResponseMessage("Internal Server Error");
        response.setT(null);
        return new Gson().toJson(response);
    }

    /**
     * Delete the selected Pedagogue from db
     * @param id gives pedagogue id selected to delete
     * @return to admin view
     */
    @RequestMapping(value="/pedagogue/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id){
        pedagogueDao.updateStatus(0, id);
        return "redirect:/admin";
    }
}

