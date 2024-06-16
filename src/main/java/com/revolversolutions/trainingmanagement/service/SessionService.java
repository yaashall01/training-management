package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.SessionDTO;
import com.revolversolutions.trainingmanagement.entity.Session;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface SessionService {
    SessionDTO createSession(SessionDTO sessionDTO);

    SessionDTO createSessionForProgram(String programId, SessionDTO sessionDTO);

    @Transactional
    SessionDTO updateSession(String id, SessionDTO sessionDTO);

    void deleteSession(String id);

    SessionDTO getSessionBySessionId(String sessionId);

    List<SessionDTO> getAllSessions();

    List<SessionDTO> getSessionsByProgram(String programId);

    Session findBySessionId(String sessionId);
}
