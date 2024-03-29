package hmy.webapp.exception;

import hmy.webapp.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * This class is used to handle exceptions that are thrown by the application.
 * It is annotated with @ControllerAdvice, which means that it will be used to handle exceptions across the whole application.
 * It contains a method annotated with @ExceptionHandler, which is used to handle a specific type of exception.
 * The method returns a ResponseEntity, which is used to return a JSON response with a status code.
 * @author Huang Miaoyan
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This method is used to handle exceptions of type BaseException.
     * It returns a ResponseEntity with a JSON response and a status code.
     * @param e the exception that was thrown
     * @return a ResponseEntity with a JSON response and a status code
     */
    @ExceptionHandler(BaseException.class) // 这个注解是用来设置需要捕获的异常类型
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 这个注解是用来设置返回的状态码
    public ResponseEntity<Response> handleBaseExceptionException(BaseException e) {
        // Handle the exception and return a JSON response
        String errorMessage = simplifyErrorMessage(e.getMessage());
        return new ResponseEntity<>(new Response(false, errorMessage), HttpStatus.BAD_REQUEST);
    }

    /**
     * This method is used to handle exceptions of type MethodArgumentNotValidException.
     * It returns a ResponseEntity with a JSON response and a status code.
     * @param e the exception that was thrown
     * @return a ResponseEntity with a JSON response and a status code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // Create a StringBuffer to store the error message
        StringBuffer errorMessage = new StringBuffer();
        // Get the error messages from the exception and append them to the error message
        e.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(", "));
        // Remove the last comma and space
        errorMessage.delete(errorMessage.length() - 2, errorMessage.length());
        return new ResponseEntity<>(new Response(false, errorMessage.toString()), HttpStatus.BAD_REQUEST);
    }
    /**
     * This method is used to simplify the error message of an exception.
     * It returns a simplified error message.
     * @param errorMessage the original error message
     * @return a simplified error message
     */
    private String simplifyErrorMessage(String errorMessage) {
        if (errorMessage.contains("Duplicate entry")) {
            return "User already exists";
        }
        // If the error message is not recognized, return the original error message
        return errorMessage;
    }
}
