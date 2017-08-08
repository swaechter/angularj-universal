package ch.swaechter.angularjuniversal.webserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

/**
 * This class provides the entry point for firing up the Spring Boot Application.
 *
 * @author Simon Wächter
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class WebApplication {

    /**
     * Start the application.
     *
     * @param args Application parameters which are passed to the Spring Boot application
     */
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
