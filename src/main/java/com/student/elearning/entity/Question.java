package com.student.elearning.entity;

import java.util.List;

public class Question {

    private long id;
    private long pedagogueId;
    private String questionType;
    private String value;

    private double points;

    private Pedagogue pedagogue;
    private List<Answer> answers;
    private List<Exam> exams;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPedagogueId() {
        return pedagogueId;
    }

    public void setPedagogueId(long pedagogueId) {
        this.pedagogueId = pedagogueId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public Pedagogue getPedagogue() {
        return pedagogue;
    }

    public void setPedagogue(Pedagogue pedagogue) {
        this.pedagogue = pedagogue;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }
}
