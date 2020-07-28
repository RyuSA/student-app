package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.example.demo.entity.StudentEntity;

import org.springframework.stereotype.Component;

@Component
public class StudentService {

    // 一時的なDB代わり
    private List<StudentEntity> savedStudents;

    public StudentService() {
        this.savedStudents = new ArrayList<StudentEntity>();
    }

    /**
     * 保存している生徒Entityを全て返却する
     * 
     * @return {@link StudentEntity}
     */
    public List<StudentEntity> findStudents() {
        // Collections#unmodifiableList を用いることでImmutableなリストとして生徒Entity一覧を返却
        return Collections.unmodifiableList(this.savedStudents);
    }

    /**
     * 指定された生徒Entityを返却する ただし指定された生徒Entityが存在しない場合はnullを返却する
     * 
     * @return {@link StudentEntity}
     */
    public StudentEntity findStudentById(String studentId) {

        for (var student : this.savedStudents) {
            if (student.getStudentId().equals(studentId)) {
                return student.clone();
            }
        }
        return null;
    }

    /**
     * 与えられたデータから生徒Entityを作成し、データを保存する
     * 
     * @param name  名前
     * @param score 成績
     * @return {@link StudentEntity} 作成された生徒Entity
     * @throws IllegalArgumentException 引数のいずれかがnull
     */
    public StudentEntity insertStudentEntity(String name, Long score) {
        if (name == null || score == null) {
            throw new IllegalArgumentException("名前と成績は必須です。");
        }
        var newStudent = new StudentEntity();
        newStudent.setName(name);
        newStudent.setScore(score);
        newStudent.setStudentId(UUID.randomUUID().toString());
        this.savedStudents.add(newStudent);
        return newStudent.clone();
    }

    /**
     * 指定された生徒IDを元に生徒Entityを与えられたデータに更新し、更新したデータを返却する
     * 
     * @param name  名前
     * @param score 成績
     * @return {@link StudentEntity} 更新された生徒Entity / 生徒IDに一致する生徒がいない場合はnull
     * @throws IllegalArgumentException 引数のいずれかがnull
     */
    public StudentEntity updateStudentEntity(String studentId, String name, Long score) {
        if (studentId == null || name == null || score == null) {
            throw new IllegalArgumentException("生徒IDと名前と成績は必須です。");
        }

        for (var student : this.savedStudents) {
            if (student.getStudentId().equals(studentId)) {
                student.setName(name);
                student.setScore(score);
                return student.clone();
            }
        }
        return null;
    }
}