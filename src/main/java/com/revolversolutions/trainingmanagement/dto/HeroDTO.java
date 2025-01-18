package com.revolversolutions.trainingmanagement.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HeroDTO {
    private String heroId;
    private String heroTitle;
    private String subtitle;
    private ImageMetadataDTO image;
    private String buttonText;
    private boolean isActive;
}
