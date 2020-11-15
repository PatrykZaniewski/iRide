package iRide.utils.exception;

public class EmailConfirmationException extends RuntimeException {
    public EmailConfirmationException(String errorMessage) {
        super(errorMessage);
    }
}
