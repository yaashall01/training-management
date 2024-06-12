package com.revolversolutions.trainingmanagement.serviceImpl;


import com.revolversolutions.trainingmanagement.dto.SessionDTO;
import com.revolversolutions.trainingmanagement.entity.Session;
import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
import com.revolversolutions.trainingmanagement.mapper.SessionDTOMapper;
import com.revolversolutions.trainingmanagement.repository.SessionRepository;
import com.revolversolutions.trainingmanagement.repository.TrainingProgramRepository;
import com.revolversolutions.trainingmanagement.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final SessionDTOMapper sessionDTOMapper;
    private final TrainingProgramRepository trainingProgramRepository;

    public SessionServiceImpl(SessionRepository sessionRepository, SessionDTOMapper sessionDTOMapper, TrainingProgramRepository trainingProgramRepository) {
        this.sessionRepository = sessionRepository;
        this.sessionDTOMapper = sessionDTOMapper;
        this.trainingProgramRepository = trainingProgramRepository;
    }

    @Override
    public SessionDTO createSession(SessionDTO sessionDTO) {
        Session session = sessionDTOMapper.toEntity(sessionDTO);
        return sessionDTOMapper.toDto(sessionRepository.save(session));
    }

    @Override
    public SessionDTO createSessionForProgram(String programId, SessionDTO sessionDTO) {
        TrainingProgram program = trainingProgramRepository.findByProgramId(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));
        Session session = Session.builder()
                .title(sessionDTO.getTitle())
                .location(sessionDTO.getLocation())
                .description(sessionDTO.getDescription())
                .startTime(sessionDTO.getStartTime())
                .endTime(sessionDTO.getEndTime())
                .program(program)
                .build();

        session = sessionRepository.save(session);
        log.info("Create session title: {}, for program name: {}",sessionDTO.getSessionId(), program.getTitle());
        return sessionDTOMapper.toDto(session);
    }


    @Override
    @Transactional
    public SessionDTO updateSession(String sessionId, SessionDTO sessionDTO) {
        Session session = sessionRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        session.setTitle(sessionDTO.getTitle());
        session.setDescription(sessionDTO.getDescription());
        session.setLocation(sessionDTO.getLocation());
        session.setStartTime(sessionDTO.getStartTime());
        session.setEndTime(sessionDTO.getEndTime());

        log.info("Session id: {}, title: {} successfully updated.", session.getSessionId(), session.getTitle());

        session = sessionRepository.save(session);
        return sessionDTOMapper.toDto(session);
    }

    @Override
    public void deleteSession(String id) {
        sessionRepository.deleteBySessionId(id);
    }

    @Override
    public SessionDTO getSessionBySessionId(String sessionId) {
        Session session = sessionRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found w/ id: " + sessionId));
        return sessionDTOMapper.toDto(session);
    }

    @Override
    public List<SessionDTO> getAllSessions(){
        return sessionDTOMapper.toDtos(sessionRepository.findAll());
    }

    @Override
    public List<SessionDTO> getSessionsByProgram(String programId){
        TrainingProgram program = trainingProgramRepository.findByProgramId(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with id: "+programId));
        return sessionDTOMapper.toDtos(program.getSessions());
    }


    //TODO : complete this
    public List<SessionDTO> getSessionsByUser(){
        return null;
    }


}
