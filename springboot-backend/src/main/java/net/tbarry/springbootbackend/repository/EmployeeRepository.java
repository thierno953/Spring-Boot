package net.tbarry.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.tbarry.springbootbackend.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
