package ru.mai.arachni.controllerstorage.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import ru.mai.arachni.controllerstorage.dto.response.UploadResponse;
import ru.mai.arachni.controllerstorage.exception.ArachniControllerStorageError;
import ru.mai.arachni.controllerstorage.exception.ArachniControllerStorageException;

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
    private final String bucket;

    public UploadResponse uploadObject(
            String objectName,
            String text
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
            throw new ArachniControllerStorageException(
                    ArachniControllerStorageError.UPLOAD_FAILED,
                    e.getMessage()
            );
        }
    }

    public String downloadObject(
            String objectName
    ) {
        try (
                InputStream stream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucket)
                                .object(objectName)
                                .build()
                )
        ) {
            return new String(stream.readAllBytes());
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new ArachniControllerStorageException(
                    ArachniControllerStorageError.DOWNLOAD_FAILED,
                    e.getMessage()
            );
        }
    }
}
