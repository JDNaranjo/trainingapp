package com.jdnt.perficient.training.exception.advice;

import com.jdnt.perficient.training.exception.UserNotDeletedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotDeletedAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotDeletedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotDeletedHandler(UserNotDeletedException e) {
        return e.getMessage();
    }

}
