package com.revolversolutions.trainingmanagement.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum TrainingProgramLevel {

    BASIC("Basic"),
    ADVANCED("Advanced"),
    PSYCHOTHERAPY("Psychotherapy");

    private String programLevel;

    TrainingProgramLevel(String programLevel) {
        this.programLevel = programLevel;
    }

    @JsonValue
    public String getProgramLevel() {
        return programLevel;
    }

    @JsonCreator
    public static TrainingProgramLevel fromValue(String value) {
        for (TrainingProgramLevel level : values()) {
            String currentLevel = level.getProgramLevel();
            if (currentLevel.equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid value for TrainingProgramLevel: " + value);
    }
}