package com.student.elearning.controller;

import com.google.gson.Gson;
import com.student.elearning.dao.*;
import com.student.elearning.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
public class PedagogueController {

    @Autowired
    PedagogueDao pedagogueDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    UserDao userDao;
    @Autowired
    FacultyDao facultyDao;
    @Autowired
    ExamQuestionDao examQuestionDao;
    @Autowired
    StudentCourseDao studentCourseDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    AnswerDao answerDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    ExamDao examDao;

    @RequestMapping("/pedagogue")
    public String pedagogue(Model model, HttpSession session) {
//        long userId = (long) session.getAttribute("user_id");
        long userId = 2;
        Pedagogue pedagogue = pedagogueDao.pedagogueByUserId(userId);

        model.addAttribute("pedagogue", pedagogue);
        model.addAttribute("courses", courseDao.getCoursesByPedagogueId(pedagogue.getId()));
        model.addAttribute("exams", examDao.examByPedagogue(pedagogue.getId()));
        model.addAttribute("students", new ArrayList<>());
        model.addAttribute("questions", new ArrayList<>());
        model.addAttribute("faculties", facultyDao.getFaculties());
        return "pedagogue";
    }

    @ResponseBody
    @RequestMapping(value = "/updatePedagogue", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String savePedagogue(@RequestBody Pedagogue pedagogue, HttpSession session) {
        User user = pedagogue.getUser();
//        long userId = (long) session.getAttribute("user_id");
        long userId = 2;
        user.setId(userId);

        return (userDao.update(user) && pedagogueDao.update(pedagogue)) ? new Gson().toJson(pedagogue) : new Gson().toJson("Something went wrong!");
    }

    @ResponseBody
    @RequestMapping(value = "/createCourse", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createQuestion(@RequestBody Course course) {
        if (courseDao.insert(course)) {
            Course lastCourse = courseDao.lastCourse();
            if (lastCourse == null)
                return new Gson().toJson("Something went wrong!");
            else
                return new Gson().toJson(lastCourse);
        } else
            return new Gson().toJson("Something went wrong!");
    }

    @ResponseBody
    @RequestMapping(value = "/getStudents", method = RequestMethod.GET, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getStudents(@RequestParam("courseId") long courseId, @RequestParam("facultyId") long facultyId) {
        List<Student> students = studentDao.getStudentsForCourse(facultyId, courseId, studentCourseDao);
        if (students.isEmpty())
            return new Gson().toJson("There are no students inserted!");
        else
            return new Gson().toJson(students);
    }

    @ResponseBody
    @RequestMapping(value = "/addStudentToCourse", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addStudentsToCourse(@RequestBody List<StudentCourse> studentCourses) {
        for (StudentCourse studentCourse : studentCourses) {
            studentCourseDao.insert(studentCourse);
        }
        return new Gson().toJson("Success");
    }

    @ResponseBody
    @RequestMapping(value = "/getQuestions", method = RequestMethod.GET, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getQuestions(@RequestParam("pedagogueId") long pedagogueId) {
        List<Question> questions = questionDao.questionsByPedagogue(pedagogueId);
        List<Course> courses = courseDao.getCoursesByPedagogueId(pedagogueId);
        Map mp = new HashMap();

        mp.put("questions", questions);
        mp.put("courses", courses);

        if (questions.isEmpty() && courses.isEmpty())
            return new Gson().toJson("You have no question or course inserted!");
        else
            return new Gson().toJson(mp);
    }

    @ResponseBody
    @RequestMapping(value = "/createExam", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createExam(@RequestBody Exam exam) {
        if (examDao.insert(exam)) {
            Exam lastExam = examDao.lastExam();
            for (Question question : exam.getQuestions()) {
                ExamQuestion examQuestion = new ExamQuestion();
                examQuestion.setExamId(lastExam.getId());
                examQuestion.setQuestionId(question.getId());
                examQuestion.setQuestionPoints(question.getPoints());
                examQuestionDao.insert(examQuestion);
            }
            return new Gson().toJson(exam);
        } else
            return new Gson().toJson("Something went wrong!");
    }

    @ResponseBody
    @RequestMapping(value = "/createQuestion", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createQuestion(@RequestBody Question question) {
        String response = "Success";

        if (questionDao.insert(question)) {
            Question insertedQuestion = questionDao.lastQuestion();
            if (insertedQuestion != null) {
                for (Answer answer : question.getAnswers()) {
                    answer.setQuestionId(insertedQuestion.getId());
                    if (!answerDao.insert(answer))
                        response = "Something went wrong!";
                }
            } else
                response = "Something went wrong!";
        } else
            response = "Something went wrong!";

        return new Gson().toJson(response);
    }

//    /**
//     * Shows the view for updating the pedagogue selected
//     * @param id used for getting Pedagogue details
//     * @param model type Model used for adding parameters to the view
//     * @return path to the specific view
//     */
//    @RequestMapping(value = "/editPedagogue/{id}")
//    public String editPedagogue(@PathVariable int id, Model model){
//        model.addAttribute("pedagogue", pedagogueDao.pedagogueDetailsToEdit(id));
//        return "edit_pedagogue_view";
//    }

//    /**
//     * Updates the modified pedagogue
//     * @param pedagogue type Student filled with new data
//     * @return to admin_view to see the changes that have been made
//     */
//    @RequestMapping(value = "/editPedagogue/updatePedagogue", method = RequestMethod.POST)
//    public String updateStudent(@ModelAttribute("pedagogue") Pedagogue pedagogue){
//        pedagogueDao.update(pedagogue);
//        userDao.update(pedagogue.getUser());
//        return "redirect:/admin_view";
//    }

//    @RequestMapping(value = "/addCourse/{id}")
//    public String addCourse(@ModelAttribute("myModel") MyModel myModel, @PathVariable long id, RedirectAttributes redirectAttributes) {
//        Course course = myModel.getCourse();
//        course.setPedagogueId(id);
//        courseDao.insert(course);
//        User user = userDao.getUserById(pedagogueDao.getPedagogueById((int) id).getUserId());
//        redirectAttributes.addFlashAttribute("user", user);
//        return "redirect:/pedagogue_view";
//    }

//    /**
//     * Delete the selected Course from db
//     * @param id gives course id selected to delete
//     * @return to pedagogue_view to see the changes that have been made
//     */
//    @RequestMapping(value="/deleteCourse/{id}", method = RequestMethod.GET)
//    public String deletePedagogue(@PathVariable int id){
//        courseDao.delete(id);
//        return "redirect:/pedagogue_view";
//    }

//    @RequestMapping(value = "/addQuestion/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public String addQuestion(@RequestBody Question question, @PathVariable long id, RedirectAttributes redirectAttributes) {
//        questionDao.insert(question);
//        long questionId = questionDao.lastQuestion().getId();
//        for (Answer answer : question.getAnswers()) {
//            answer.setQuestionId(questionId);
//            answerDao.insert(answer);
//        }
//        User user = userDao.getUserById(pedagogueDao.getPedagogueById((int) id).getUserId());
//        redirectAttributes.addFlashAttribute("user", user);
//        return "redirect:/pedagogue_view";
//    }
}

