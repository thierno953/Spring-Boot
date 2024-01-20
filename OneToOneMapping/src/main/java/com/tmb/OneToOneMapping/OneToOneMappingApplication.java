package com.tmb.OneToOneMapping;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class OneToOneMappingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneToOneMappingApplication.class, args);
	}

}

@RestController
class UserController {
	private final UserRepo repo;

	public UserController(UserRepo repo) {
		this.repo = repo;
	}

	@GetMapping("/users")
	public List<User> all() {
		return repo.findAll();
	}

	@PostMapping("/users")
	public User create(@RequestBody User user) {
		return repo.save(user);
	}

	@DeleteMapping("/users/{id}")
	public void delete(@PathVariable Long id) {
		repo.deleteById(id);
	}

}

interface UserRepo extends JpaRepository<User, Long> {
};

interface AddressRepo extends JpaRepository<Address, Long> {
};

@Getter
@Setter
@ToString
@Entity
class User {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
}

@Getter
@Setter
@ToString
@Entity
class Address {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String city;

	@Column(nullable = false, unique = true)
	private String country;
}