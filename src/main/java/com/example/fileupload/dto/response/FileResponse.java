package com.example.fileupload.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class FileResponse {
    private long id;
    private String fileName;
    private String filePath;
    private String fileExtension;
    private double size;
}
