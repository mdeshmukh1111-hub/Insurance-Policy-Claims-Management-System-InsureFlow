package com.insureflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InsureFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsureFlowApplication.class, args);
        System.out.println("\n========================================================");
        System.out.println(" InsureFlow Insurance System Started Successfully!");
        System.out.println(" Access Application UI: http://localhost:8080/");
        System.out.println("========================================================\n");
    }
}
