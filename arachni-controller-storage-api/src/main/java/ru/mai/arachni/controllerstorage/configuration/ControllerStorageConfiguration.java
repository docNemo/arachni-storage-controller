package ru.mai.arachni.controllerstorage.configuration;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mai.arachni.controllerstorage.service.MinioStorageService;
@Slf4j
@Configuration
public class ControllerStorageConfiguration {
    @Bean
    public MinioClient minioClient(
            @Value("${storage.minio.host}") String minioHost,
            @Value("${storage.minio.accessKey}") String accessKey,
            @Value("${storage.minio.secretKey}") String secretKey,
            @Value("${storage.minio.region}") String region
    ) {
        LOGGER.info(minioHost);
        LOGGER.info(accessKey);
        LOGGER.info(secretKey);
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
