package com.student.elearning.controller;

import com.google.gson.Gson;
import com.student.elearning.dao.CourseDao;
import com.student.elearning.dao.ExamDao;
import com.student.elearning.dao.ExamQuestionDao;
import com.student.elearning.entity.Course;
import com.student.elearning.entity.Exam;
import com.student.elearning.entity.ExamQuestion;
import com.student.elearning.entity.Question;
import com.student.elearning.network.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ExamController {

    /** Implementation of CRUD operation and other queries */
    @Autowired CourseDao courseDao;
    @Autowired ExamDao examDao;
    @Autowired ExamQuestionDao examQuestionDao;

    /**
     * Insert new Exam
     * @param exam represents new exam to be inserted
     * @return json response
     */
    @ResponseBody
    @RequestMapping(value = "/exam/insert", method = RequestMethod.POST, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String insert(@RequestBody Exam exam) {
        Response<Exam> response = new Response<>();

        if (examDao.insert(exam)) {
            Exam lastExam = examDao.lastExam();
            for (Question question : exam.getQuestions()) {
                ExamQuestion examQuestion = new ExamQuestion();
                examQuestion.setExamId(lastExam.getId());
                examQuestion.setQuestionId(question.getId());
                examQuestion.setQuestionPoints(question.getPoints());
                examQuestionDao.insert(examQuestion);
            }
            lastExam.setCourse(courseDao.getCourseById(exam.getCourseId()));
            response.setResponseCode(201);
            response.setResponseMessage("Created");
            response.setT(lastExam);
            return new Gson().toJson(response);
        }

        response.setResponseCode(500);
        response.setResponseMessage("Internal Server Error");
        response.setT(null);
        return new Gson().toJson(response);
    }

    /**
     * Provides exams of selected course
     * @param courseId used for getting exams of selected course
     * @return json response
     */
    @ResponseBody
    @RequestMapping(value = "/exams", method = RequestMethod.GET, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String exams(@RequestParam("courseId") long courseId) {
        Response<List<Exam>> response = new Response<>();

        List<Exam> exams = examDao.examByCourse(courseId);
        if (!exams.isEmpty()) {
            response.setResponseCode(200);
            response.setResponseMessage("OK");
            response.setT(exams);
            return new Gson().toJson(response);
        }

        response.setResponseCode(204);
        response.setResponseMessage("No Content");
        response.setT(null);
        return new Gson().toJson(response);
    }

    /**
     * Provides exam details to client side
     * @param examId used for getting exam details
     * @return json response
     */
    @ResponseBody
    @RequestMapping(value = "/exam/details", method = RequestMethod.GET, headers="Content-Type=application/json", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String examDetails(@RequestParam("examId") long examId) {
        Response<Exam> response = new Response<>();

        Exam exam = examDao.examById(examId);
        if (exam != null) {
            List<ExamQuestion> examQuestions = examQuestionDao.examQuestionByExamId(exam.getId());
            if (!examQuestions.isEmpty()) {
                exam.setExamQuestions(examQuestions);
                response.setResponseCode(200);
                response.setResponseMessage("OK");
                response.setT(exam);
                return new Gson().toJson(response);
            }
        }

        response.setResponseCode(500);
        response.setResponseMessage("Internal Server Error");
        response.setT(null);
        return new Gson().toJson(response);
    }
}
