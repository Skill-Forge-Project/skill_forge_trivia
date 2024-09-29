package bg.trivia.advice;

import bg.trivia.exceptions.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class QuestionAdvice {

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleInvalidInputException(InvalidInputException e) {
        return e.getMessage();
    }
}
