package com.student.elearning.entity;

public class ExamQuestion {

    private long id;
    private long examId;
    private long questionId;
    private double questionPoints;

    private String questionDescription;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public double getQuestionPoints() {
        return questionPoints;
    }

    public void setQuestionPoints(double questionPoints) {
        this.questionPoints = questionPoints;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }
}
