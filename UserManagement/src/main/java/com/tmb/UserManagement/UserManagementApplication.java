package com.tmb.UserManagement;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@SpringBootApplication
public class UserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}
}

@RestController
class UserController {
	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/users")
	public List<User> all() {
		return userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public Optional<User> getUserById(@PathVariable Long id) {
		System.out.println("getUserById id = " + id);
		return userRepository.findById(id);
	}

	@PostMapping("/users")
	public User create(@RequestBody User user) {
		System.out.println("create user = " + user);
		return userRepository.save(user);
	}

	@PutMapping("/users/{id}")
	public Optional<User> update(@RequestBody User updateUserRequest, @PathVariable Long id) {
		System.out.println("updateUserRequest = " + updateUserRequest);
		Optional<User> isUserExist = userRepository.findById(id);
		if (isUserExist.isPresent()) {
			User dbUser = isUserExist.get();
			dbUser.setEmail(updateUserRequest.getEmail());
			dbUser.setName(updateUserRequest.getName());
			userRepository.save(dbUser);
		}
		return userRepository.findById(id);
	}

	@DeleteMapping("/users/{id}")
	public void delete(@PathVariable Long id) {
		userRepository.deleteById(id);
	}

}

interface UserRepository extends JpaRepository<User, Long> {
}

@Entity
class User {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	public User() {

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
