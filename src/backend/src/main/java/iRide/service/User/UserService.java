package iRide.service.User;

import iRide.model.User;
import iRide.repository.UserRepository;
import iRide.service.model.LoginCreateInput;
import iRide.utils.exceptions.DataExistsException;
import iRide.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createLogin(LoginCreateInput loginCreateInput, String accountGroup) throws DataExistsException {
        if(userRepository.getUserByEmail(loginCreateInput.getEmail()).isPresent()){
            throw new DataExistsException("Email: " + loginCreateInput.getEmail() + " is currently in usage.");
        }
        User user = new User(loginCreateInput, accountGroup);
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) throws NotFoundException {
        if (userRepository.getUserByEmail(email).isPresent()){
            return userRepository.getUserByEmail(email).get();
        }
        throw new NotFoundException("Typed email and/or password is not correct.");
    }
}
