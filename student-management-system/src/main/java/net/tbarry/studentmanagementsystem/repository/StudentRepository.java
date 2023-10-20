package net.tbarry.studentmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.tbarry.studentmanagementsystem.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
