package com.revolversolutions.trainingmanagement.exception;



public class AlreadyEnrolledException extends RuntimeException{

    public AlreadyEnrolledException(String msg){
        super(msg);
    }
}
