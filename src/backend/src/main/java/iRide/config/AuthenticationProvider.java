package iRide.config;

import iRide.model.User;
import iRide.service.User.UserService;
import iRide.utils.exceptions.EmailConfirmationException;
import iRide.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
        try {
            User user = userService.getUserByEmail(email);
            if (user.getStatus().equals("PENDING_CONFIRMATION")){
                throw new EmailConfirmationException("Address email need to be confirmed before logging in.");
            }
            if (!user.getPassword().equals(usernamePasswordAuthenticationToken.getCredentials().toString())){
                throw new NotFoundException("Typed email and/or password is not correct.");
            }


            //FIXME passwordEncoder.matches(usernamePasswordAuthenticationToken.getCredentials().toString(), user.getPassword());
            AuthenticationUserDetails userDetails = new AuthenticationUserDetails(user);
            return userDetails;
        } catch (NotFoundException e){
            throw e;
        }
    }
}
