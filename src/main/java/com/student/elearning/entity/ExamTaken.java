package com.student.elearning.entity;

public class ExamTaken {

    private long id;
    private long examId;
    private long studentId;
    private long pedagogueId;
    private String holdingDate;
    private String result;

    private String course;
    private String examHeader;
    private String examDescription;

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

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getPedagogueId() {
        return pedagogueId;
    }

    public void setPedagogueId(long pedagogueId) {
        this.pedagogueId = pedagogueId;
    }

    public String getHoldingDate() {
        return holdingDate;
    }

    public void setHoldingDate(String holdingDate) {
        this.holdingDate = holdingDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getExamHeader() {
        return examHeader;
    }

    public void setExamHeader(String examHeader) {
        this.examHeader = examHeader;
    }

    public String getExamDescription() {
        return examDescription;
    }

    public void setExamDescription(String examDescription) {
        this.examDescription = examDescription;
    }
}
