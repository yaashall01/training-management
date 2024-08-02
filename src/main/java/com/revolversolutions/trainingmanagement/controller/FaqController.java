package com.revolversolutions.trainingmanagement.controller;


import com.revolversolutions.trainingmanagement.entity.Faq;
import com.revolversolutions.trainingmanagement.serviceImpl.FaqService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/faqs")
public class FaqController {


    private final FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public ResponseEntity<List<Faq>> getAllFaqs() {
        List<Faq> faqs = faqService.getAllFaqs();
        return new ResponseEntity<>(faqs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faq> getFaqById(@PathVariable Integer id) {
        Optional<Faq> faq = faqService.getFaqById(id);
        return faq.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Faq> addFaq(@RequestBody Faq faq) {
        Faq savedFaq = faqService.saveFaq(faq);
        return new ResponseEntity<>(savedFaq, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faq> updateFaq(@PathVariable Integer id, @RequestBody Faq faq) {
        Optional<Faq> existingFaq = faqService.getFaqById(id);
        if (existingFaq.isPresent()) {
            faq.setId(id);
            Faq updatedFaq = faqService.saveFaq(faq);
            return new ResponseEntity<>(updatedFaq, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaq(@PathVariable Integer id) {
        Optional<Faq> faq = faqService.getFaqById(id);
        if (faq.isPresent()) {
            faqService.deleteFaq(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
