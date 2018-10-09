package com.patientregistration.system.service.impl;

import com.patientregistration.system.domain.VisitModel;
import com.patientregistration.system.repository.VisitModelRepository;
import com.patientregistration.system.service.EmailService;
import com.patientregistration.system.service.Impl.VisitModelServiceImpl;
import com.patientregistration.system.service.VisitService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class VisitModelServiceImplTest {

    private VisitModelServiceImpl service;

    private VisitModelRepository mockRepository;
    private VisitService mockvisitService;
    private EmailService mockemailService;

    @Before
    public void before() {
        mockRepository = mock(VisitModelRepository.class);
        mockvisitService = mock(VisitService.class);
        mockemailService = mock(EmailService.class);
    }

    @Test
    public void save() {
        // Given
        VisitModel visitModel = new VisitModel();

        // When
        when(mockRepository.save(visitModel)).thenReturn(visitModel);
        VisitModel newVisitModel = service.save(visitModel);
    }
}
