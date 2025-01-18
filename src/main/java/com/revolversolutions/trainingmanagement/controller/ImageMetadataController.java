package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.serviceImpl.ImageMetadataServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/images")
public class ImageMetadataController {
    private final ImageMetadataServiceImpl imageMetadataService;

    public ImageMetadataController(ImageMetadataServiceImpl imageMetadataService) {
        this.imageMetadataService = imageMetadataService;
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<String> deleteFaq(@PathVariable String imageId) {
        imageMetadataService.deleteImage(imageId);
        return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
    }
}
