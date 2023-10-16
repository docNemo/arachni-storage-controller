package ru.mai.arachni.controllerstorage.exception;

import lombok.Value;

@Value
public class ArachniControllerStorageRepresentation {
    String errorCode;
    String message;
}
