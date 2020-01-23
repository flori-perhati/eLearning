package com.student.elearning.entity;

import java.util.List;

public class Course {

    private long id;
    private long pedagogueId;
    private String description;

    private Pedagogue pedagogue;
    private List<Student> students;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Pedagogue getPedagogue() {
        return pedagogue;
    }

    public void setPedagogue(Pedagogue pedagogue) {
        this.pedagogue = pedagogue;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
