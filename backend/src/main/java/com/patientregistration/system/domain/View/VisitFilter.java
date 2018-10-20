package com.patientregistration.system.domain.View;

import lombok.Data;

@Data
public class VisitFilter {

    private String careType;
    private String city;
    private Long idClinic;
    private Long idDoctor;
    private String specialization;
    private Integer minPrice;
    private Integer maxPrice;

}
