package ru.mai.arachni.controllerstorage.service;

import ru.mai.arachni.controllerstorage.dto.response.UploadResponse;

public interface StorageService {

    UploadResponse uploadObject(
            String objectName,
            String text
    );
    String downloadObject(
            String objectName
    );
}
