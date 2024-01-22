package com.tmb.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootApplication
public class PaginationApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaginationApplication.class, args);
	}
}

@RestController
class EmployeeController {
	private final EmployeeRepo repo;

	public EmployeeController(EmployeeRepo repo) {
		this.repo = repo;
	}

	@GetMapping("/employees")
	public List<Employee> getAll() {
		return repo.findAll();
	}

	@GetMapping("/employees/{id}")
	public Optional<Employee> getOne(@PathVariable Long id) {
		return repo.findById(id);
	}

	@GetMapping("/employeesByIds")
	public List<Employee> getMany(@RequestParam List<Long> ids) {
		return repo.findAllById(ids);
	}

	@GetMapping("/employeesByPagination")
	public Page<Employee> employeesByPagination(
			@RequestParam(required = false, defaultValue = "0") int pageNo,
			@RequestParam(required = false, defaultValue = "3") int pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
		return repo.findAll(pageRequest);
	}

	@GetMapping("/employeesByPaginationWithSorting")
	public Page<Employee> employeesByPaginationWithSorting(
			@RequestParam(required = false, defaultValue = "0") int pageNo,
			@RequestParam(required = false, defaultValue = "3") int pageSize,
			@RequestParam(required = false, defaultValue = "id") String sortBy,
			@RequestParam(required = false, defaultValue = "ASC") String orderBy) {

		Sort sort = null;

		if ("DESC".equalsIgnoreCase(orderBy)) {
			sort = Sort.by(sortBy).descending();
		} else {
			sort = Sort.by(sortBy).ascending();
		}

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
		System.out.println("pageRequest = " + pageRequest);

		return repo.findAll(pageRequest);

	}

	@PostMapping("/employees")
	public List<Employee> create(@RequestBody List<Employee> employees) {
		return repo.saveAll(employees);
	}

}

interface EmployeeRepo extends JpaRepository<Employee, Long> {

}

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
class Employee {
	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private int age;
	private long salary;
}

@Configuration
class LoadData {
	@Bean
	CommandLineRunner loadIniitData(EmployeeRepo repo) {
		return args -> {
			List<Employee> employees = new ArrayList<>();
			employees.add(Employee.builder().name("John").age(10).salary(500).build());
			employees.add(Employee.builder().name("Pierre").age(20).salary(1500).build());
			employees.add(Employee.builder().name("Barbara").age(30).salary(2000).build());
			employees.add(Employee.builder().name("Tim").age(40).salary(2500).build());
			employees.add(Employee.builder().name("Bene").age(50).salary(3000).build());
			employees.add(Employee.builder().name("Axel").age(60).salary(3500).build());
			employees.add(Employee.builder().name("Sylvain").age(70).salary(4000).build());
			employees.add(Employee.builder().name("Arthur").age(80).salary(4500).build());

			repo.saveAll(employees);
			System.out.println("Data loaded. employees = " + employees);
		};
	}
}