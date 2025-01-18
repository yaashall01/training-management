package com.revolversolutions.trainingmanagement.repository;

import com.revolversolutions.trainingmanagement.entity.LService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<LService, String> {
}
