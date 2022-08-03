package com.jdnt.perficient.training.exception;

public class UserNotDeletedException extends RuntimeException{

    public UserNotDeletedException(Long id){
        super("User: "+id+" not deleted" );
    }
}
