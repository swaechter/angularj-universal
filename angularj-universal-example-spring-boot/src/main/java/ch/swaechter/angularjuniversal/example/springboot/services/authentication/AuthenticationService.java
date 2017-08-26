package ch.swaechter.angularjuniversal.example.springboot.services.authentication;

import java.util.Optional;

/**
 * This class is responsible for the authentication management.
 *
 * @author Simon Wächter
 */
public interface AuthenticationService {

    /**
     * Try to log in the user and return a authentication token.
     *
     * @param user User used for the login
     * @return Optional authentication token
     */
    Optional<String> loginUser(User user);

    /**
     * Validate the token and return the username.
     *
     * @param token Token to be validated
     * @return Optional username
     */
    Optional<String> validateToken(String token);
}
