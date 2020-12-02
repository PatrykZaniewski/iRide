package iRide.utils.exception;

import org.springframework.security.authentication.AccountStatusException;

public class AccountBlocked extends AccountStatusException {
    public AccountBlocked(String msg) {
        super(msg);
    }
}
