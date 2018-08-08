package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.Statement;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.StatementRepository;
import com.patientregistration.system.service.StatementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatementServiceImpl implements StatementService {

    private StatementRepository statementRepository;

    public StatementServiceImpl(StatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    @Override
    public List<Statement> findAllStatements() {
        return statementRepository.findAll();
    }

    @Override
    public Statement findByStatementId(Long idStatement) {
        return statementRepository.findById(idStatement)
                .orElseThrow(() -> new ResourceNotFoundException(idStatement.toString()));
    }

    @Override
    public Statement saveOrUpdate(Statement statement) {
        return statementRepository.save(statement);
    }

    @Override
    public void delete(Long idStatement) {
        statementRepository.deleteById(idStatement);
    }

}
