package com.tmb.BuilkProcessing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootApplication
public class BuilkProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuilkProcessingApplication.class, args);
	}

}

@RestController
class Controller {
	CustomerRepo repo;

	public Controller(CustomerRepo repo) {
		this.repo = repo;
	}

	@PostMapping("/bulkcustomer")
	public Customer createOne(@RequestBody Customer customer) {
		return repo.save(customer);
	}

	@PostMapping("/bulkCreatecustomers")
	public List<Customer> createMany(@RequestBody List<Customer> customers) {
		return repo.saveAll(customers);
	}

	@GetMapping("/bulkcustomers/{id}")
	public Customer one(@PathVariable Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer id: [" + id + "] is not found"));
	}

	@GetMapping("/bulkGetCustomersList")
	public List<Optional<Customer>> oneToLimit(@RequestParam List<Long> ids) {
		System.out.println("ids = " + ids);
		List<Optional<Customer>> res = new ArrayList<>();
		for (var id : ids) {
			res.add(repo.findById(id));
		}
		return res;
	}

	@GetMapping("/bulkcustomers")
	public List<Customer> all() {
		List<Customer> all = repo.findAll();
		System.out.println("all customers = " + all);
		return repo.findAll();
	}
}

interface CustomerRepo extends JpaRepository<Customer, Long> {
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
@Entity
class Customer {
	@Id
	@GeneratedValue
	Long id;

	String name;
}

@Configuration
class LoadDatabase {
	@Bean
	CommandLineRunner loadBD(CustomerRepo repo) {
		return args -> {
			List<Customer> customers = LongStream.rangeClosed(100, 150)
					.mapToObj(id -> new Customer(id, "CustomerName_" + id))
					.toList();
			System.out.println("list = " + customers);
			// repo.saveAll(customers);
			System.out.println("loadBD done");
		};
	}
}

class CustomerNotFoundException extends RuntimeException {
	public CustomerNotFoundException(String message) {
		super(message);
	}
}

@ControllerAdvice
class MyExceptionHandling {
	@ResponseBody
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String getMessage(RuntimeException exception) {
		return exception.getMessage();
	}
}