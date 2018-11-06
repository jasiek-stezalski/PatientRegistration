package com.patientregistration.system.repository;

import com.patientregistration.system.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
