package com.jdnt.perficient.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserNotUpdatedAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotUpdatedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotUpdatedHandler(UserNotUpdatedException e) {
        return e.getMessage();
    }

}
