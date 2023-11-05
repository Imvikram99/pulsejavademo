package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/api")
public class BasicController {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        try {
            // Simulate some processing time
            Thread.sleep(1000);

            // Business logic
            String message = "Hello, world!";
            
            // Logging
            System.out.println("Request handled successfully. Sent: " + message);

            return ResponseEntity.ok(message);
        } catch (InterruptedException e) {
            // Error handling
            System.err.println("An error occurred: " + e.getMessage());
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
    @PostMapping ("/hello")
    public ResponseEntity<String> sayHellopost(@RequestBody String body) {
        try {
            // Simulate some processing time
            Thread.sleep(1000);

            // Business logic
            String message = "Hello, world!";

            // Logging
            System.out.println("Request handled successfully. Sent: " + message);

            return ResponseEntity.ok(message);
        } catch (InterruptedException e) {
            // Error handling
            System.err.println("An error occurred: " + e.getMessage());
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @GetMapping("/greet/{name}")
    public ResponseEntity<String> greetUser(
            @PathVariable String name,
            @RequestParam(value = "age", required = false) Integer age,
            @RequestParam(value = "country", required = false) String country) {
        try {
            // Simulate some processing time
            Thread.sleep(1000);

            // Business logic
            StringBuilder message = new StringBuilder("Hello, " + name + "!");

            if (age != null) {
                message.append(" You are ").append(age).append(" years old.");
            }
            if (country != null) {
                message.append(" You are from ").append(country).append(".");
            }

            // Logging
            System.out.println("Request handled successfully. Sent: " + message.toString());

            return ResponseEntity.ok(message.toString());
        } catch (InterruptedException e) {
            // Error handling
            System.err.println("An error occurred: " + e.getMessage());
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}
