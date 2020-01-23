package com.student.elearning.entity;

import java.util.Date;

public class ExamTaken {

    private long id;
    private long examId;
    private long studentId;
    private long pedagogueId;
    private Date holdingDate;
    private double result;
    private double totalPoints;

    private Exam exam;
    private Student student;
    private Pedagogue pedagogue;

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

    public Date getHoldingDate() {
        return holdingDate;
    }

    public void setHoldingDate(Date holdingDate) {
        this.holdingDate = holdingDate;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }
}
