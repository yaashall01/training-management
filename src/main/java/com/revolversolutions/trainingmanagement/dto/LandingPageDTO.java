package com.revolversolutions.trainingmanagement.dto;

import com.revolversolutions.trainingmanagement.entity.Heading;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class LandingPageDTO {
    private String landingPageId;
    private String country;
    private String name;
    private String slogan;
    private List<HeroDTO> heros;
    private List<ServiceDTO> LServices;
    private List<CtaDTO> ctaList = new ArrayList<>();
    private Heading heading;
}
