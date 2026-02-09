package ru.coxey.library.rest.handler.reader;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.coxey.library.dto.ErrorDto;
import ru.coxey.library.exceptions.EmailNotUniqueException;
import ru.coxey.library.exceptions.PhoneNumberNotUniqueException;
import ru.coxey.library.exceptions.ReaderNotFoundException;
import ru.coxey.library.utils.ExceptionHandlerUtils;

@Component
@ControllerAdvice(annotations = ReaderExceptionHandler.class)
public class ReaderControllerExceptionHandler {

    @ExceptionHandler(exception = ReaderNotFoundException.class)
    private ResponseEntity<ErrorDto> handleReaderNotFound(ReaderNotFoundException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(exception = EmailNotUniqueException.class)
    private ResponseEntity<ErrorDto> handlerEmailNotUnique(EmailNotUniqueException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(exception = PhoneNumberNotUniqueException.class)
    private ResponseEntity<ErrorDto> handlerPhoneNumberNotUnique(PhoneNumberNotUniqueException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorDto> handleSqlException(MethodArgumentNotValidException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.BAD_REQUEST, e.getAllErrors());
    }

    @ExceptionHandler(exception = HttpMessageNotReadableException.class)
    private ResponseEntity<ErrorDto> handleException(HttpMessageNotReadableException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.BAD_REQUEST, e);
    }
}
