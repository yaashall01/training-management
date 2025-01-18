package com.revolversolutions.trainingmanagement.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CtaDTO {
    private String ctaId;
    private String ctaTitle;
    private String ctaSub;
    private String buttonText;
    private String leadingTo;
    private ImageMetadataDTO image;

}
