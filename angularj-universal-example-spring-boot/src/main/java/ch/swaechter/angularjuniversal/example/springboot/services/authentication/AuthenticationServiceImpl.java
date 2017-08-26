package ch.swaechter.angularjuniversal.example.springboot.services.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

/**
 * This class is responsible for the authentication management.
 *
 * @author Simon Wächter
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * Suffix of the token.
     */
    private static final String HEADER_VALUE = "Bearer ";

    /**
     * Duration of the token.
     */
    private static final long TOKEN_DURATION = 1000 * 60 * 60 * 24 * 2;

    /**
     * Auto generated secret that is based on a generated key.
     */
    private final String secret;

    /**
     * Default constructor.
     */
    @Autowired
    public AuthenticationServiceImpl() {
        Key key = MacProvider.generateKey(SignatureAlgorithm.HS512);
        secret = Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * Try to log in the user and return a authentication token.
     *
     * @param user User used for the login
     * @return Optional authentication token
     */
    @Override
    public Optional<String> loginUser(User user) {
        try {
            if (user.getUsername().equals("user") && user.getPassword().equals("password")) {
                Date date = new Date(System.currentTimeMillis() + TOKEN_DURATION);
                String token = Jwts.builder().setSubject(user.getUsername()).setExpiration(date).signWith(SignatureAlgorithm.HS512, secret).compact();
                return Optional.of(token);
            } else {
                return Optional.empty();
            }
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    /**
     * Validate the token and return the username.
     *
     * @param token Token to be validated
     * @return Optional username
     */
    public Optional<String> validateToken(String token) {
        try {
            if (token == null || !token.startsWith(HEADER_VALUE)) {
                return Optional.empty();
            }

            token = token.replace(HEADER_VALUE, "");

            if (token.length() == 0) {
                return Optional.empty();
            }

            return Optional.of(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject());
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
