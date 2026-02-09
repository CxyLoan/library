package ru.coxey.library.exceptions;

public class IsbnNotUniqueException extends RuntimeException {
    public IsbnNotUniqueException(String message) {
        super(message);
    }
}
