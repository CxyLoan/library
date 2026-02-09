package ru.coxey.library.exceptions;

public class PhoneNumberNotUniqueException extends RuntimeException {
    public PhoneNumberNotUniqueException(String message) {
        super(message);
    }
}
