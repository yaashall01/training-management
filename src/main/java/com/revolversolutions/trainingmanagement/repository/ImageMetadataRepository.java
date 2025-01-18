package com.revolversolutions.trainingmanagement.repository;

import com.revolversolutions.trainingmanagement.entity.ImageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMetadataRepository extends JpaRepository<ImageMetadata, String> {
}
