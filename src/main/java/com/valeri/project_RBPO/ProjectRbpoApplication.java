package com.valeri.project_RBPO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.valeri.project_RBPO", "simple"})
public class ProjectRbpoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectRbpoApplication.class, args);
    }
}