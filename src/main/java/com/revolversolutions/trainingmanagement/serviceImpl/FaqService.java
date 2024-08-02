package com.revolversolutions.trainingmanagement.serviceImpl;

import com.revolversolutions.trainingmanagement.entity.Faq;
import com.revolversolutions.trainingmanagement.repository.FaqRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FaqService {

    private final FaqRepository faqRepository;

    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public List<Faq> getAllFaqs() {
        return faqRepository.findAll();
    }

    public Optional<Faq> getFaqById(Integer id) {
        return faqRepository.findById(id);
    }

    public Faq saveFaq(Faq faq) {
        return faqRepository.save(faq);
    }

    public void deleteFaq(Integer id) {
        faqRepository.deleteById(id);
    }
}