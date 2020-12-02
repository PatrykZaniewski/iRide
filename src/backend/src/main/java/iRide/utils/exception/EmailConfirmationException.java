package iRide.utils.exception;

import org.springframework.security.authentication.AccountStatusException;

public class EmailConfirmationException extends AccountStatusException {
    public EmailConfirmationException(String errorMessage) {
        super(errorMessage);
    }
}
