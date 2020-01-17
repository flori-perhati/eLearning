package com.student.elearning.model;

import java.util.List;

public class Exam {

    private long id;
    private long pedagogueId;
    private long courseId;
    private String title;

    private List<Question> questions;

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

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
