package iRide.config;

import iRide.service.User.UserService;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public AuthenticationUserDetailsService (UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws NotFoundException {
        return new AuthenticationUserDetails(userService.getUserByEmail(email));
    }
}
