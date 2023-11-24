package ru.mai.arachni.storagecontroller.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.utils.Base64;
import org.springframework.http.MediaType;
import ru.mai.arachni.storagecontroller.dto.response.UploadResponse;
import ru.mai.arachni.storagecontroller.exception.ArachniStorageControllerError;
import ru.mai.arachni.storagecontroller.exception.ArachniStorageControllerException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RequiredArgsConstructor
public class MinioStorageService implements StorageService {
    private final MinioClient minioClient;
//    private final String bucket;

    public UploadResponse uploadObject(
            String objectName,
            String text,
            String bucket
    ) {

        try (InputStream inputStream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))) {

            ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(MediaType.TEXT_PLAIN_VALUE)
                            .build()
            );

            LOGGER.info(objectWriteResponse.toString());

            return new UploadResponse(
                    objectWriteResponse.object(),
                    objectWriteResponse.versionId()
            );

        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new ArachniStorageControllerException(
                    ArachniStorageControllerError.UPLOAD_FAILED,
                    e.getMessage()
            );
        }
    }

    public String downloadObject(
            String objectName,
            String bucket
    ) {
        LOGGER.info("bucket: {}", bucket);
        try (
                InputStream stream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucket)
                                .object(objectName)
                                .build()
                )
        ) {
            return Base64.encodeBase64String(stream.readAllBytes());
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new ArachniStorageControllerException(
                    ArachniStorageControllerError.DOWNLOAD_FAILED,
                    e.getMessage()
            );
        }
    }

    public void deleteObject(
            String objectName,
            String bucket
    ) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new ArachniStorageControllerException(
                    ArachniStorageControllerError.DELETE_FAILED,
                    e.getMessage()
            );
        }
    }
}
