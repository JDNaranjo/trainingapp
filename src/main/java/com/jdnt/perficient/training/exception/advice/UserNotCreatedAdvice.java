package com.jdnt.perficient.training.exception.advice;

import com.jdnt.perficient.training.exception.UserNotCreatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserNotCreatedAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotCreatedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotCreatedHandler(UserNotCreatedException e) {
        return e.getMessage();
    }

}
