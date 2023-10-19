package ru.mai.arachni.storagecontroller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {
    private String objectName;
    private String versionId;
}
