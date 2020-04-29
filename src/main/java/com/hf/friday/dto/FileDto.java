package com.hf.friday.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDto {
    private MultipartFile file;
    private Integer userId;
}
