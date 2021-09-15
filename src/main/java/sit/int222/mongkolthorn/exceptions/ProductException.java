package sit.int222.mongkolthorn.exceptions;

public class ProductException extends RuntimeException{
    ExceptionResponse.ERROR_CODE errorCode;

    public ProductException(ExceptionResponse.ERROR_CODE errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ExceptionResponse.ERROR_CODE getErrorCode() {
        return errorCode;
    }
}
