package com.student.elearning.model;

import java.util.List;

public class Course {

    public long id;
    public long pedagogueId;
    public String description;

    public List<Student> students;

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
