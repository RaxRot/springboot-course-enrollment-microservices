package com.raxrot.courseservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    UploadResult upload(MultipartFile file, String folder);

    void delete(String publicId);

    @Data
    @AllArgsConstructor
    class UploadResult {
        private String url;
        private String publicId;
    }
}
