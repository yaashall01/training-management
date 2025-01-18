package com.revolversolutions.trainingmanagement.serviceImpl;

import com.revolversolutions.trainingmanagement.dto.ImageMetadataDTO;
import com.revolversolutions.trainingmanagement.entity.ImageMetadata;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
import com.revolversolutions.trainingmanagement.mapper.ImageMetadataDTOMapper;
import com.revolversolutions.trainingmanagement.repository.ImageMetadataRepository;
import com.revolversolutions.trainingmanagement.service.ImageMetadataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ImageMetadataServiceImpl implements ImageMetadataService {

    private final ImageMetadataDTOMapper imageMetadataDTOMapper;
    private final ImageMetadataRepository imageMetadataRepository;
    private final ImgurServiceImpl imgurService;

    public ImageMetadataServiceImpl(
            ImageMetadataDTOMapper imageMetadataDTOMapper,
            ImageMetadataRepository imageMetadataRepository, ImgurServiceImpl imgurService) {
        this.imageMetadataDTOMapper = imageMetadataDTOMapper;
        this.imageMetadataRepository = imageMetadataRepository;
        this.imgurService = imgurService;
    }

    @Override
    @Transactional
    public ImageMetadataDTO uploadImageMetadata(MultipartFile file) {
        ImageMetadata img = imgurService.uploadImage(file);
        return imageMetadataDTOMapper.toDto(img);
    }

    @Override
    @Transactional
    public void deleteImage(String imageMetadataId) {
        ImageMetadata imageMetadata = imageMetadataRepository.findById(imageMetadataId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + imageMetadataId));
        boolean isDeletedImage = imgurService.deleteImage(imageMetadata.getDeleteHash());
        if (isDeletedImage) {
            imageMetadataRepository.delete(imageMetadata);
        }
    }

    @Transactional()
    @Override
    public List<ImageMetadataDTO> uploadImageMultiple(List<MultipartFile> files) {
        List<ImageMetadata> uploadedImageMetadata = imgurService.uploadImages(files);

        List<ImageMetadataDTO> imageMetadataDTOList = uploadedImageMetadata.stream()
                .map(imageMetadataDTOMapper::toDto)
                .toList();

        return imageMetadataDTOList;
    }


}
