package com.revolversolutions.trainingmanagement.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.revolversolutions.trainingmanagement.entity.Logistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LogisticRepository extends JpaRepository<Logistic, Long> {

    Optional<Logistic> findByLogisticsId(String id);

    void deleteByLogisticsId(String id);
}
