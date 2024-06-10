package com.revolversolutions.trainingmanagement.controller;


import com.revolversolutions.trainingmanagement.dto.SessionDTO;
import com.revolversolutions.trainingmanagement.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTO sessionDTO) {
        SessionDTO createdSession = sessionService.createSession(sessionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionDTO> getSession(@PathVariable String sessionId) {
            SessionDTO session = sessionService.getSessionBySessionId(sessionId);
            return ResponseEntity.ok(session);
    }

    @Operation(
            description = "Get All Sessions"
    )
    @GetMapping
    public ResponseEntity<List<SessionDTO>> getAllSessions() {
        return ResponseEntity.ok(sessionService.getAllSessions());
    }


    @PutMapping("/{sessionId}")
    public ResponseEntity<SessionDTO> updateSession(@PathVariable String sessionId, @RequestBody SessionDTO sessionDTO) {
            return ResponseEntity.ok(sessionService.updateSession(sessionId, sessionDTO));
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable String sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{programId}")
    public ResponseEntity<List<SessionDTO>> getSessionsByProgram(@PathVariable String programId){
        return ResponseEntity.ok(sessionService.getSessionsByProgram(programId));
    }


}