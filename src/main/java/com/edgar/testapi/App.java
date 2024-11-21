package com.edgar.testapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.out.println("Starting SpringBoot!");
        SpringApplication.run(App.class, args);
    }
}
