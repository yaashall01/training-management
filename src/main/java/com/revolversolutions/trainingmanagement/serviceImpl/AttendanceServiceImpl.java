package com.revolversolutions.trainingmanagement.serviceImpl;


import com.revolversolutions.trainingmanagement.dto.AttendanceDTO;
import com.revolversolutions.trainingmanagement.entity.Attendance;
import com.revolversolutions.trainingmanagement.entity.AttendanceId;
import com.revolversolutions.trainingmanagement.entity.Session;
import com.revolversolutions.trainingmanagement.entity.User;
import com.revolversolutions.trainingmanagement.enums.AttendanceStatus;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
import com.revolversolutions.trainingmanagement.mapper.AttendanceDTOMapper;
import com.revolversolutions.trainingmanagement.repository.AttendanceRepository;
import com.revolversolutions.trainingmanagement.service.AttendanceService;
import com.revolversolutions.trainingmanagement.service.SessionService;
import com.revolversolutions.trainingmanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final UserService userService;

    private final SessionService sessionService;

    private final AttendanceDTOMapper attendanceDTOMapper;

    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, UserService userService, SessionService sessionService, AttendanceDTOMapper attendanceDTOMapper) {
        this.attendanceRepository = attendanceRepository;
        this.userService = userService;
        this.sessionService = sessionService;
        this.attendanceDTOMapper = attendanceDTOMapper;
    }

    @Override
    public void markAttendance(String userId, String sessionId, AttendanceStatus status, String justification) {
        log.info("Marking attendance for user: {}, session: {}, status: {}, justification: {}",
                userId, sessionId, status, justification);

        User user = userService.findUserById(userId);
        Session session = sessionService.findBySessionId(sessionId);

        AttendanceId attendanceId = new AttendanceId(user.getId(), session.getId());
        Attendance attendance = Attendance.builder()
                .id(attendanceId)
                .user(user)
                .session(session)
                .status(status)
                .justification(justification)
                .build();

        attendanceRepository.save(attendance);
        log.info("Attendance marked successfully for user: {}, session: {}", userId, sessionId);
    }

    @Override
    public List<AttendanceDTO> getUserAttendance(String userId) {
        log.info("Fetching attendance for user: {}", userId);
        User user = userService.findUserById(userId);
        List<AttendanceDTO> attendances = attendanceDTOMapper.toDtos(attendanceRepository.findByUser(user));
        log.info("Attendance fetched successfully for user: {}", userId);
        return attendances;
    }

    @Override
    public List<AttendanceDTO> getSessionAttendance(String sessionId) {
        log.info("Fetching attendance for session: {}", sessionId);
        Session session = sessionService.findBySessionId(sessionId);
        List<AttendanceDTO> attendances = attendanceDTOMapper.toDtos(attendanceRepository.findBySession(session));
        log.info("Attendance fetched successfully for session: {}", sessionId);
        return attendances;
    }

    @Override
    public double calculateUserProgress(String userId, String programId) {
        log.info("Calculating progress for user: {} in program: {}", userId, programId);
        User user = userService.findUserById(userId);
        List<Attendance> attendances = attendanceRepository.findByUser(user);

        long totalSessions = attendances.stream()
                .filter(attendance -> attendance.getSession().getProgram().getProgramId().equals(programId))
                .count();
        System.out.println(totalSessions);
        long attendedSessions = attendances.stream()
                .filter(attendance -> attendance.getSession().getProgram().getProgramId().equals(programId) && attendance.getStatus() == AttendanceStatus.PRESENT/*|| attendance.getStatus() == AttendanceStatus.EXCUSED*/)
                .count();
        System.out.println(attendedSessions);

        double progress = (double) attendedSessions / totalSessions * 100;
        log.info("Progress calculated for user: {} in program: {} is {}", userId, programId, progress);
        return progress;
    }

    @Override
    public void updateAttendance(String userId, String sessionId, AttendanceStatus status, String justification) {
        log.info("Updating attendance for user: {}, session: {}, status: {}, justification: {}",
                userId, sessionId, status, justification);

        User user = userService.findUserById(userId);
        Session session = sessionService.findBySessionId(sessionId);

        AttendanceId attendanceId = new AttendanceId(user.getId(), session.getId());
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        attendance.setStatus(status);
        attendance.setJustification(justification);

        attendanceRepository.save(attendance);
        log.info("Attendance updated successfully for user: {}, session: {}", userId, sessionId);
    }

    @Override
    public void deleteAttendance(String userId, String sessionId) {
        log.info("Deleting attendance for user: {}, session: {}", userId, sessionId);

        User user = userService.findUserById(userId);
        Session session = sessionService.findBySessionId(sessionId);

        AttendanceId attendanceId = new AttendanceId(user.getId(), session.getId());
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        attendanceRepository.delete(attendance);
        log.info("Attendance deleted successfully for user: {}, session: {}", userId, sessionId);
    }
}
