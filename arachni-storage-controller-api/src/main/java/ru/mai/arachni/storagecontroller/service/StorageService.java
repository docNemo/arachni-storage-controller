package ru.mai.arachni.storagecontroller.service;

import ru.mai.arachni.storagecontroller.dto.response.UploadResponse;

public interface StorageService {

    UploadResponse uploadObject(
            String objectName,
            String text
    );
    String downloadObject(
            String objectName
    );
}
