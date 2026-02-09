package ru.coxey.library.exceptions;

public class EmailNotUniqueException extends RuntimeException {
    public EmailNotUniqueException(String message) {
        super(message);
    }
}
