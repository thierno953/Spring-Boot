package com.tmb.EmployeeManagementApplication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootApplication
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}

}

// REST API for - CRUD operations
// CRUD --> Create, Read (Get), Update, Delete

@RestController
class EmployeeController {
	private final EmployeeJpaRepository jpaRepository;

	EmployeeController(EmployeeJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@GetMapping("/empoyees")
	public List<Employee> getAll() {
		return jpaRepository.findAll();
	}

	@GetMapping("/empoyees/{id}")
	public Optional<Employee> getOne(@PathVariable Long id) {
		return jpaRepository.findById(id);
	}

	@PostMapping("/empoyees")
	public Employee create(@RequestBody Employee employee) {
		System.out.println("employee = " + employee);
		return jpaRepository.save(employee);
	}

	@PutMapping("/empoyees/{id}")
	public Optional<Employee> update(@RequestBody Employee employee, @PathVariable Long id) {
		Optional<Employee> existingEmp = jpaRepository.findById(id);
		if (existingEmp.isPresent()) {
			Employee dbEmployee = existingEmp.get();
			dbEmployee.setName(employee.getName());
			dbEmployee.setRole(employee.getRole());
			jpaRepository.save(dbEmployee);
		} else {
			System.out.println("Employee  not found");
		}
		return jpaRepository.findById(id);
	}

	@DeleteMapping("/empoyees/{id}")
	public void deleteOne(@PathVariable Long id) {
		jpaRepository.deleteById(id);
	}
}

interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {
}

@Entity
class Employee {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String role;

	public Employee() {
	}

	public Employee(String name, String role) {
		this.name = name;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Employee employee = (Employee) o;
		return Objects.equals(id, employee.id) && Objects.equals(name, employee.name)
				&& Objects.equals(role, employee.role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, role);
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", name='" + name + '\'' +
				", role='" + role + '\'' +
				'}';
	}
}

@Configuration
class LoadDefaultData {

	@Bean
	CommandLineRunner initializeDatabase(EmployeeJpaRepository jpaRepository) {
		return args -> {
			Employee employee1 = new Employee("John", "Dev");
			Employee employee2 = new Employee("Pierre", "IT");
			Employee employee3 = new Employee("Paul", "Tested");
			Employee employee4 = new Employee("Jamila", "Doc");
			Employee employee5 = new Employee("Axel", "Dev");
			jpaRepository.saveAll(List.of(employee1, employee2, employee3, employee4, employee5));
		};
	}
}