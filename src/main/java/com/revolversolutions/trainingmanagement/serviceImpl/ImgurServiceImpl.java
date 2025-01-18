package com.revolversolutions.trainingmanagement.serviceImpl;

import com.revolversolutions.trainingmanagement.dto.ImageMetadataDTO;
import com.revolversolutions.trainingmanagement.entity.ImageMetadata;
import com.revolversolutions.trainingmanagement.mapper.ImageMetadataDTOMapper;
import com.revolversolutions.trainingmanagement.repository.ImageMetadataRepository;
import com.revolversolutions.trainingmanagement.service.ImgurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ImgurServiceImpl implements ImgurService {

    private final RestTemplate restTemplate;
    private final ImageMetadataDTOMapper imageMetadataDTOMapper;
    private final ImageMetadataRepository imageMetadataRepository;

    @Value("${imgur.clientId}")
    private String clientId;
    @Value("${imgur.url}")
    private String imgurBaseUrl;

    public ImgurServiceImpl(RestTemplateBuilder restTemplateBuilder, ImageMetadataDTOMapper imageMetadataDTOMapper, ImageMetadataRepository imageMetadataRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.imageMetadataDTOMapper = imageMetadataDTOMapper;
        this.imageMetadataRepository = imageMetadataRepository;
    }

    private HttpHeaders getAuthorizationHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Client-ID " + clientId);
        return headers;
    }

    @Transactional()
    @Override
    public ImageMetadata uploadImage(MultipartFile file) {
        try {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
        HttpHeaders headers = this.getAuthorizationHeaders();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", base64Image);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(imgurBaseUrl, HttpMethod.POST, requestEntity, Map.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            Map data = (Map) response.getBody().get("data");
            ImageMetadata imageMetadata = new ImageMetadata();
            imageMetadata.setImgurId(data.get("id").toString());
            imageMetadata.setDeleteHash(data.get("deletehash").toString());
            imageMetadata.setImgurUrl(data.get("link").toString());
            ImageMetadata createdImage = imageMetadataRepository.save(imageMetadata);
            return createdImage;
        } else {
            throw new RuntimeException("Failed to upload image");
        }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional()
    @Override
    public boolean deleteImage(String deleteHash) {
        HttpHeaders headers = this.getAuthorizationHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        String deleteUrl = imgurBaseUrl + "/" + deleteHash;
        ResponseEntity<Map> response = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, requestEntity, Map.class);
        return  response.getStatusCode() == HttpStatus.OK;
    }

    @Transactional()
    @Override
    public List<ImageMetadata> uploadImages(List<MultipartFile> files) {
        List<ImageMetadata> imageMetadataList = new ArrayList<>();
        if (!files.isEmpty()) {
            files.forEach(file -> {
                ImageMetadata addedImage = uploadImage(file);
                imageMetadataList.add(addedImage);
            });
        }
        return imageMetadataList;
    }

}
