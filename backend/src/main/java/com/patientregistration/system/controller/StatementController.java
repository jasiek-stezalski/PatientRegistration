package com.patientregistration.system.controller;

import com.patientregistration.system.domain.Statement;
import com.patientregistration.system.service.StatementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StatementController {

    private StatementService statementService;

    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @GetMapping("/statements")
    public List<Statement> getAllStatements() {
        return statementService.findAllStatements();
    }

    @GetMapping("/statements/{id}")
    public Statement getStatementById(@PathVariable(value = "id") Long idStatement) {
        return statementService.findByStatementId(idStatement);
    }

    @PostMapping("/statements")
    public Statement createStatement(@Valid @RequestBody Statement statement) {
        return statementService.saveOrUpdate(statement);
    }

    @DeleteMapping("/statements/{id}")
    public ResponseEntity<?> deleteStatement(@PathVariable(value = "id") Long idStatement) {
        statementService.delete(idStatement);

        return ResponseEntity.ok().build();
    }

}
