package ru.coxey.library.exceptions;

public class IncorrectAvailableCopiesCountException extends RuntimeException {
    public IncorrectAvailableCopiesCountException(String message) {
        super(message);
    }
}
