package ru.mai.arachni.storagecontroller.exception;

import lombok.Value;

@Value
public class ArachniStorageControllerRepresentation {
    String errorCode;
    String message;
}
