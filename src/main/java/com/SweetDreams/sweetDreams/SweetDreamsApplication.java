package com.SweetDreams.sweetDreams;

import com.SweetDreams.sweetDreams.Models.Email;
import com.SweetDreams.sweetDreams.Services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@SpringBootApplication
@EnableScheduling
public class SweetDreamsApplication {



    @GetMapping(value = "")
    public String index() {
        return "Under Development";
    }

    public static void main(String[] args) {
        SpringApplication.run(SweetDreamsApplication.class, args);
    }

}


