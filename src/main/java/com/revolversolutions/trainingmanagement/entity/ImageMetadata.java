package com.revolversolutions.trainingmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "ImageMetadata")
@Table(name = "imageMetadata")
public class ImageMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String imageMetadataId;
    private String imgurId; // Imgur's image ID
    private String imgurUrl; // URL of the stored image
    private String deleteHash;   // Hash required to delete the image
    private String title; // Optional: title of the image
    private String description; // Optional: description of the image
    private LocalDateTime uploadTimestamp;

    @PrePersist
    protected void onCreate() {
        this.uploadTimestamp = LocalDateTime.now();
    }

    // Explicit getter method to ensure availability
    public String getDeleteHash() {
        return deleteHash;
    }

}
