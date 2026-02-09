package ru.coxey.library.exceptions;

public class BookIssuedException extends RuntimeException {
    public BookIssuedException(String message) {
        super(message);
    }
}
