package com.patientregistration.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class DataNotAcceptableException extends RuntimeException {

    public DataNotAcceptableException(String message) {
        super(message);
    }

}
