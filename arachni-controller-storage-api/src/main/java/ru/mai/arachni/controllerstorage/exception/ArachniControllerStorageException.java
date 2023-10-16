package ru.mai.arachni.controllerstorage.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@Getter
public class ArachniControllerStorageException extends RuntimeException {
    private final ArachniControllerStorageError error;
    private final String extraInformation;

    @Override
    public String getMessage() {
        return "%s: %s: %s".formatted(error, error.getErrorMessage(), extraInformation);
    }
}
