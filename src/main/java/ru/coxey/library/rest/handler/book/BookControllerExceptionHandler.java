package ru.coxey.library.rest.handler.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.coxey.library.dto.ErrorDto;
import ru.coxey.library.exceptions.BookNotFoundException;
import ru.coxey.library.exceptions.InvalidBookCopiesException;
import ru.coxey.library.exceptions.IsbnNotUniqueException;
import ru.coxey.library.utils.ExceptionHandlerUtils;

@Component
@ControllerAdvice(annotations = BookExceptionHandler.class)
public class BookControllerExceptionHandler {

    @ExceptionHandler(exception = BookNotFoundException.class)
    private ResponseEntity<ErrorDto> handleBookNotFound(BookNotFoundException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(exception = IsbnNotUniqueException.class)
    private ResponseEntity<ErrorDto> handleIsbnNotUniqueException(IsbnNotUniqueException e) {
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
    @ExceptionHandler(exception = MethodArgumentTypeMismatchException.class)
    private ResponseEntity<ErrorDto> handleException(MethodArgumentTypeMismatchException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(exception = InvalidBookCopiesException.class)
    private ResponseEntity<ErrorDto> handleException(InvalidBookCopiesException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.BAD_REQUEST, e);
    }
}
