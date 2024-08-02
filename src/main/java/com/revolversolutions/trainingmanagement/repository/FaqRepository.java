package com.revolversolutions.trainingmanagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.revolversolutions.trainingmanagement.entity.Faq;

public interface FaqRepository extends JpaRepository<Faq, Integer> {

}
