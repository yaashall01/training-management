package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.AttendanceDTO;
import com.revolversolutions.trainingmanagement.entity.Attendance;
import com.revolversolutions.trainingmanagement.enums.AttendanceStatus;

import java.util.List;


public interface AttendanceService {
    void markAttendance(String userId, String sessionId, AttendanceStatus status, String justification);

    List<AttendanceDTO> getUserAttendance(String userId);

    List<AttendanceDTO> getSessionAttendance(String sessionId);

    double calculateUserProgress(String userId, String programId);

    void updateAttendance(String userId, String sessionId, AttendanceStatus status, String justification);

    void deleteAttendance(String userId, String sessionId);
}
