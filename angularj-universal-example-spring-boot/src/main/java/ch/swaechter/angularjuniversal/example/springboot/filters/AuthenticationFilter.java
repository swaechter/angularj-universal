package ch.swaechter.angularjuniversal.example.springboot.filters;


import ch.swaechter.angularjuniversal.example.springboot.services.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * This class is responsible for checking all incoming requests and accept or reject them based on the access token.
 *
 * @author Simon Wächter
 */
public class AuthenticationFilter extends GenericFilterBean {

    /**
     * Header name of the token.
     */
    public static final String HEADER_KEY = "Authorization";

    /**
     * Authentication serviced used for the token validation.
     */
    private final AuthenticationService authenticationservice;

    /**
     * Constructor with the authentication service.
     *
     * @param authenticationservice Authentication service
     */
    @Autowired
    public AuthenticationFilter(AuthenticationService authenticationservice) {
        this.authenticationservice = authenticationservice;
    }

    /**
     * Check the incoming request. If the access token is parsable, a user authentication will be created.
     *
     * @param request  Incoming request
     * @param response Outgoing response
     * @param chain    Filter chain
     * @throws IOException      Exception in case of an internal problem
     * @throws ServletException Exception in case of an internal problem
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httprequest = (HttpServletRequest) request;
        Optional<String> username = authenticationservice.validateToken(httprequest.getHeader(HEADER_KEY));
        if (username.isPresent()) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username.get(), null, null);
            SecurityContextHolder.getContext().setAuthentication(token);
            chain.doFilter(request, response);
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
            chain.doFilter(request, response);
        }
    }
}
