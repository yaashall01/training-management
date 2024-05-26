package com.revolversolutions.trainingmanagement.repository;

import com.revolversolutions.trainingmanagement.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface FileDBRepository extends JpaRepository<FileDB, String> {
}
