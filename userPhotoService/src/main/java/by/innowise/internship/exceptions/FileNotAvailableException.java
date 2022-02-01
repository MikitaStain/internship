package by.innowise.internship.exceptions;

import java.io.IOException;

public class FileNotAvailableException extends RuntimeException {

    public FileNotAvailableException(String message) {
        super(message);
    }
}
