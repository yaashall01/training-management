package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.dto.CtaDTO;
import com.revolversolutions.trainingmanagement.dto.HeroDTO;
import com.revolversolutions.trainingmanagement.dto.LandingPageDTO;
import com.revolversolutions.trainingmanagement.dto.ServiceDTO;
import com.revolversolutions.trainingmanagement.mapper.LandingPageDTOMapper;
import com.revolversolutions.trainingmanagement.serviceImpl.LandingPageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/landing_page")
@Slf4j
public class LandingPageController {
    private final LandingPageServiceImpl landingPageService;
    private final LandingPageDTOMapper landingPageDTOMapper;

    public LandingPageController(LandingPageServiceImpl landingPageService, LandingPageDTOMapper landingPageDTOMapper) {
        this.landingPageService = landingPageService;
        this.landingPageDTOMapper = landingPageDTOMapper;
    }

    @GetMapping
    public ResponseEntity<LandingPageDTO> getOrCreateLandingPage() {
        LandingPageDTO landingPage =  landingPageService.getOrCreateLandingPage();
        return ResponseEntity.ok(landingPage);
    }
    @PatchMapping
    public ResponseEntity<LandingPageDTO> patchLandingPage(@RequestBody Map<String , Object> fields) {
        LandingPageDTO updatedLandingPage = landingPageService.updateLandingPage(fields);
        return ResponseEntity.ok(updatedLandingPage);
    }

    @PostMapping(value = "/hero", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<HeroDTO> createHero(
            @RequestPart("hero") HeroDTO heroDTO,
            @RequestParam("image") MultipartFile imageFile
    ) {
        HeroDTO createdHero = landingPageService.createHero(heroDTO , imageFile);
        return ResponseEntity.ok(createdHero);
    }

    @PutMapping(value = "/hero/{heroId}" , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<HeroDTO> updateHero(
            @PathVariable String heroId,
            @RequestPart("hero") HeroDTO heroDTO,
            @RequestParam("image") MultipartFile imageFile
    ) {
        HeroDTO updatedHero = landingPageService.updateHero(heroId , heroDTO , imageFile);
        return ResponseEntity.ok(updatedHero);
    }
    @DeleteMapping("/hero/{heroId}")
    public ResponseEntity<Void> deleteHero(@PathVariable String heroId) {
        landingPageService.deleteHero(heroId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/hero/{heroId}/image/{imageId}")
    public ResponseEntity<String> deleteHeroImage(@PathVariable String heroId , @PathVariable String imageId) {
        landingPageService.deleteHeroImage(heroId , imageId);
        return new ResponseEntity<>("Hero Image deleted successfully", HttpStatus.OK);
    }
    @PostMapping("service")
    public ResponseEntity<ServiceDTO> createService(@RequestBody ServiceDTO serviceDTO) {
        ServiceDTO createdService = landingPageService.createService(serviceDTO);
        return ResponseEntity.ok(createdService);
    }
    @PutMapping("/service/{serviceId}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable String serviceId, @RequestBody ServiceDTO serviceDTO) {
        ServiceDTO updatedService = landingPageService.updateService(serviceId , serviceDTO);
        return ResponseEntity.ok(updatedService);
    }
    @DeleteMapping("/service/{serviceId}")
    public ResponseEntity<Void> deleteService(@PathVariable String serviceId) {
        landingPageService.deleteService(serviceId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping(value ="cta" , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CtaDTO> createCta(
            @RequestPart("cta") CtaDTO ctaDTO,
            @RequestParam("image") MultipartFile imageFile
    ) {
        CtaDTO createdCta = landingPageService.createCta(ctaDTO , imageFile);
        return ResponseEntity.ok(createdCta);
    }
    @PutMapping(value = "/cta/{ctaId}" , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CtaDTO> updateCta(
            @PathVariable String ctaId,
            @RequestPart("cta") CtaDTO ctaDTO,
            @RequestParam("image") MultipartFile imageFile

    ) {
        CtaDTO updatedCta = landingPageService.updateCta(ctaId , ctaDTO , imageFile);
        return ResponseEntity.ok(updatedCta);
    }
    @DeleteMapping("/cta/{ctaId}/image/{imageId}")
    public ResponseEntity<String> deleteCtaImage(@PathVariable String ctaId , @PathVariable String imageId) {
        landingPageService.deleteCtaImage(ctaId , imageId);
        return new ResponseEntity<>("Call to action Image deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/cta/{ctaId}")
    public ResponseEntity<String> deleteCta(@PathVariable String ctaId) {
        landingPageService.deleteCta(ctaId);
        return ResponseEntity.ok("Call to action Deleted successfully!!.");
    }

}
