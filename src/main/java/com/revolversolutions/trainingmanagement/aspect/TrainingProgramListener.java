package com.revolversolutions.trainingmanagement.aspect;


import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import com.revolversolutions.trainingmanagement.entity.User;
import jakarta.persistence.PrePersist;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class TrainingProgramListener {

    @PrePersist
    public void prePersist(TrainingProgram program){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            User user = (User) authentication.getPrincipal();
            String fullName = user.getFirstName() + " " + user.getLastName();

            program.setCreatedBy(fullName);
        }

    }

}
