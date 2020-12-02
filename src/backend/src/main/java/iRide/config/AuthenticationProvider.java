package iRide.config;

import iRide.model.User;
import iRide.service.User.UserService;
import iRide.utils.exception.AccountBlocked;
import iRide.utils.exception.EmailConfirmationException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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
    protected UserDetails retrieveUser(String email, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException, BadCredentialsException {
        User user = null;
        try {
            user = userService.getUserByEmail(email);
        }
        catch (NotFoundException e){
            throw new BadCredentialsException("Bad credentials");
        }
        if (user.getStatus().equals("PENDING_CONFIRMATION")) {
            throw new EmailConfirmationException("Email need to be confirmed");

        }
        if (user.getStatus().equals("BLOCKED")) {
            throw new AccountBlocked("Account blocked");
        }

        if (!passwordEncoder.matches(usernamePasswordAuthenticationToken.getCredentials().toString(), user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        return new AuthenticationUserDetails(user);
    }
}
