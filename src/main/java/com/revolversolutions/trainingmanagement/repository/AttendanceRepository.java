package com.revolversolutions.trainingmanagement.repository;


import com.revolversolutions.trainingmanagement.entity.Attendance;
import com.revolversolutions.trainingmanagement.entity.AttendanceId;
import com.revolversolutions.trainingmanagement.entity.Session;
import com.revolversolutions.trainingmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, AttendanceId> {

    List<Attendance> findByUser(User user);
    List<Attendance> findBySession(Session session);

    void deleteByUserId(Long userId);
}
