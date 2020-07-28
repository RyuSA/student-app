package com.example.demo.controller;

import java.util.List;

import com.example.demo.entity.StudentEntity;
import com.example.demo.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生徒EntityのCRUD APIエンドポイント
 */
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 登録されている生徒Entityを全て返却する
     */
    @GetMapping(path = "/students")
    public List<StudentEntity> retrieveStudents() {
        return this.studentService.findStudents();
    }

    /**
     * 指定された生徒Entityを返却する
     */
    @GetMapping(path = "/students/{studentId}")
    public ResponseEntity<StudentEntity> retrieveStudent(@PathVariable String studentId) {
        var student = this.studentService.findStudentById(studentId);

        if (student == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST /student で受け取るリクエストボディのスキーマ
     */
    public static class PostRequestBody {

        private String name;
        private Long score;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getScore() {
            return score;
        }

        public void setScore(Long score) {
            this.score = score;
        }
    }

    /**
     * 指定された内容で生徒を保存する
     */
    @PostMapping(path = "/students")
    public ResponseEntity<StudentEntity> createStudent(@RequestBody PostRequestBody postRequestBody) {

        try {
            var student = this.studentService.insertStudentEntity(postRequestBody.getName(),
                    postRequestBody.getScore());
            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * PUT /students/{ID} で受け取るリクエストボディのスキーマ
     */
    public static class PutRequestBody {

        private String name;
        private Long score;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getScore() {
            return score;
        }

        public void setScore(Long score) {
            this.score = score;
        }
    }

    /**
     * 指定された生徒Entityを更新する
     */
    @PutMapping(path = "/students/{studentId}")
    public ResponseEntity<StudentEntity> updateStudent(@PathVariable String studentId,
            @RequestBody PutRequestBody putRequestBody) {
        try {
            var student = this.studentService.updateStudentEntity(studentId, putRequestBody.getName(),
                    putRequestBody.getScore());

            if (student == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
