package ru.mai.arachni.storagecontroller.service;

import ru.mai.arachni.storagecontroller.dto.response.UploadResponse;

public interface StorageService {

    UploadResponse uploadObject(
            String objectName,
            String text,
            String bucket
    );
    String downloadObject(
            String objectName,
            String bucket
    );
}
