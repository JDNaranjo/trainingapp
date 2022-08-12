package com.jdnt.perficient.training.exception.advice;

import com.jdnt.perficient.training.exception.NotCreatedException;
import com.jdnt.perficient.training.exception.NotDeletedException;
import com.jdnt.perficient.training.exception.NotFoundException;
import com.jdnt.perficient.training.exception.NotUpdatedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        body.put("validations", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotCreatedException.class)
    public ResponseEntity<Object> userNotCreatedHandler(NotCreatedException e) {
        Map<String, String> body = new HashMap<>();

        body.put("code", "NotCreatedException");
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotDeletedException.class)
    public ResponseEntity<Object> userNotDeletedHandler(NotDeletedException e) {
        Map<String, String> body = new HashMap<>();

        body.put("code", "NotDeletedException");
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> userNotFoundHandler(NotFoundException e) {
        Map<String, String> body = new HashMap<>();

        body.put("code", "NotFoundException");
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotUpdatedException.class)
    public ResponseEntity<Object> userNotUpdatedHandler(NotUpdatedException e) {
        Map<String, String> body = new HashMap<>();

        body.put("code", "NotUpdatedException");
        body.put("message", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
