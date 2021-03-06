package com.antonkirvel.maze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class MazeApplication {

    @RequestMapping("/")
    String index() {
        return "index";
    }

    public static void main(String[] args) {
        SpringApplication.run(MazeApplication.class, args);
    }

}
