package iRide.utils.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String errorMessage){
        super(errorMessage);
    }
}
