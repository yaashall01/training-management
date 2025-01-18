package com.revolversolutions.trainingmanagement.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ImageMetadataDTO {
    private String imageMetadataId;
    private String imgurId; // Imgur's image ID
    private String imgurUrl; // URL of the stored image
    private String deleteHash; // Hash required to delete the image
    private String title; // Optional: title of the image
    private String description; // Optional: description of the image
    private LocalDateTime uploadTimestamp; // When the image was uploaded

}
