package iRide.utils.exceptions;

public class CategoryExistsException extends RuntimeException {
    public CategoryExistsException(String errorMessage){
        super(errorMessage);
    }
}
