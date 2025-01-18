package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.ImageMetadataDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageMetadataService {
    ImageMetadataDTO uploadImageMetadata(MultipartFile file);
    void deleteImage(String imageMetadataId);
    List<ImageMetadataDTO> uploadImageMultiple(List<MultipartFile> files);
}
