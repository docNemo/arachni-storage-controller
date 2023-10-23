package ru.mai.arachni.storagecontroller.handler;

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
import ru.mai.arachni.storagecontroller.exception.ArachniStorageControllerError;
import ru.mai.arachni.storagecontroller.exception.ArachniStorageControllerException;
import ru.mai.arachni.storagecontroller.exception.ArachniStorageControllerRepresentation;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalRuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ResponseEntity<ArachniStorageControllerRepresentation> handleRuntimeException(
            final RuntimeException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(ArachniStorageControllerError.UNKNOWN_ERROR.getStatusCode())
                .body(
                        new ArachniStorageControllerRepresentation(
                                ArachniStorageControllerError.UNKNOWN_ERROR.name(),
                                ArachniStorageControllerError.UNKNOWN_ERROR.getErrorMessage()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public @ResponseBody ResponseEntity<ArachniStorageControllerRepresentation>
    handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(ArachniStorageControllerError.INVALID_PARAMETER.getStatusCode())
                .body(
                        new ArachniStorageControllerRepresentation(
                                ArachniStorageControllerError.INVALID_PARAMETER.name(),
                                "%s: %s".formatted(
                                        ArachniStorageControllerError.INVALID_PARAMETER.getErrorMessage(),
                                        e.getMessage()
                                )
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ResponseEntity<ArachniStorageControllerRepresentation> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e
    ) {
        LOGGER.error("Handling: ", e);

        String issues = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(ArachniStorageControllerError.INVALID_JSON_PARAMETERS.getStatusCode())
                .body(
                        new ArachniStorageControllerRepresentation(
                                ArachniStorageControllerError.INVALID_JSON_PARAMETERS.name(),
                                "%s: %s".formatted(
                                        ArachniStorageControllerError.INVALID_JSON_PARAMETERS.getErrorMessage(),
                                        issues
                                )
                        )
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public @ResponseBody ResponseEntity<ArachniStorageControllerRepresentation> handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(ArachniStorageControllerError.INVALID_HTTP_MESSAGE.getStatusCode())
                .body(
                        new ArachniStorageControllerRepresentation(
                                ArachniStorageControllerError.INVALID_HTTP_MESSAGE.name(),
                                "%s: %s".formatted(
                                        ArachniStorageControllerError.INVALID_HTTP_MESSAGE.getErrorMessage(),
                                        e.getMessage()
                                )
                        )
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public @ResponseBody ResponseEntity<ArachniStorageControllerRepresentation> handleIllegalArgumentException(
            final IllegalArgumentException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(ArachniStorageControllerError.INVALID_PAGINATION_PARAMETER.getStatusCode())
                .body(
                        new ArachniStorageControllerRepresentation(
                                ArachniStorageControllerError.INVALID_PAGINATION_PARAMETER.name(),
                                "%s: %s".formatted(
                                        ArachniStorageControllerError.INVALID_PAGINATION_PARAMETER.getErrorMessage(),
                                        e.getMessage()
                                )
                        )
                );
    }

    @ExceptionHandler(ArachniStorageControllerException.class)
    public @ResponseBody ResponseEntity<ArachniStorageControllerRepresentation> handleArachniStorageControllerException(
            final ArachniStorageControllerException e
    ) {
        LOGGER.error("Handling: ", e);

        return ResponseEntity
                .status(e.getError().getStatusCode())
                .body(
                        new ArachniStorageControllerRepresentation(
                                e.getError().name(),
                                e.getError().getErrorMessage()
                        )
                );
    }
}
