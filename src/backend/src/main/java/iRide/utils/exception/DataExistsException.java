package iRide.utils.exception;

public class DataExistsException extends RuntimeException {
    public DataExistsException(String errorMessage) {
        super(errorMessage);
    }
}
