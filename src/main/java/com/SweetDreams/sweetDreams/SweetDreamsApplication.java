package com.SweetDreams.sweetDreams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SweetDreamsApplication {

    @GetMapping(value = "")
    public String index() {
        return "Under Development";
    }

    public static void main(String[] args) {
        SpringApplication.run(SweetDreamsApplication.class, args);
    }



}
