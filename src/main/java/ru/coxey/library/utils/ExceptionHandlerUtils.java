package ru.coxey.library.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import ru.coxey.library.dto.ErrorDto;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
@Slf4j
public class ExceptionHandlerUtils {

    public static ResponseEntity<ErrorDto> getResponse(HttpStatus httpStatus, Exception e) {
        return ResponseEntity.status(httpStatus).body(getError(e));
    }

    public static ResponseEntity<ErrorDto> getResponse(HttpStatus httpStatus, List<ObjectError> errors) {
        return ResponseEntity.status(httpStatus).body(getErrors(errors));
    }

    private static ErrorDto getError(Exception exception) {
        return new ErrorDto(exception.getMessage());
    }

    private static ErrorDto getErrors(List<ObjectError> errors) {
        final var message = errors.stream()
                                  .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                  .collect(Collectors.joining("; "));
        return new ErrorDto(message);
    }
}
