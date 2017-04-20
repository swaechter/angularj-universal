package ch.swaechter.springular.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SpringularApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringularApplication.class, args);
    }
}
