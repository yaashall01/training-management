package com.revolversolutions.trainingmanagement.repository;

import com.revolversolutions.trainingmanagement.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session,Long> {
}
