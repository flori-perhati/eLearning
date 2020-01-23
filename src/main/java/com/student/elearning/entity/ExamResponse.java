package com.student.elearning.entity;

public class ExamResponse {

    private long id;
    private long examTakenId;
    private long questionId;
    private long answerId;

    private Exam exam;
    private Question question;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExamTakenId() {
        return examTakenId;
    }

    public void setExamTakenId(long examTakenId) {
        this.examTakenId = examTakenId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
