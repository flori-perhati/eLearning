package com.student.elearning.controller;

import com.student.elearning.dao.*;
import com.student.elearning.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class PedagogueController {

    @Autowired
    PedagogueDao pedagogueDao;
    @Autowired
    FacultyDao facultyDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    AnswerDao answerDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    ExamDao examDao;
    @Autowired
    UserDao userDao;

    /**
     * Shows the view when a pedagogue is logged in
     * @param model type Model used for adding parameters to the view
     * @return pedagogue_view
     */
    @RequestMapping("/pedagogue_view")
    public String viewData(@ModelAttribute("user") User user, Model model) {
        Pedagogue pedagogue = pedagogueDao.pedagogueByUserId(user.getId());
        List<String> questionTypes = new ArrayList<>();
        questionTypes.add("Yes/No");
        questionTypes.add("Single Choice");
        questionTypes.add("Multiple Choice");

        model.addAttribute("pedagogue", pedagogue);
        model.addAttribute("myModel", new MyModel());
        model.addAttribute("questionTypes", questionTypes);
        model.addAttribute("courses", courseDao.getCoursesByPedagogueId(pedagogue.getId()));
        model.addAttribute("exams", examDao.examByPedagogue(pedagogue.getId()));

        model.addAttribute("students", new ArrayList<>());
        model.addAttribute("questions", new ArrayList<>());
        return "pedagogue_view";
    }

    @RequestMapping("/pedagogue")
    public String pedagogue(Model model) {
//        long userId = (long) session.getAttribute("user_id");
        long userId = 2;
        Pedagogue pedagogue = pedagogueDao.pedagogueByUserId(userId);
        List<String> questionTypes = new ArrayList<>();
        questionTypes.add("Yes/No");
        questionTypes.add("Single Choice");
        questionTypes.add("Multiple Choice");

        model.addAttribute("pedagogue", pedagogue);
        model.addAttribute("questionTypes", questionTypes);
        model.addAttribute("courses", courseDao.getCoursesByPedagogueId(pedagogue.getId()));
        model.addAttribute("exams", examDao.examByPedagogue(pedagogue.getId()));
        model.addAttribute("students", new ArrayList<>());
        model.addAttribute("questions", new ArrayList<>());
        return "pedagogue";
    }

    /**
     * Shows the view for inserting new Student to db
     * @param model type Model used for adding parameters to the view
     * @return path to the specific view
     */
    @RequestMapping("/addPedagogue")
    public String show(Model model) {
        model.addAttribute("pedagogue", new Pedagogue());
        model.addAttribute("faculties", facultyDao.getFaculties());
        return "add_pedagogue_view";
    }

    /**
     * Shows the view for updating the pedagogue selected
     * @param id used for getting Pedagogue details
     * @param model type Model used for adding parameters to the view
     * @return path to the specific view
     */
    @RequestMapping(value = "/editPedagogue/{id}")
    public String editPedagogue(@PathVariable int id, Model model){
        model.addAttribute("pedagogue", pedagogueDao.pedagogueDetailsToEdit(id));
        return "edit_pedagogue_view";
    }

    /**
     * Updates the modified pedagogue
     * @param pedagogue type Student filled with new data
     * @return to admin_view to see the changes that have been made
     */
    @RequestMapping(value = "/editPedagogue/updatePedagogue", method = RequestMethod.POST)
    public String updateStudent(@ModelAttribute("pedagogue") Pedagogue pedagogue){
        pedagogueDao.update(pedagogue);
        userDao.update(pedagogue.getUser());
        return "redirect:/admin_view";
    }

    @RequestMapping(value = "/addCourse/{id}")
    public String addCourse(@ModelAttribute("myModel") MyModel myModel, @PathVariable long id, RedirectAttributes redirectAttributes) {
        Course course = myModel.getCourse();
        course.setPedagogueId(id);
        courseDao.insert(course);
        User user = userDao.getUserById(pedagogueDao.getPedagogueById((int) id).getUserId());
        redirectAttributes.addFlashAttribute("user", user);
        return "redirect:/pedagogue_view";
    }

    /**
     * Delete the selected Course from db
     * @param id gives course id selected to delete
     * @return to pedagogue_view to see the changes that have been made
     */
    @RequestMapping(value="/deleteCourse/{id}", method = RequestMethod.GET)
    public String deletePedagogue(@PathVariable int id){
        courseDao.delete(id);
        return "redirect:/pedagogue_view";
    }

    @RequestMapping(value = "/addQuestion/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addQuestion(@RequestBody Question question, @PathVariable long id, RedirectAttributes redirectAttributes) {
        questionDao.insert(question);
        long questionId = questionDao.lastQuestion().getId();
        for (Answer answer : question.getAnswers()) {
            answer.setQuestionId(questionId);
            answerDao.insert(answer);
        }
        User user = userDao.getUserById(pedagogueDao.getPedagogueById((int) id).getUserId());
        redirectAttributes.addFlashAttribute("user", user);
        return "redirect:/pedagogue_view";
    }
}

