package com.student.elearning.model;

public class MyModel {
    public Student student;
    public Pedagogue pedagogue;
    public Course course;
    public Question question;
    public Answer answer;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Pedagogue getPedagogue() {
        return pedagogue;
    }

    public void setPedagogue(Pedagogue pedagogue) {
        this.pedagogue = pedagogue;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
