package com.revolversolutions.trainingmanagement.repository;

import com.revolversolutions.trainingmanagement.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {

    Optional<Session> findBySessionId(String sessionId);
    void deleteBySessionId(String sessionId);

}
