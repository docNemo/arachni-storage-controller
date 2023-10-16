package ru.mai.arachni.controllerstorage.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.mai.arachni.controllerstorage.exception.ArachniControllerStorageError;
import ru.mai.arachni.controllerstorage.exception.ArachniControllerStorageException;
import ru.mai.arachni.controllerstorage.exception.ArachniControllerStorageRepresentation;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalRuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ResponseEntity<ArachniControllerStorageRepresentation> handleRuntimeException(
            final RuntimeException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(ArachniControllerStorageError.UNKNOWN_ERROR.getStatusCode())
                .body(
                        new ArachniControllerStorageRepresentation(
                                ArachniControllerStorageError.UNKNOWN_ERROR.name(),
                                ArachniControllerStorageError.UNKNOWN_ERROR.getErrorMessage()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public @ResponseBody ResponseEntity<ArachniControllerStorageRepresentation>
    handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(ArachniControllerStorageError.INVALID_PARAMETER.getStatusCode())
                .body(
                        new ArachniControllerStorageRepresentation(
                                ArachniControllerStorageError.INVALID_PARAMETER.name(),
                                "%s: %s".formatted(
                                        ArachniControllerStorageError.INVALID_PARAMETER.getErrorMessage(),
                                        e.getMessage()
                                )
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ResponseEntity<ArachniControllerStorageRepresentation> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e
    ) {
        LOGGER.error("Handling: ", e);

        String issues = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(ArachniControllerStorageError.INVALID_JSON_PARAMETERS.getStatusCode())
                .body(
                        new ArachniControllerStorageRepresentation(
                                ArachniControllerStorageError.INVALID_JSON_PARAMETERS.name(),
                                "%s: %s".formatted(
                                        ArachniControllerStorageError.INVALID_JSON_PARAMETERS.getErrorMessage(),
                                        issues
                                )
                        )
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public @ResponseBody ResponseEntity<ArachniControllerStorageRepresentation> handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(ArachniControllerStorageError.INVALID_HTTP_MESSAGE.getStatusCode())
                .body(
                        new ArachniControllerStorageRepresentation(
                                ArachniControllerStorageError.INVALID_HTTP_MESSAGE.name(),
                                "%s: %s".formatted(
                                        ArachniControllerStorageError.INVALID_HTTP_MESSAGE.getErrorMessage(),
                                        e.getMessage()
                                )
                        )
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public @ResponseBody ResponseEntity<ArachniControllerStorageRepresentation> handleIllegalArgumentException(
            final IllegalArgumentException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(ArachniControllerStorageError.INVALID_PAGINATION_PARAMETER.getStatusCode())
                .body(
                        new ArachniControllerStorageRepresentation(
                                ArachniControllerStorageError.INVALID_PAGINATION_PARAMETER.name(),
                                "%s: %s".formatted(
                                        ArachniControllerStorageError.INVALID_PAGINATION_PARAMETER.getErrorMessage(),
                                        e.getMessage()
                                )
                        )
                );
    }

    @ExceptionHandler(ArachniControllerStorageException.class)
    public @ResponseBody ResponseEntity<ArachniControllerStorageRepresentation> handleArachniControllerStorageException(
            final ArachniControllerStorageException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(e.getError().getStatusCode())
                .body(
                        new ArachniControllerStorageRepresentation(
                                e.getError().name(),
                                e.getError().getErrorMessage()
                        )
                );
    }
}
