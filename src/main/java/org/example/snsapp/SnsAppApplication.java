package org.example.snsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication
public class SnsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnsAppApplication.class, args);
    }

}
