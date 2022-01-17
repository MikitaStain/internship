package by.innowise.internship.exceptions;

import by.innowise.internship.util.CalendarUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorObject> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorObject> handleNotDataFoundException(NoDataFoundException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.NO_CONTENT.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.OK);
    }

    @ExceptionHandler(NoCreateException.class)
    public ResponseEntity<ErrorObject> handleNoCreateException(NoCreateException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotParameterException.class)
    public ResponseEntity<ErrorObject> handleNotParameterException(NotParameterException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailInvalidException.class)
    public ResponseEntity<ErrorObject> handleEmailInvalidException(EmailInvalidException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorObject> handleDuplicate(DuplicateException ex) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(CalendarUtils.
                convertMilliSecondsToFormattedDate(System.currentTimeMillis()));

        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }
}
