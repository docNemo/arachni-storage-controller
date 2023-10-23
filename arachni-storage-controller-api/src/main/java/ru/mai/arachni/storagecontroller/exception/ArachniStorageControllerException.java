package ru.mai.arachni.storagecontroller.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@Getter
public class ArachniStorageControllerException extends RuntimeException {
    private final ArachniStorageControllerError error;
    private final String extraInformation;

    @Override
    public String getMessage() {
        return "%s: %s: %s".formatted(error, error.getErrorMessage(), extraInformation);
    }
}
