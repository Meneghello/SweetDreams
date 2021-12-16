package com.SweetDreams.sweetDreams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SweetDreamsApplication {

	@GetMapping(value = "/")
	public String index(){
		return String.format("Under development");
	}

	public static void main(String[] args) {
		SpringApplication.run(SweetDreamsApplication.class, args);
	}

}
