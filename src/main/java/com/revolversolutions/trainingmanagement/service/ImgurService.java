package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.entity.ImageMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImgurService {
    ImageMetadata uploadImage(MultipartFile file);
    boolean deleteImage(String deleteHash);
    List<ImageMetadata> uploadImages(List<MultipartFile> files);
}
