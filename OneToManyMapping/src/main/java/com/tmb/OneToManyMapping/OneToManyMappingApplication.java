package com.tmb.OneToManyMapping;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@SpringBootApplication
public class OneToManyMappingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneToManyMappingApplication.class, args);
	}
}

@RestController
class AuthorController {
	AuthorRepo repo;

	public AuthorController(AuthorRepo repo) {
		this.repo = repo;
	}

	@GetMapping("/authors")
	public List<Author> all() {
		return repo.findAll();
	}

	@PostMapping("/authors")
	public Author create(@RequestBody Author author) {
		return repo.save(author);
	}

	@GetMapping("/authors/{id}")
	public Optional<Author> one(@PathVariable Long id) {
		return repo.findById(id);
	}

	@DeleteMapping("/authors/{id}")
	public void delete(@PathVariable Long id) {
		repo.deleteById(id);
	}
}

interface AuthorRepo extends JpaRepository<Author, Long> {
}

@Getter
@Setter
@Entity
class Author {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Book> books;
}

@Getter
@Setter
@Entity
class Book {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String type;
}
