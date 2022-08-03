package com.jdnt.perficient.training.exception;

public class UserNotCreatedException extends RuntimeException{

    public UserNotCreatedException(){
        super("User can not be created");
    }

}
