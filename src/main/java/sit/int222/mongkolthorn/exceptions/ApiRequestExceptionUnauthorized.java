package sit.int222.mongkolthorn.exceptions;

public class ApiRequestExceptionUnauthorized extends RuntimeException{
    public ApiRequestExceptionUnauthorized(String message){
        super(message);
    }

    public ApiRequestExceptionUnauthorized(String message, Throwable cause) {
        super(message, cause);
    }
}
