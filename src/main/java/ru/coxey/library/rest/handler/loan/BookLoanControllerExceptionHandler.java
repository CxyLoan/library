package ru.coxey.library.rest.handler.loan;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.coxey.library.dto.ErrorDto;
import ru.coxey.library.exceptions.*;
import ru.coxey.library.utils.ExceptionHandlerUtils;

@Component
@ControllerAdvice(annotations = BookLoanExceptionHandler.class)
public class BookLoanControllerExceptionHandler {

    @ExceptionHandler(exception = LoanNotFoundException.class)
    private ResponseEntity<ErrorDto> handleLoanNotFound(LoanNotFoundException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(exception = BookReturnedException.class)
    private ResponseEntity<ErrorDto> handleBookReturned(BookReturnedException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(exception = IncorrectAvailableCopiesCountException.class)
    private ResponseEntity<ErrorDto> handleIncorrectCopies(IncorrectAvailableCopiesCountException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorDto> handleSqlException(MethodArgumentNotValidException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.BAD_REQUEST, e.getAllErrors());
    }

    @ExceptionHandler(exception = BookNotFoundException.class)
    private ResponseEntity<ErrorDto> handleBookNotFound(BookNotFoundException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(exception = ReaderNotFoundException.class)
    private ResponseEntity<ErrorDto> handleReaderNotFound(ReaderNotFoundException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(exception = IncorrectDateException.class)
    private ResponseEntity<ErrorDto> handleIncorrectDate(IncorrectDateException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(exception = BookIssuedException.class)
    private ResponseEntity<ErrorDto> handleBookIssued(BookIssuedException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(exception = HttpMessageNotReadableException.class)
    private ResponseEntity<ErrorDto> handleException(HttpMessageNotReadableException e) {
        return ExceptionHandlerUtils.getResponse(HttpStatus.BAD_REQUEST, e);
    }
}
