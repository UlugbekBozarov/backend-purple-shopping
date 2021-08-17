package com.example.online_shopping.controller.exception;

import com.example.online_shopping.erroe.NotAcceptable;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.erroe.SystemError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> exception_500(SystemError e) {

        log.error("{}", e.toString());

        return new ResponseEntity<>(e.getError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<?> exception_404(NotFound e) {

        log.error("{}", e.toString());

        return new ResponseEntity<>(e.getError(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> exception_406(NotAcceptable e) {

        log.error("{}", e.toString());

        return new ResponseEntity<>(e.getError(), HttpStatus.ACCEPTED);
    }
}
