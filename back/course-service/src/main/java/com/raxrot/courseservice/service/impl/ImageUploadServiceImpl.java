package com.raxrot.courseservice.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.raxrot.courseservice.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageUploadServiceImpl implements ImageUploadService {

    private final Cloudinary cloudinary;

    @Override
    public UploadResult upload(MultipartFile file, String folder) {
        try {
            Map<String, Object> res = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "image"
                    )
            );

            String url = (String) res.getOrDefault("secure_url", res.get("url"));
            String publicId = (String) res.get("public_id");

            return new UploadResult(url, publicId);

        } catch (Exception e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }


    @Override
    public void delete(String publicId) {
        if (publicId == null || publicId.isBlank()) return;
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            // ignore or log
        }
    }
}
