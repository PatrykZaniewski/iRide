package iRide.utils.exceptions;

public class DataExistsException extends RuntimeException {
    public DataExistsException(String errorMessage){
        super(errorMessage);
    }
}
