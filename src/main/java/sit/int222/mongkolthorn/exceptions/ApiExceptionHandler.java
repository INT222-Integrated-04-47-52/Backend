package sit.int222.mongkolthorn.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException,  badRequest);
    }

    @ExceptionHandler(value = {ApiRequestExceptionUnauthorized.class})
    public ResponseEntity<Object> handleApiRequestExceptionUnauthorized(ApiRequestExceptionUnauthorized e) {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                e.getMessage(),
                unauthorized,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException,  unauthorized);
    }

    @ExceptionHandler(value = {ApiRequestExceptionNotFound.class})
    public ResponseEntity<Object> handleApiRequestExceptionNotfound(ApiRequestExceptionNotFound e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException,  notFound);
    }
}
