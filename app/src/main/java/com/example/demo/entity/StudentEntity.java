package com.example.demo.entity;

/**
 * 生徒Entity
 */
public class StudentEntity implements Cloneable {

    /**
     * 生徒ID
     */
    private String studentId;

    /**
     * 生徒名
     */
    private String name;

    /**
     * 成績
     */
    private long score;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public StudentEntity clone() {
        var clonedStudent = new StudentEntity();
        clonedStudent.setName(this.name);
        clonedStudent.setScore(this.score);
        clonedStudent.setStudentId(this.studentId);
        return clonedStudent;
    }
}