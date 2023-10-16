package ru.mai.arachni.controllerstorage.configuration;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mai.arachni.controllerstorage.service.MinioStorageService;

@Configuration
public class ControllerStorageConfiguration {
    @Bean
    public MinioClient minioClient(
            @Value("${storage.minio.host}") String minioHost,
            @Value("${storage.minio.accessKey}") String accessKey,
            @Value("${storage.minio.secretKey}") String secretKey,
            @Value("${storage.minio.region}") String region
    ) {
        return MinioClient.builder()
                .endpoint(minioHost)
                .credentials(accessKey, secretKey)
                .region(region)
                .build();
    }

    @Bean
    public MinioStorageService minioStorageService(
            MinioClient minioClient,
            @Value("${storage.minio.bucket}") String bucket
    ) {
        return new MinioStorageService(
                minioClient,
                bucket
        );
    }
}
