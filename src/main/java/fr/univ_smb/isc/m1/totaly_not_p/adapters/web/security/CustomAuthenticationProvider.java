package fr.univ_smb.isc.m1.totaly_not_p.adapters.web.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.User;
import fr.univ_smb.isc.m1.totaly_not_p.infrastructure.persistence.UserRepository;

import java.util.ArrayList;
import java.util.Locale;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserRepository userRepository;

    public CustomAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();

        User user = userRepository.findByUsername(username);
        if (user != null
                && user.getUsername().equals(username)
                && new BCryptPasswordEncoder().matches(password, user.getPassword())
        ) {
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            SimpleGrantedAuthority authGiven = new SimpleGrantedAuthority(user.getRole().toUpperCase(Locale.ROOT));
            authorities.add(authGiven);
            return new UsernamePasswordAuthenticationToken
                    (username, password, authorities);
        } else {
            throw new
                    BadCredentialsException("External system authentication failed - Incorrect username or password");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}