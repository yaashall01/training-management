package com.revolversolutions.trainingmanagement.serviceImpl;

import com.revolversolutions.trainingmanagement.dto.*;
import com.revolversolutions.trainingmanagement.entity.*;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
import com.revolversolutions.trainingmanagement.mapper.*;
import com.revolversolutions.trainingmanagement.repository.CtaRepository;
import com.revolversolutions.trainingmanagement.repository.HeroRepository;
import com.revolversolutions.trainingmanagement.repository.LandingPageRepository;
import com.revolversolutions.trainingmanagement.repository.ServiceRepository;
import com.revolversolutions.trainingmanagement.service.LandingPageService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.Map;

@Service
@Slf4j
public class LandingPageServiceImpl implements LandingPageService {
    private final LandingPageRepository landingPageRepository;
    private final HeroRepository heroRepository;
    private final ServiceRepository serviceRepository;
    private final LandingPageDTOMapper landingPageDTOMapper;
    private final HeroDTOMapper heroDTOMapper;
    private final ServiceDTOMapper serviceDTOMapper;
    private final CtaDTOMapper ctaDTOMapper;
    private final CtaRepository ctaRepository;
    private final ImageMetadataServiceImpl imageMetadataService;
    private final ImageMetadataDTOMapper imageMetadataDTOMapper;
    ModelMapper modelMapper = new ModelMapper();


    public LandingPageServiceImpl(LandingPageRepository landingPageRepository, HeroRepository heroRepository, ServiceRepository serviceRepository, LandingPageDTOMapper landingPageDTOMapper, HeroDTOMapper heroDTOMapper, ServiceDTOMapper serviceDTOMapper, CtaDTOMapper ctaDTOMapper, CtaRepository ctaRepository, ImageMetadataServiceImpl imageMetadataService, ImageMetadataDTOMapper imageMetadataDTOMapper) {
        this.landingPageRepository = landingPageRepository;
        this.heroRepository = heroRepository;
        this.serviceRepository = serviceRepository;
        this.landingPageDTOMapper = landingPageDTOMapper;
        this.heroDTOMapper = heroDTOMapper;
        this.serviceDTOMapper = serviceDTOMapper;
        this.ctaDTOMapper = ctaDTOMapper;
        this.ctaRepository = ctaRepository;
        this.imageMetadataService = imageMetadataService;
        this.imageMetadataDTOMapper = imageMetadataDTOMapper;
    }

    @Override
    public LandingPageDTO getOrCreateLandingPage() {
        LandingPageDTO landingPageDTO = landingPageRepository.findAll()
                .stream()
                .findFirst()
                .map(landingPageDTOMapper::toDto)
                .orElse(null);

        if (landingPageDTO !=null) {
            return landingPageDTO;
        }

        LandingPage defaultLandingPage = new LandingPage();
        Heading defaultHeading = new Heading();
        defaultLandingPage.setName("Default Landing Page");defaultLandingPage.setCountry("Morocco");
        defaultLandingPage.setSlogan("Slogan Here ................");
        defaultHeading.setHeadingText("Default Heading");defaultHeading.setHeadingTitle("Default Heading Title");
        defaultLandingPage.setHeading(defaultHeading);

        LandingPage savedLandingPage = landingPageRepository.save(defaultLandingPage);

        return landingPageDTOMapper.toDto(savedLandingPage);
    }

