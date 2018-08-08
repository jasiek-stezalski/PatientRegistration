package com.patientregistration.system.service;

import com.patientregistration.system.domain.Statement;

import java.util.List;

public interface StatementService {

    List<Statement> findAllStatements();

    Statement findByStatementId(Long idStatement);

    Statement saveOrUpdate(Statement statement);

    void delete(Long idStatement);

}
