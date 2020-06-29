package com.example.demo.notification;

import com.example.demo.security.services.AccountDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class WebSocketAuthenticatorService {

    @Autowired
    private AccountDetailsServiceImpl accountDetailsService;

    // This method MUST return a UsernamePasswordAuthenticationToken instance, the spring security chain is testing it with 'instanceof' later on. So don't use a subclass of it or any other class
    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String  username) throws AuthenticationException {
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
        }

        UserDetails userDetails  = accountDetailsService.loadUserByUsername(username);

        // Add your own logic for retrieving user in fetchUserFromDb()
        if (userDetails == null) {
            throw new BadCredentialsException("Bad credentials for user " + username);
        }
        // null credentials, we do not pass the password along
        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                userDetails.getAuthorities() // MUST provide at least one role
        );
    }
}
