package com.gl.FirstWebApp;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FirstWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstWebAppApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello() {
		String output = "<b><i> <h1>Hello World !! at time : " + LocalDateTime.now() + "</h1></i></b>";
		System.out.println("----------- output on my server = " + output);
		return output;
	}

	@GetMapping("/")
	public String first() {
		String output = "<b><i> <h1>Welcome !! at time : " + LocalDateTime.now() + "</h1></i></b>";
		System.out.println("----------- output on my server = " + output);
		return output;
	}

	@GetMapping("/greet")
	public String greet(@RequestParam(value = "name", defaultValue = "in App") String name) {
		String output = "<b><i> <h1>Welcome, " + name + " !! at time : " + LocalDateTime.now() + "</h1></i></b>";
		System.out.println("----------- output on my server = " + output);
		return output;
	}

	@GetMapping("/test")
	public void test() {
		String output = "Test my server: " + LocalDateTime.now();
		System.out.println("----------- output on my server = " + output);
	}
}
