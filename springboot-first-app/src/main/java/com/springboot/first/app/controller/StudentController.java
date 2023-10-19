package com.springboot.first.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.first.app.Student;

@RestController
public class StudentController {

    @GetMapping("/student")
    public Student getStudent() {
        return new Student("John", "Doe");
    }

    @GetMapping("/students")
    public List<Student> getStudents() {

        List<Student> students = new ArrayList<>();
        students.add(new Student("John", "Doe"));
        students.add(new Student("Pierre", "Durant"));
        students.add(new Student("Marion", "Colard"));
        return students;
    }

    // @PathVariable annotation
    @GetMapping("/student/{firstName}/{lastName}")
    public Student studentPathVariable(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName) {
        return new Student(firstName, lastName);
    }

    // build rest API to handle query parameters
    // http://localhost:8080/student/query?firstName=John&lastName=Doe
    @GetMapping("/student/query")
    public Student studentQueryParam(
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName) {
        return new Student(firstName, lastName);
    }
}
