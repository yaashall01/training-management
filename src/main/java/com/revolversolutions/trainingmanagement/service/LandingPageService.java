package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.CtaDTO;
import com.revolversolutions.trainingmanagement.dto.HeroDTO;
import com.revolversolutions.trainingmanagement.dto.LandingPageDTO;
import com.revolversolutions.trainingmanagement.dto.ServiceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface LandingPageService {
    LandingPageDTO getOrCreateLandingPage();
    LandingPageDTO updateLandingPage(Map<String , Object> fields);

    // service services...

    ServiceDTO createService(ServiceDTO serviceDTO);
    ServiceDTO updateService(String serviceId , ServiceDTO serviceDTO);
    void deleteService(String serviceId);

    // Hero Services...

    HeroDTO createHero(HeroDTO heroDTO , MultipartFile file);
    HeroDTO updateHero(String heroId , HeroDTO heroDTO , MultipartFile file);
    void deleteHero(String heroId);
    void deleteHeroImage(String heroId , String imageId);

    // Cta Services...

    CtaDTO createCta(CtaDTO ctaDTO , MultipartFile file);
    CtaDTO updateCta(String ctaId , CtaDTO ctaDTO , MultipartFile file);
    void deleteCta(String ctaId);
    void deleteCtaImage(String ctaId , String imageId);
}
