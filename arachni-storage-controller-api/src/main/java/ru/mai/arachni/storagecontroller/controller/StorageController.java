package ru.mai.arachni.storagecontroller.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.arachni.storagecontroller.dto.request.UploadRequest;
import ru.mai.arachni.storagecontroller.dto.response.UploadResponse;
import ru.mai.arachni.storagecontroller.service.MinioStorageService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/objects")
public class StorageController {
    private final MinioStorageService minioStorageService;

    @PostMapping
    public UploadResponse uploadObject(
            @RequestBody UploadRequest uploadRequest
    ) {
        return minioStorageService.uploadObject(
                uploadRequest.getObjectName(),
                uploadRequest.getText()
        );
    }

    @GetMapping("/{objectName}")
    public String downloadObject(@PathVariable String objectName) {
        return minioStorageService.downloadObject(objectName);
    }

    @DeleteMapping("/{objectName}")
    public void deleteObject(@PathVariable String objectName) {
        minioStorageService.deleteObject(objectName);
    }

}
