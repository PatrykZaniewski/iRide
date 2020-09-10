package iRide.service.Login;

import iRide.model.Login;
import iRide.repository.LoginRepository;
import iRide.service.Student.model.StudentCreateInput;
import iRide.service.model.LoginCreateInput;
import iRide.utils.exceptions.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository){
        this.loginRepository = loginRepository;
    }

    public Login createLogin(LoginCreateInput loginCreateInput, String accountGroup) throws EmailExistsException {
        if(loginRepository.getLoginByEmail(loginCreateInput.getEmail()).isPresent()){
            throw new EmailExistsException(loginCreateInput.getEmail() + " is currently in usage.");
        }
        Login login = new Login(loginCreateInput, accountGroup);
        return loginRepository.save(login);
    }
}
