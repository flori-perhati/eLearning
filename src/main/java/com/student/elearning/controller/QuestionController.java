package com.student.elearning.controller;

import com.google.gson.Gson;
import com.student.elearning.dao.AnswerDao;
import com.student.elearning.dao.CourseDao;
import com.student.elearning.dao.QuestionDao;
import com.student.elearning.entity.Answer;
import com.student.elearning.entity.Course;
import com.student.elearning.entity.Question;
import com.student.elearning.network.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
public class QuestionController {

    /** Implementation of CRUD operation and other queries */
    @Autowired CourseDao courseDao;
    @Autowired QuestionDao questionDao;
    @Autowired AnswerDao answerDao;

    /**
     * Insert new Question
     * @param question represents new question to be inserted
     * @return json response
     */
    @ResponseBody
    @RequestMapping(value = "/question/insert", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String insert(@RequestBody Question question) {
        Response<Question> response = new Response<>();

        if (questionDao.insert(question)) {
            Question insertedQuestion = questionDao.lastQuestion();
            if (insertedQuestion != null) {
                for (Answer answer : question.getAnswers()) {
                    answer.setQuestionId(insertedQuestion.getId());
                    answerDao.insert(answer);
                }
                response.setResponseCode(201);
                response.setResponseMessage("Created");
                response.setT(question);
                return new Gson().toJson(response);
            }
        }

        response.setResponseCode(500);
        response.setResponseMessage("Internal Server Error");
        response.setT(null);
        return new Gson().toJson(response);
    }

    /**
     * Provides questions and courses when user wants to insert new exam
     * @param pedagogueId used for selecting question and courses created by this user
     * @return json response
     */
    @ResponseBody
    @RequestMapping(value = "/questions", method = RequestMethod.GET, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getQuestions(@RequestParam("pedagogueId") long pedagogueId) {
        Response<Map<String, Object>> response = new Response<>();

        List<Question> questions = questionDao.questionsByPedagogue(pedagogueId);
        List<Course> courses = courseDao.getCoursesByPedagogueId(pedagogueId);

        Map<String, Object> customObject = new HashMap<>();
        customObject.put("questions", questions);
        customObject.put("courses", courses);

        if (!questions.isEmpty() && !courses.isEmpty()) {
            response.setResponseCode(200);
            response.setResponseMessage("OK");
            response.setT(customObject);
            return new Gson().toJson(response);
        }

        response.setResponseCode(204);
        response.setResponseMessage("No Content");
        response.setT(null);
        return new Gson().toJson(response);
    }
}
