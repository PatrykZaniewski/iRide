package iRide.config;

import iRide.model.User;
import iRide.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String email, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        User user = userService.getUserByEmail(email);
        if (user.getStatus().equals("PENDING_CONFIRMATION")) {
            throw new AccountStatusException("Address email need to be confirmed before logging in.") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String test = bCryptPasswordEncoder.encode(usernamePasswordAuthenticationToken.getCredentials().toString());
        if (!user.getPassword().equals(usernamePasswordAuthenticationToken.getCredentials().toString())) {
            throw new BadCredentialsException("Typed email and/or password is not correct.");
        }


        //FIXME passwordEncoder.matches(usernamePasswordAuthenticationToken.getCredentials().toString(), user.getPassword());
        AuthenticationUserDetails userDetails = new AuthenticationUserDetails(user);
        return userDetails;
    }
}
