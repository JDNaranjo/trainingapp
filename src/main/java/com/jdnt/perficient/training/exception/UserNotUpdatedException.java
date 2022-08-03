package com.jdnt.perficient.training.exception;

public class UserNotUpdatedException extends RuntimeException{

    public UserNotUpdatedException(Long id){
        super("User: "+id+" not updated" );
    }
}
