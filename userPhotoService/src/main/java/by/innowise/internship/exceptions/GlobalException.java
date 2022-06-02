package by.innowise.internship.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<ErrorObject> handleResourceNotFoundException(EmptyFileException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileNotAvailableException.class)
    public ResponseEntity<ErrorObject> handleNotDataFoundException(FileNotAvailableException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NO_CONTENT.value());
        errorObject.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorObject, HttpStatus.OK);
    }

    @ExceptionHandler(NotAvailableContentException.class)
    public ResponseEntity<ErrorObject> handleNoCreateException(NotAvailableContentException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

}
