package ru.mai.arachni.controllerstorage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.arachni.controllerstorage.dto.request.UploadRequest;
import ru.mai.arachni.controllerstorage.dto.response.UploadResponse;
import ru.mai.arachni.controllerstorage.service.MinioStorageService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StorageController {
    private final MinioStorageService minioStorageService;

    @PostMapping("/upload")
    public UploadResponse uploadObject(
            @RequestBody UploadRequest uploadRequest
    ) {
        return minioStorageService.uploadObject(
                uploadRequest.getObjectName(),
                uploadRequest.getText()
        );
    }

    @GetMapping("/download")
    public String downloadObject(@RequestParam String objectName) {
        return minioStorageService.downloadObject(objectName);
    }

}
