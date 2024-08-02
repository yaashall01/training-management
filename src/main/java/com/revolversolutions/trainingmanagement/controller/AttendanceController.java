package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.aspect.UserActivityLog;
import com.revolversolutions.trainingmanagement.dto.AttendanceDTO;
import com.revolversolutions.trainingmanagement.entity.Attendance;
import com.revolversolutions.trainingmanagement.enums.ActionType;
import com.revolversolutions.trainingmanagement.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {


    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<Void> markAttendance(@RequestBody AttendanceDTO request) {
        attendanceService.markAttendance(
                request.getUserId(),
                request.getSessionId(),
                request.getStatus(),
                request.getJustification()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateAttendance(@RequestBody AttendanceDTO request) {
        attendanceService.updateAttendance(
                request.getUserId(),
                request.getSessionId(),
                request.getStatus(),
                request.getJustification()
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{userId}/{sessionId}")
    @UserActivityLog(action = "Admin Delete Attendance", actionType = ActionType.DELETE)
    public ResponseEntity<Void> deleteAttendance(@PathVariable String userId, @PathVariable String sessionId) {
        attendanceService.deleteAttendance(userId, sessionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AttendanceDTO>> getUserAttendance(@PathVariable String userId) {
        List<AttendanceDTO> attendances = attendanceService.getUserAttendance(userId);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<AttendanceDTO>> getSessionAttendance(@PathVariable String sessionId) {
        List<AttendanceDTO> attendances = attendanceService.getSessionAttendance(sessionId);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/progress/{userId}/{programId}")
    public ResponseEntity<Double> calculateUserProgress(@PathVariable String userId, @PathVariable String programId) {
        double progress = attendanceService.calculateUserProgress(userId, programId);
        return ResponseEntity.ok(progress);
    }
}
