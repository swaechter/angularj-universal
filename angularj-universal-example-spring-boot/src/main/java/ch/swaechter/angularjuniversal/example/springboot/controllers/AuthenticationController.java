package ch.swaechter.angularjuniversal.example.springboot.controllers;

import ch.swaechter.angularjuniversal.example.springboot.filters.AuthenticationFilter;
import ch.swaechter.angularjuniversal.example.springboot.services.authentication.AuthenticationService;
import ch.swaechter.angularjuniversal.example.springboot.services.authentication.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * This class is responsible for logging in the user and providing an authentication token.
 *
 * @author Simon Wächter
 */
@RestController
@RequestMapping("/")
public class AuthenticationController {

    /**
     * Authentication serviced used for the user authentication.
     */
    private final AuthenticationService authenticationservice;

    /**
     * Constructor with the authentication service used for the user authentication.
     *
     * @param authenticationservice Authentication service used for the user authentication
     */
    @Autowired
    public AuthenticationController(AuthenticationService authenticationservice) {
        this.authenticationservice = authenticationservice;
    }

    /**
     * Login a user and send it back.
     *
     * @param user     Given user account
     * @param response Given response that will contain the toke if the login was successful
     * @return Empty response
     */
    @PostMapping("/publicapi/login")
    public ResponseEntity<User> loginUser(@Validated @RequestBody User user, HttpServletResponse response) {
        Optional<String> token = authenticationservice.loginUser(user);
        if (token.isPresent()) {
            response.setHeader(AuthenticationFilter.HEADER_KEY, token.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
