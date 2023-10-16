package ru.mai.arachni.controllerstorage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadRequest {
    private String objectName;
    private String text;
}
