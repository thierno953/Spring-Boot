package com.tbarry.springbootlombokapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootLombokAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootLombokAppApplication.class, args);

		Student student = new Student();
		student.setId(100);
		student.setFirstName("John");
		student.setLastName("Doe");

		System.out.println(student.getId());
		System.out.println(student.getFirstName());
		System.out.println(student.getLastName());
	}

}
