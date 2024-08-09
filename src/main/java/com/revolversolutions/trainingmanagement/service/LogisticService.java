package com.revolversolutions.trainingmanagement.service;

import com.revolversolutions.trainingmanagement.dto.LogisticDTO;
import com.revolversolutions.trainingmanagement.entity.Logistic;

import java.util.List;


public interface LogisticService {
    List<LogisticDTO> getAllLogistics();

    LogisticDTO getLogisticById(String id);

    LogisticDTO updateLogistic(String id, LogisticDTO logisticDTO);

    LogisticDTO saveLogistic(LogisticDTO Logistic);

    void deleteLogistic(String id);
}
