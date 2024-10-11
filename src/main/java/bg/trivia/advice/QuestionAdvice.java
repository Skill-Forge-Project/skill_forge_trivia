package bg.trivia.advice;

import bg.trivia.exceptions.InvalidInputException;
import bg.trivia.exceptions.TooManyRequestsException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class QuestionAdvice {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInputException(InvalidInputException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<Object> handleTooManyRequestsException(TooManyRequestsException ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(new ErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), ex.getMessage()));
    }

    public record ErrorResponse(int statusCode, String message) {
    }
}
