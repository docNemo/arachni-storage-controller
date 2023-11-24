package ru.mai.arachni.storagecontroller.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.arachni.storagecontroller.service.MinioStorageService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/models")
public class ClassifierModelController {
    private final MinioStorageService minioStorageService;
    @Value("${storage.minio.bucket.classifier}")
    private String bucket;

    @GetMapping("/{objectName}")
    public String downloadObject(@PathVariable String objectName) {
        return minioStorageService.downloadObject(
                objectName,
                bucket
        );
    }

}
