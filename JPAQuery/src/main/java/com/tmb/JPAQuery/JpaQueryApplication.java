package com.tmb.JPAQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootApplication
public class JpaQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaQueryApplication.class, args);
	}
}

@RestController
class EmployeeController {
	private final EmployeeRepo repo;

	public EmployeeController(EmployeeRepo repo) {
		this.repo = repo;
	}

	@GetMapping("/emps")
	public List<Employee> getAll() {
		return repo.findAll();
	}

	@GetMapping("/getById")
	public Optional<Employee> getById(@RequestParam Long id) {
		return repo.findById(id);
	}

	@GetMapping("/findByName")
	public List<Employee> findByName(@RequestParam String name) {
		return repo.findByName(name);
	}

	@GetMapping("/findByNameIgnoreCase")
	public List<Employee> findByNameIgnoreCase(@RequestParam String name) {
		return repo.findByNameIgnoreCase(name);
	}

	@GetMapping("/empsByAge")
	public List<Employee> findByAge(@RequestParam int age) {
		return repo.findByAge(age);
	}

	@GetMapping("/empsByCity")
	public List<Employee> findByCity(@RequestParam String city) {
		return repo.findByCity(city);
	}

	@GetMapping("/findBySalaryGreaterThan")
	public List<Employee> findBySalaryGreaterThan(@RequestParam int salary) {
		return repo.findBySalaryGreaterThan(salary);
	}

	@GetMapping("/findByNameAndAge")
	public List<Employee> findByNameAndAge(@RequestParam String name, @RequestParam int age) {
		return repo.findByNameAndAge(name, age);
	}

	@GetMapping("/findByNameOrAge")
	public List<Employee> findByNameOrAge(@RequestParam String name, @RequestParam int age) {
		return repo.findByNameOrAge(name, age);
	}

	@GetMapping("/allSQLQuery")
	public List<Employee> allSQLQuery() {
		return repo.allSQLQuery();
	}

	@GetMapping("/allJavaQuery")
	public List<Employee> allJavaQuery() {
		return repo.allJavaQuery();
	}

	@GetMapping("/allJavaQueryWhere")
	public List<Employee> allJavaQueryWhere(@RequestParam int age) {
		return repo.allJavaQueryWhere(age);
	}

	@GetMapping("/allSQLQueryWhere")
	public List<Employee> allSQLQueryWhere(@RequestParam int firstId, @RequestParam int secondId) {
		return repo.allSQLQueryWhere(firstId, secondId);
	}
}

interface EmployeeRepo extends JpaRepository<Employee, Long> {
	List<Employee> findByName(String name);

	List<Employee> findByNameIgnoreCase(String name);

	List<Employee> findByAge(int age);

	List<Employee> findByCity(String city);

	List<Employee> findBySalaryGreaterThan(int salary);

	List<Employee> findByNameAndAge(String name, int age);

	List<Employee> findByNameOrAge(String name, int age);

	@Query(value = "SELECT * FROM EMP", nativeQuery = true)
	List<Employee> allSQLQuery();

	@Query("SELECT e FROM Employee e")
	List<Employee> allJavaQuery();

	@Query("SELECT e FROM Employee e Where e.age > ?1")
	List<Employee> allJavaQueryWhere(int age);

	@Query(value = "SELECT * FROM EMP WHERE id > ?1 and id < ?2", nativeQuery = true)
	List<Employee> allSQLQueryWhere(int firstId, int secondId);
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "EMP")
class Employee {
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private int age;
	private String city;
	private int salary;
}

@Configuration
class LoadData {
	@Bean
	CommandLineRunner loadInitDatabase(EmployeeRepo repo) {
		return args -> {
			List<Employee> employeeList = new ArrayList<>();

			employeeList.add(Employee.builder().name("John").age(50).city("Brussels").salary(500).build());
			employeeList.add(Employee.builder().name("Pierre").age(42).city("Paris").salary(1700).build());
			employeeList.add(Employee.builder().name("Barbara").age(33).city("Rome").salary(1000).build());
			employeeList.add(Employee.builder().name("Tim").age(24).city("Amsterdam").salary(2100).build());
			employeeList.add(Employee.builder().name("Bene").age(30).city("Berlin").salary(3000).build());
			employeeList.add(Employee.builder().name("Axel").age(36).city("Madrid").salary(1200).build());
			employeeList.add(Employee.builder().name("Sylvain").age(17).city("London").salary(1400).build());
			employeeList.add(Employee.builder().name("Arthur").age(20).city("Lisbon").salary(2300).build());

			repo.saveAll(employeeList);
		};
	}
}