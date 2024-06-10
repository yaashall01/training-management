package com.revolversolutions.trainingmanagement.serviceImpl;


import com.revolversolutions.trainingmanagement.entity.UserActivity;
import com.revolversolutions.trainingmanagement.repository.UserActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserActivityService {

    private final UserActivityRepository userActivityRepository;

    public UserActivityService(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }


    public void save(UserActivity userActivity){
        userActivityRepository.save(userActivity);
    }

    public List<UserActivity> getAllActivities(){
        return userActivityRepository.findAll();
    }
}