    @Override
    public LandingPageDTO updateLandingPage(Map<String , Object> fields) {
        LandingPageDTO landingPageDTO = this.getOrCreateLandingPage();
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(LandingPageDTO.class, key);
            if (field != null) {
                field.setAccessible(true);

                // Check if the field is a nested object (e.g., Cta or Heading)
                if (field.getType().equals(Cta.class) && value instanceof Map) {
                    Cta cta = modelMapper.map(value, Cta.class);
                    ReflectionUtils.setField(field, landingPageDTO, cta);
                } else if (field.getType().equals(Heading.class) && value instanceof Map) {
                    Heading heading = modelMapper.map(value, Heading.class);
                    ReflectionUtils.setField(field, landingPageDTO, heading);
                } else {
                    ReflectionUtils.setField(field, landingPageDTO, value);
                }
            }
        });
        LandingPage updatedLandingPage = landingPageRepository.save(landingPageDTOMapper.toEntity(landingPageDTO));
        return landingPageDTOMapper.toDto(updatedLandingPage);
    }

    @Override
    public ServiceDTO createService(ServiceDTO serviceDTO) {
        LService service = serviceDTOMapper.toEntity(serviceDTO);
        service.setLandingPage(landingPageDTOMapper.toEntity(this.getOrCreateLandingPage()));
        LService savedService = serviceRepository.save(service);
        return serviceDTOMapper.toDto(savedService);
    }

    @Override
    public ServiceDTO updateService(String serviceId, ServiceDTO serviceDTO) {
        LService service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + serviceId));
        service.setServiceTitle(serviceDTO.getServiceTitle());
        service.setDescription(serviceDTO.getDescription());

        LService savedService = serviceRepository.save(service);
        return serviceDTOMapper.toDto(savedService);
    }

    @Override
    public void deleteService(String serviceId) {
        log.info("delete String with id : {} ", serviceId);
        LService service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + serviceId));
        serviceRepository.delete(service);
        log.info("Service with id : {} is deleted", serviceId);

    }

    @Override
    public HeroDTO createHero(HeroDTO heroDTO , MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Hero image must not be empty");
        }

        ImageMetadataDTO uploadedImage =  imageMetadataService.uploadImageMetadata(file);
        Hero hero =heroDTOMapper.toEntity(heroDTO);
        hero.setImage(imageMetadataDTOMapper.toEntity(uploadedImage));
        hero.setLandingPage(landingPageDTOMapper.toEntity(this.getOrCreateLandingPage()));
        Hero savedHero = heroRepository.save(hero);
        return heroDTOMapper.toDto(savedHero);
    }
    @Transactional
    @Override
    public HeroDTO updateHero(String heroId, HeroDTO heroDTO , MultipartFile file) {
        Hero hero = heroRepository.findById(heroId)
                .orElseThrow(() -> new ResourceNotFoundException("Hero not found with id: " + heroId));

        if (file != null || !file.isEmpty()) {
            if (hero.getImage() != null) {
                imageMetadataService.deleteImage(hero.getImage().getImageMetadataId());
            }
            ImageMetadataDTO uploadedImage =  imageMetadataService.uploadImageMetadata(file);
            hero.setImage(imageMetadataDTOMapper.toEntity(uploadedImage));

        }

        hero.setHeroTitle(heroDTO.getHeroTitle());
        hero.setActive(heroDTO.isActive());
        hero.setButtonText(heroDTO.getButtonText());
        hero.setSubtitle(heroDTO.getSubtitle());

        Hero savedHero = heroRepository.save(hero);

        return heroDTOMapper.toDto(savedHero);
    }

    @Override
    public void deleteHero(String heroId) {
        log.info("delete Hero with id : {} ", heroId);
        Hero hero = heroRepository.findById(heroId)
                .orElseThrow(() -> new ResourceNotFoundException("Hero not found with id: " + heroId));
        heroRepository.delete(hero);
        log.info("Hero with id : {} is deleted", heroId);

    }
    @Transactional
    @Override
    public void deleteHeroImage(String heroId , String imageId) {
        log.info("delete Hero Inage with id : {} ", heroId);
        Hero hero = heroRepository.findById(heroId)
                .orElseThrow(() -> new ResourceNotFoundException("Hero not found with id: " + heroId));
        if (hero.getImage() != null) {
            hero.setImage(null);
            heroRepository.save(hero);
            imageMetadataService.deleteImage(imageId);
        }
        log.info("Hero with id : {} is deleted", heroId);

    }

    @Override
    public CtaDTO createCta(CtaDTO ctaDTO , MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Hero image must not be empty");
        }
        ImageMetadataDTO uploadedImage =  imageMetadataService.uploadImageMetadata(file);

        Cta cta = ctaDTOMapper.toEntity(ctaDTO);
        cta.setImage(imageMetadataDTOMapper.toEntity(uploadedImage));
        cta.setLandingPage(landingPageDTOMapper.toEntity(this.getOrCreateLandingPage()));
        Cta savedCta = ctaRepository.save(cta);
        return ctaDTOMapper.toDto(savedCta);
    }

    @Override
    public CtaDTO updateCta(String ctaId, CtaDTO ctaDTO , MultipartFile file) {
        Cta cta = ctaRepository.findById(ctaId)
                .orElseThrow(() -> new ResourceNotFoundException("Hero not found with id: " + ctaId));

        if (file != null || !file.isEmpty()) {
            if (cta.getImage() != null) {
                imageMetadataService.deleteImage(cta.getImage().getImageMetadataId());
            }
            ImageMetadataDTO uploadedImage =  imageMetadataService.uploadImageMetadata(file);
            cta.setImage(imageMetadataDTOMapper.toEntity(uploadedImage));

        }


        cta.setCtaTitle(ctaDTO.getCtaTitle());
        cta.setCtaSub(ctaDTO.getCtaSub());
        cta.setButtonText(ctaDTO.getButtonText());
        cta.setLeadingTo(ctaDTO.getLeadingTo());
        Cta savedCta = ctaRepository.save(cta);
        return ctaDTOMapper.toDto(savedCta);
    }

    @Override
    public void deleteCta(String ctaId) {
        log.info("delete Cta with id : {} ", ctaId);
        Cta cta = ctaRepository.findById(ctaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cta not found with id: " + ctaId));
        ctaRepository.delete(cta);
        log.info("Cta with id : {} is deleted", ctaId);
    }

    @Override
    public void deleteCtaImage(String ctaId, String imageId) {
        log.info("delete Cta Image with id : {} ", ctaId);
        Cta cta = ctaRepository.findById(ctaId)
                .orElseThrow(() -> new ResourceNotFoundException("Cta not found with id: " + ctaId));

        if (cta.getImage() != null) {
            cta.setImage(null);
            ctaRepository.save(cta);
            imageMetadataService.deleteImage(imageId);
        }
        log.info("Cta Image with id : {} is deleted", imageId);

    }


}
