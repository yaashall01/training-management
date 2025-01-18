package com.revolversolutions.trainingmanagement.repository;

import com.revolversolutions.trainingmanagement.entity.PickupPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PickupPointRepository extends JpaRepository<PickupPoint , String> {
    Optional<PickupPoint> findByPickupPointId(String id);
}
