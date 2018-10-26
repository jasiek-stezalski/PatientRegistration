package com.patientregistration.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class VisitsInTheSameTimeException extends RuntimeException {

    public VisitsInTheSameTimeException(String message) {
        super(message);
    }

}
