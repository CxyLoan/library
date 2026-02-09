package ru.coxey.library.exceptions;

public class ReaderNotFoundException extends RuntimeException {
    public ReaderNotFoundException(String message) {
        super(message);
    }
}
