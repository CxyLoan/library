package ru.coxey.library.exceptions;

public class InvalidBookCopiesException extends RuntimeException {
    public InvalidBookCopiesException(String message) {
        super(message);
    }
}
