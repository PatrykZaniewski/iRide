package iRide.utils.exceptions;

public class EmailConfirmationException extends RuntimeException {
    public EmailConfirmationException(String errorMessage){
        super(errorMessage);
    }
}
