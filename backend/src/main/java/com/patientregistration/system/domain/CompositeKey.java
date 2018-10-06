package com.patientregistration.system.domain;

import java.io.Serializable;

public class CompositeKey implements Serializable {
    private User user;
    private Clinic clinic;
}