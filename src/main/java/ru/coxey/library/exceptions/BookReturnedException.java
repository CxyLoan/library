package ru.coxey.library.exceptions;

public class BookReturnedException extends RuntimeException {
    public BookReturnedException(String message) {
        super(message);
    }
}
