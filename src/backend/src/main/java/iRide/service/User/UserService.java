package iRide.service.User;

import iRide.model.User;
import iRide.repository.UserRepository;
import iRide.service.User.model.UserCreateInput;
import iRide.utils.exception.DataExistsException;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserCreateInput userCreateInput, String accountGroup) throws DataExistsException {
        if (userRepository.getUserByEmail(userCreateInput.getEmail()).isPresent()) {
            throw new DataExistsException("Email: " + userCreateInput.getEmail() + " is currently in usage.");
        }
        User user = new User(userCreateInput, accountGroup);
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) throws NotFoundException {
        Optional<User> test = userRepository.getUserByEmail(email);
        if (userRepository.getUserByEmail(email).isPresent()) {
            return userRepository.getUserByEmail(email).get();
        }
        throw new NotFoundException("Typed email and/or password is not correct.");
    }
}
