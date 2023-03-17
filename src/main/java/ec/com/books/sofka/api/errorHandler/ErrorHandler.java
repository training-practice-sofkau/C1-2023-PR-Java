package ec.com.books.sofka.api.errorHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity <String> handleException(WebExchangeBindException e) {

        String error = "Some of the fields of isbn, title or year are null";

        return ResponseEntity.badRequest().body(error);
    }
}
