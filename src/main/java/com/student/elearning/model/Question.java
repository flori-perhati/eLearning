package com.student.elearning.model;

import java.util.List;

public class Question {

    public long id;
    public long pedagogueId;
    public String questionType;
    public String description;

    public List<Answer> answers;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
