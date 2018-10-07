package com.patientregistration.system.domain.View;

import com.patientregistration.system.domain.Clinic;
import com.patientregistration.system.domain.User;

import java.io.Serializable;

public class CompositeKey implements Serializable {
    private User user;
    private Clinic clinic;
}