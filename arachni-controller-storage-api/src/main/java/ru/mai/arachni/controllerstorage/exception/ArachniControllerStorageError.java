package ru.mai.arachni.controllerstorage.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ArachniControllerStorageError {
    UNKNOWN_ERROR(
            "Неизвестная ошибка",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_PARAMETER(
            "Получен некорректный параметр",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_HTTP_MESSAGE(
            "Ошибка в HTTP-сообщении",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_JSON_PARAMETERS(
            "Невозможно принять json-файл с пустыми или NULL параметрами",
            HttpStatus.BAD_REQUEST
    ),
    INVALID_PAGINATION_PARAMETER(
            "Некорректные параметры пагинации",
            HttpStatus.BAD_REQUEST
    ),
    UPLOAD_FAILED(
            "Не удалось загрузить объект",
            HttpStatus.BAD_REQUEST
    ),
    DOWNLOAD_FAILED(
            "Не удалось скачать объект",
            HttpStatus.BAD_REQUEST
    );

    private final String errorMessage;
    private final HttpStatus statusCode;
}
