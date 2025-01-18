package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.dto.ImageMetadataDTO;
import com.revolversolutions.trainingmanagement.dto.LandingPageDTO;
import com.revolversolutions.trainingmanagement.dto.ResponseTrainingProgramPage;
import com.revolversolutions.trainingmanagement.dto.ReviewDTO;
import com.revolversolutions.trainingmanagement.entity.Faq;
import com.revolversolutions.trainingmanagement.entity.Notification;
import com.revolversolutions.trainingmanagement.service.TrainingProgramService;
import com.revolversolutions.trainingmanagement.serviceImpl.FaqService;
import com.revolversolutions.trainingmanagement.serviceImpl.ImageMetadataServiceImpl;
import com.revolversolutions.trainingmanagement.serviceImpl.LandingPageServiceImpl;
import com.revolversolutions.trainingmanagement.serviceImpl.ReviewServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
@AllArgsConstructor
public class PublicResource {

    private final ReviewServiceImpl reviewService;
    private final FaqService faqService;
    private final TrainingProgramService trainingProgramService;
    private final LandingPageServiceImpl landingPageService;
    private final ImageMetadataServiceImpl imageMetadataService;
    private final SimpMessagingTemplate messagingTemplate;



    @GetMapping("/program-reviews/{programId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProgram(@PathVariable String programId){
        return ResponseEntity.ok(reviewService.getReviewsByProgramId(programId));
    }
    @GetMapping("reviews")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/faqs")
    public ResponseEntity<List<Faq>> getAllFaqs() {
        List<Faq> faqs = faqService.getAllFaqs();
        return new ResponseEntity<>(faqs, HttpStatus.OK);
    }

    @GetMapping("/programs")
    public ResponseEntity<ResponseTrainingProgramPage> getPrograms(
            @RequestParam(name = "pageIndex" ,defaultValue = "0" ,required = false) int pageNo,
            @RequestParam(name = "pageSize" ,defaultValue = "20" ,required = false) int pageSize,
            @RequestParam(name = "sortBy" ,defaultValue = "id" ,required = false) String sortBy,
            @RequestParam(name = "sortDir" ,defaultValue = "asc" ,required = false) String sortDir,
            @RequestParam(name = "term" ,defaultValue = "" ,required = false) String term,
            @RequestParam(name = "programType" ,required = false) String programType

    ){
        ResponseTrainingProgramPage page = trainingProgramService.getAllTrainingPrograms(pageNo,pageSize,sortBy,sortDir,term , programType);
        return new ResponseEntity<>(page,HttpStatus.OK);
    }

    @GetMapping("landing_page")
    public ResponseEntity<LandingPageDTO> getOrCreateLandingPage() {
        LandingPageDTO landingPage =  landingPageService.getOrCreateLandingPage();
        return ResponseEntity.ok(landingPage);
    }

    // Test Images ...... TODO: Should removed...


    @PostMapping("/image/upload")
    public ResponseEntity<ImageMetadataDTO> uploadImage(@RequestParam("file") MultipartFile file) {
        ImageMetadataDTO savedImage = imageMetadataService.uploadImageMetadata(file);
        return ResponseEntity.ok(savedImage);
    }
    @PostMapping("/image/upload/multiple")
    public ResponseEntity<List<ImageMetadataDTO>> uploadImages(@RequestParam("files") List<MultipartFile> files) {
        List<ImageMetadataDTO> savedImages = imageMetadataService.uploadImageMultiple(files);
        return ResponseEntity.ok(savedImages);
    }
    @DeleteMapping("/image/{imageMetadataId}")
    public ResponseEntity<String> deleteImage(@PathVariable String imageMetadataId) {
        imageMetadataService.deleteImage(imageMetadataId);
        return ResponseEntity.ok("Image deleted successfully");
    }

    // Test Notifications ...... TODO: Should removed...

//    @PostMapping("/send-notification")
//    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {
//        messagingTemplate.convertAndSend("/topic/notifications", notification);
//        return ResponseEntity.ok("Notification sent!");
//    }


}
