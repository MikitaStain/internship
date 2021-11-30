package by.innowise.internship.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class Main {

    @GetMapping("/hello")
    public ResponseEntity<?> getHello() {
        return new ResponseEntity("hello", HttpStatus.OK);
    }
}
