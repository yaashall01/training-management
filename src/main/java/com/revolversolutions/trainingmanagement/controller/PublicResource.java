package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.dto.ResponseTrainingProgramPage;
import com.revolversolutions.trainingmanagement.dto.ReviewDTO;
import com.revolversolutions.trainingmanagement.entity.Faq;
import com.revolversolutions.trainingmanagement.service.TrainingProgramService;
import com.revolversolutions.trainingmanagement.serviceImpl.FaqService;
import com.revolversolutions.trainingmanagement.serviceImpl.ReviewServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
@AllArgsConstructor
public class PublicResource {

    private final ReviewServiceImpl reviewService;
    private final FaqService faqService;
    private final TrainingProgramService trainingProgramService;


    @GetMapping("/program-reviews/{programId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProgram(@PathVariable String programId){
        return ResponseEntity.ok(reviewService.getReviewsByProgramId(programId));
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



}
